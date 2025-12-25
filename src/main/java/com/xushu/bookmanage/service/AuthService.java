package com.xushu.bookmanage.service;

import com.xushu.bookmanage.dto.LoginRequest;
import com.xushu.bookmanage.dto.LoginResponse;
import com.xushu.bookmanage.dto.UserDTO;
import com.xushu.bookmanage.entity.User;
import com.xushu.bookmanage.exception.ServiceException;
import com.xushu.bookmanage.mapper.UserMapper;
import com.xushu.bookmanage.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserMapper userMapper;

    public LoginResponse login(LoginRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();
        
        User user = userMapper.getByUsername(username);

        // 2. 如果用户不存在，抛出异常
        if (user == null) {
            throw new ServiceException(401, "用户名不存在");
        }

        // 3. 比较密码
        if (!user.getPassword().equals(request.getPassword())) {
            throw new ServiceException(401, "密码错误");
        }

        // 4. 登录成功，生成 Token
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDTO.getId());
        claims.put("username", userDTO.getUsername());
        claims.put("role", userDTO.getRole());
        
        String token = jwtUtils.createToken(claims);
        
        return LoginResponse.builder()
                .token(token)
                .userInfo(userDTO)
                .build();
    }
}
