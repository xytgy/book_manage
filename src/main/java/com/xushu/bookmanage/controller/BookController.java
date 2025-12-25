package com.xushu.bookmanage.controller;

import com.xushu.bookmanage.common.Result;
import com.xushu.bookmanage.entity.Book;
import com.xushu.bookmanage.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 查询图书接口 (获取图书列表)
     */
    @GetMapping
    public Result<Map<String, Object>> getBooks(@RequestParam(required = false) String title,
                                              @RequestParam(required = false) String author,
                                              @RequestParam(required = false) Integer categoryId,
                                              @RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Map<String, Object> data = bookService.findBookList(title, author, categoryId, pageNum, pageSize);
        return Result.success("获取成功", data);
    }

    /**
     * 新增图书 (图书入库)
     */
    @PostMapping
    public Result<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        return Result.success("新增成功", newBook);
    }

    /**
     * 删除图书
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success("删除成功", null);
    }

    /**
     * 更新图书
     */
    @PutMapping("/{id}")
    public Result<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return Result.success("更新成功", updatedBook);
    }
}
