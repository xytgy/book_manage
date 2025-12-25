package com.xushu.bookmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xushu.bookmanage.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> findUsers(String username, String role, Integer pageNum, Integer pageSize);
    User addUser(User user);
    User updateUser(Long id, User user);
}
