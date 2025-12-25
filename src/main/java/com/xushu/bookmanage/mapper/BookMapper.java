package com.xushu.bookmanage.mapper;

import com.xushu.bookmanage.entity.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    int insert(Book book);
    int countByIsbn(@Param("isbn") String isbn);
    Book selectById(@Param("id") Long id);
    
    List<Book> selectBookList(@Param("title") String title, 
                             @Param("author") String author, 
                             @Param("categoryId") Integer categoryId, 
                             @Param("offset") Integer offset, 
                             @Param("pageSize") Integer pageSize);
    
    long countBookList(@Param("title") String title, 
                      @Param("author") String author, 
                      @Param("categoryId") Integer categoryId);

    int deleteById(@Param("id") Long id);

    int updateById(Book book);
}
