package com.xushu.bookmanage.service.impl;

import com.xushu.bookmanage.entity.Book;
import com.xushu.bookmanage.entity.Borrow;
import com.xushu.bookmanage.entity.User;
import com.xushu.bookmanage.exception.ServiceException;
import com.xushu.bookmanage.mapper.BookMapper;
import com.xushu.bookmanage.mapper.BorrowMapper;
import com.xushu.bookmanage.mapper.UserMapper;
import com.xushu.bookmanage.service.BorrowService;
import com.xushu.bookmanage.dto.BorrowRecordDto; // 导入 BorrowRecordDto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List; // 导入 List

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public Borrow borrowBook(Long userId, Long bookId) {

        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ServiceException(404, "用户不存在");
        }


        Book book = bookMapper.selectById(bookId);
        if (book == null) {
            throw new ServiceException(404, "图书不存在");
        }

        // 3. 检查图书库存
        if (book.getStock() <= 0) {
            throw new ServiceException(400, "图书库存不足");
        }

        // 4. 检查用户是否已借阅该书且未归还
        Borrow existingBorrow = borrowMapper.findUnreturnedBorrowByUserAndBook(userId, bookId);
        if (existingBorrow != null) {
            throw new ServiceException(400, "您已借阅该图书且尚未归还");
        }

        // 5. 创建借阅记录
        Borrow borrow = new Borrow();
        borrow.setUserId(userId);
        borrow.setBookId(bookId);
        borrow.setBorrowDate(LocalDateTime.now());
        borrow.setStatus(0);
        try {
            borrowMapper.insert(borrow);
        } catch (DuplicateKeyException e) {
            throw new ServiceException(400, "您已借阅该图书且尚未归还");
        }

        // 5. 更新图书库存和已借阅数量
        bookMapper.decreaseStock(bookId);
        bookMapper.increaseBorrowedCount(bookId);

        return borrowMapper.selectById(borrow.getId());
    }

    @Override
    public List<BorrowRecordDto> getBorrowList(String username, String bookTitle, Integer status) {
        return borrowMapper.selectBorrowList(username, bookTitle, status);
    }

    @Override
    @Transactional
    public void returnBook(Long borrowId) {
        Borrow borrow = borrowMapper.selectById(borrowId);
        if (borrow == null) {
            throw new ServiceException(404, "借阅记录不存在");
        }
        if (borrow.getStatus() != 0) { // 0: 借阅中
            throw new ServiceException(400, "该图书已归还或状态异常");
        }

        // 更新借阅记录状态为已归还 (1)
        borrow.setStatus(1); // 1: 已归还
        borrow.setReturnDate(LocalDateTime.now());
        borrowMapper.updateById(borrow);

        // 更新图书库存和已借阅数量
        bookMapper.increaseStock(borrow.getBookId());
        bookMapper.decreaseBorrowedCount(borrow.getBookId());
    }
}
