package com.xushu.bookmanage.mapper;

import com.xushu.bookmanage.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    Category selectById(@Param("id") Integer id);
    
    List<Category> selectList(@Param("name") String name);
    
    int insert(Category category);
    
    int updateById(Category category);
    
    int deleteById(@Param("id") Integer id);
    
    int countBooksByCategoryId(@Param("categoryId") Integer categoryId);
}
