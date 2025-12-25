package com.xushu.bookmanage.service.impl;

import com.xushu.bookmanage.entity.Book;
import com.xushu.bookmanage.entity.Borrow;
import com.xushu.bookmanage.entity.User;
import com.xushu.bookmanage.exception.ServiceException;
import com.xushu.bookmanage.mapper.BookMapper;
import com.xushu.bookmanage.mapper.BorrowMapper;
import com.xushu.bookmanage.mapper.UserMapper;
import com.xushu.bookmanage.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        // 4. 创建借阅记录
        Borrow borrow = new Borrow();
        borrow.setUserId(userId);
        borrow.setBookId(bookId);
        borrow.setBorrowDate(LocalDateTime.now());
        borrow.setStatus("BORROWED");
        borrowMapper.insert(borrow);

        // 5. 更新图书库存和已借阅数量
        bookMapper.decreaseStock(bookId);
        bookMapper.increaseBorrowedCount(bookId);

        return borrowMapper.selectById(borrow.getId());
    }
}
