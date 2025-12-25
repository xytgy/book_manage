package com.xushu.bookmanage.mapper;

import com.xushu.bookmanage.entity.Borrow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BorrowMapper {
    int insert(Borrow borrow);
    Borrow selectById(@Param("id") Long id);
    int updateById(Borrow borrow);
}
