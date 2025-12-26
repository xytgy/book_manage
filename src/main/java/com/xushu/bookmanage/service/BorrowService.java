package com.xushu.bookmanage.service;

import com.xushu.bookmanage.entity.Borrow;
import com.xushu.bookmanage.dto.BorrowRecordDto; // 导入 BorrowRecordDto

import java.util.List; // 导入 List

public interface BorrowService {
    Borrow borrowBook(Long userId, Long bookId);

    // 新增方法：获取借阅列表
    List<BorrowRecordDto> getBorrowList(String username, String bookTitle, Integer status);

    void returnBook(Long borrowId);

    void deleteBorrow(Long id);
}
