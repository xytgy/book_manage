package com.xushu.bookmanage.controller;

import com.xushu.bookmanage.common.Result;
import com.xushu.bookmanage.entity.Category;
import com.xushu.bookmanage.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类列表
     */
    @GetMapping
    public Result<List<Category>> getCategories(@RequestParam(required = false) String name) {
        List<Category> list = categoryService.findCategories(name);
        return Result.success("获取成功", list);
    }

    /**
     * 新增分类
     */
    @PostMapping
    public Result<Category> addCategory(@RequestBody Category category) {
        Category newCategory = categoryService.addCategory(category);
        return Result.success("新增成功", newCategory);
    }

    /**
     * 更新分类
     */
    @PutMapping("/{id}")
    public Result<Category> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return Result.success("更新成功", updatedCategory);
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return Result.success("删除成功", null);
    }
}
