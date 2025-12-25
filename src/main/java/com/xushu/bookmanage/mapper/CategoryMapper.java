package com.xushu.bookmanage.mapper;

import com.xushu.bookmanage.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryMapper {
    Category selectById(@Param("id") Integer id);
}
