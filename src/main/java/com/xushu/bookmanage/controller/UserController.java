package com.xushu.bookmanage.controller;

import com.xushu.bookmanage.common.Result;
import com.xushu.bookmanage.entity.User;
import com.xushu.bookmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public Result<List<User>> getUsers(@RequestParam(required = false) String username,
                                     @RequestParam(required = false) String role,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        List<User> list = userService.findUsers(username, role, pageNum, pageSize);
        return Result.success("获取成功", list);
    }

    /**
     * 新增用户
     */
    @PostMapping("/users")
    public Result<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return Result.success("新增成功", newUser);
    }

    /**
     * 更新用户
     */
    @PutMapping("/users/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return Result.success("更新成功", updatedUser);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/users/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success("删除成功", null);
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/user/{id}")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

}
