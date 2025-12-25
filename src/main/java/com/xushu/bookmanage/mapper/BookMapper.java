package com.xushu.bookmanage.mapper;

import com.xushu.bookmanage.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BookMapper {
    int insert(Book book);
    int countByIsbn(@Param("isbn") String isbn);
    Book selectById(@Param("id") Long id);
}
