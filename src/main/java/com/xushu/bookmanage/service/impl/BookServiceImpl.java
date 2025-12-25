package com.xushu.bookmanage.service.impl;

import com.xushu.bookmanage.entity.Book;
import com.xushu.bookmanage.entity.Category;
import com.xushu.bookmanage.exception.ServiceException;
import com.xushu.bookmanage.mapper.BookMapper;
import com.xushu.bookmanage.mapper.CategoryMapper;
import com.xushu.bookmanage.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Book addBook(Book book) {
        // 1. 检查 ISBN 是否已存在
        if (bookMapper.countByIsbn(book.getIsbn()) > 0) {
            throw new ServiceException(400, "该 ISBN 编号已存在");
        }

        // 2. 保存图书
        bookMapper.insert(book);

        // 3. 获取并设置分类名称
        Category category = categoryMapper.selectById(book.getCategoryId());
        if (category != null) {
            book.setCategoryName(category.getName());
        }

        return book;
    }
}
