package com.xushu.bookmanage.service;

import com.xushu.bookmanage.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findCategories(String name);
    
    Category addCategory(Category category);
    
    Category updateCategory(Integer id, Category category);
    
    void deleteCategory(Integer id);
}
