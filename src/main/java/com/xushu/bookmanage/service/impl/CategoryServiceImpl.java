package com.xushu.bookmanage.service.impl;

import com.xushu.bookmanage.entity.Category;
import com.xushu.bookmanage.exception.ServiceException;
import com.xushu.bookmanage.mapper.CategoryMapper;
import com.xushu.bookmanage.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findCategories(String name) {
        if ("null".equals(name) || "undefined".equals(name)) {
            name = null;
        }
        return categoryMapper.selectList(name);
    }

    @Override
    public Category addCategory(Category category) {
        categoryMapper.insert(category);
        return category;
    }

    @Override
    public Category updateCategory(Integer id, Category category) {
        Category existing = categoryMapper.selectById(id);
        if (existing == null) {
            throw new ServiceException(404, "分类不存在");
        }
        category.setId(id);
        categoryMapper.updateById(category);
        return categoryMapper.selectById(id);
    }

    @Override
    public void deleteCategory(Integer id) {

        int bookCount = categoryMapper.countBooksByCategoryId(id);
        if (bookCount > 0) {
            throw new ServiceException(400, "该分类下仍有图书，无法删除");
        }
        

        int rows = categoryMapper.deleteById(id);
        if (rows == 0) {
            throw new ServiceException(404, "分类不存在");
        }
    }
}
