package com.xushu.bookmanage.service;

import com.xushu.bookmanage.entity.Book;

import java.util.Map;

public interface BookService {
    Book addBook(Book book);
    
    Map<String, Object> findBookList(String title, String author, Integer categoryId, Integer pageNum, Integer pageSize, Integer minStock);

    void deleteBook(Long id);

    Book updateBook(Long id, Book book);

    Book selectById(Long id);
}
