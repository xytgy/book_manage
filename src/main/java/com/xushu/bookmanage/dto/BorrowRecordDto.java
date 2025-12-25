package com.xushu.bookmanage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowRecordDto {
    private Long id;
    private Long userId;
    private String username; // 借阅人用户名
    private Long bookId;
    private String bookTitle; // 图书名称

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime borrowDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime returnDate;

    private Integer status; // 0: 借阅中, 1: 已归还, 2: 已逾期
}
