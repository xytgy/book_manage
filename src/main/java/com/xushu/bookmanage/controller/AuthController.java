package com.xushu.bookmanage.controller;

import com.xushu.bookmanage.common.Result;
import com.xushu.bookmanage.common.UserContext;
import com.xushu.bookmanage.dto.LoginRequest;
import com.xushu.bookmanage.dto.LoginResponse;
import com.xushu.bookmanage.entity.User;
import com.xushu.bookmanage.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@Slf4j
//@Api(tags="员工相关接口")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("员工登录:{}",loginRequest);
        LoginResponse response = authService.login(loginRequest);
        return Result.success("登录成功", response);
    }

    @GetMapping("/info")
    public Result<User> getInfo() {
        Long userId = UserContext.getCurrentUserId();
        if (userId == null) {
            return Result.error(401, "未登录");
        }
        User user = authService.getUserInfo(userId);
        return Result.success("获取成功", user);
    }

    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody User profileData) {
        Long userId = UserContext.getCurrentUserId();
        if (userId == null) {
            return Result.error(401, "未登录");
        }
        authService.updateProfile(userId, profileData);
        return Result.success("更新成功", null);
    }

    @PostMapping("/reset-password")
    public Result<Void> resetPassword(@RequestBody Map<String, String> request) {
        Long userId = UserContext.getCurrentUserId();
        if (userId == null) {
            return Result.error(401, "未登录");
        }
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        authService.resetPassword(userId, oldPassword, newPassword);
        return Result.success("修改成功", null);
    }
}
