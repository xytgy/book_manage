package com.xushu.bookmanage.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xushu.bookmanage.entity.User;
import com.xushu.bookmanage.mapper.UserMapper;
import com.xushu.bookmanage.service.UserService;
import com.xushu.bookmanage.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<User> findUsers(String username, String role, Integer pageNum, Integer pageSize) {
        if (pageNum == null) pageNum = 1;
        if (pageSize == null) pageSize = 10;
        Page<User> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectUserList(page, username, role);
    }

    @Override
    public User addUser(User user) {

        User existingUser = baseMapper.getByUsername(user.getUsername());
        if (existingUser != null) {
            throw new ServiceException(400, "用户名已存在");
        }
        

        this.save(user);
        

        return user;
    }

    @Override
    public User updateUser(Long id, User user) {
        // 1. 检查用户是否存在
        User existingUser = baseMapper.selectById(id);
        if (existingUser == null) {
            throw new ServiceException(404, "用户不存在");
        }
        
        // 2. 设置 ID 并更新 (MyMetaObjectHandler 会自动填充 updateTime)
        user.setId(id);
        this.updateById(user);
        
        // 3. 返回更新后的最新信息
        return baseMapper.selectById(id);
    }
}
