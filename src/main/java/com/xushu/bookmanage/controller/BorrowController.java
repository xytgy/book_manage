package com.xushu.bookmanage.controller;

import com.xushu.bookmanage.common.Result;
import com.xushu.bookmanage.entity.Borrow;
import com.xushu.bookmanage.dto.BorrowRecordDto; // 导入 BorrowRecordDto
import com.xushu.bookmanage.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List; // 导入 List
import java.util.Map;

@RestController
@RequestMapping("/api/borrows")
@CrossOrigin
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping
    public Result<Borrow> borrowBook(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        Long bookId = request.get("bookId");
        Borrow newBorrow = borrowService.borrowBook(userId, bookId);
        return Result.success("借阅成功", newBorrow);
    }

    @GetMapping
    public Result<List<BorrowRecordDto>> getBorrowList(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) Integer status) {
        List<BorrowRecordDto> borrowList = borrowService.getBorrowList(username, bookTitle, status);
        return Result.success("获取借阅列表成功", borrowList);
    }

    @PostMapping("/{id}/return")
    public Result<Void> returnBook(@PathVariable Long id) {
        borrowService.returnBook(id);
        return Result.success("归还成功", null);
    }
}
