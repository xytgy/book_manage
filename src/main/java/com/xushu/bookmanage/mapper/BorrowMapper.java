package com.xushu.bookmanage.mapper;

import com.xushu.bookmanage.entity.Borrow;
import com.xushu.bookmanage.dto.BorrowRecordDto; // 导入 BorrowRecordDto
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List; // 导入 List

@Mapper
public interface BorrowMapper {
    int insert(Borrow borrow);
    Borrow selectById(@Param("id") Long id);
    int updateById(Borrow borrow);
    int deleteById(@Param("id") Long id);

    // 新增方法：查询借阅列表
    List<BorrowRecordDto> selectBorrowList(@Param("username") String username,
                                           @Param("bookTitle") String bookTitle,
                                           @Param("status") Integer status);

    Borrow findUnreturnedBorrowByUserAndBook(@Param("userId") Long userId, @Param("bookId") Long bookId);

    int countUnreturnedBorrowsByBookId(@Param("bookId") Long bookId);
}
