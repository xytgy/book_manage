package com.xushu.bookmanage.controller;

import com.xushu.bookmanage.common.Result;
import com.xushu.bookmanage.entity.Borrow;
import com.xushu.bookmanage.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
