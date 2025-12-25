package com.xushu.bookmanage.service.impl;

import com.xushu.bookmanage.entity.Book;
import com.xushu.bookmanage.exception.ServiceException;
import com.xushu.bookmanage.mapper.BookMapper;
import com.xushu.bookmanage.mapper.BorrowMapper;
import com.xushu.bookmanage.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BorrowMapper borrowMapper;


    @Override
    public Book addBook(Book book) {
        // 1. 检查 ISBN 是否已存在
        if (bookMapper.countByIsbn(book.getIsbn()) > 0) {
            throw new ServiceException(400, "该 ISBN 编号已存在");
        }

        // 2. 保存图书
        bookMapper.insert(book);

        // 3. 返回包含分类名称的完整对象
        return bookMapper.selectById(book.getId());
    }

    @Override
    public Map<String, Object> findBookList(String title, String author, Integer categoryId, Integer pageNum, Integer pageSize, Integer minStock) {
        if (pageNum == null || pageNum < 1) pageNum = 1;
        if (pageSize == null || pageSize < 1) pageSize = 10;
        
        int offset = (pageNum - 1) * pageSize;
        
        List<Book> list = bookMapper.selectBookList(title, author, categoryId, offset, pageSize, minStock);
        long total = bookMapper.countBookList(title, author, categoryId, minStock);
        
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        
        return result;
    }

    @Override
    public void deleteBook(Long id) {

        Book book = bookMapper.selectById(id);
        if (book == null) {
            throw new ServiceException(404, "图书不存在");
        }

        // 检查图书是否已被借阅且未归还
        if (borrowMapper.countUnreturnedBorrowsByBookId(id) > 0) {
            throw new ServiceException(400, "该图书已被借阅，无法删除");
        }
        
        // 2. 执行删除
        bookMapper.deleteById(id);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        // 1. 检查图书是否存在
        Book existingBook = bookMapper.selectById(id);
        if (existingBook == null) {
            throw new ServiceException(404, "图书不存在");
        }

        // 2. 如果修改了 ISBN，检查新 ISBN 是否冲突
        if (book.getIsbn() != null && !book.getIsbn().equals(existingBook.getIsbn())) {
            if (bookMapper.countByIsbn(book.getIsbn()) > 0) {
                throw new ServiceException(400, "该 ISBN 编号已存在");
            }
        }

        // 3. 执行更新
        book.setId(id);
        bookMapper.updateById(book);

        // 4. 返回更新后的完整信息（包含分类名称）
        return bookMapper.selectById(id);
    }

    @Override
    public Book selectById(Long id) {
        Book book = bookMapper.selectById(id);
        if (book == null) {
            throw new ServiceException(404, "图书不存在");
        }
        return book;
    }
}
