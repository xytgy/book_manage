package com.xushu.bookmanage.service;

import com.xushu.bookmanage.entity.Borrow;

public interface BorrowService {
    Borrow borrowBook(Long userId, Long bookId);
}
