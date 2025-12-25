package com.xushu.bookmanage.controller;

import com.xushu.bookmanage.common.Result;
import com.xushu.bookmanage.entity.Book;
import com.xushu.bookmanage.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 新增图书 (图书入库)
     */
    @PostMapping
    public Result<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        return Result.success("新增成功", newBook);
    }
}
