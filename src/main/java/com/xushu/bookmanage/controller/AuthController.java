package com.xushu.bookmanage.controller;

import com.xushu.bookmanage.common.Result;
import com.xushu.bookmanage.dto.LoginRequest;
import com.xushu.bookmanage.dto.LoginResponse;
import com.xushu.bookmanage.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin // 允许跨域
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
}
