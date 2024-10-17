package com.museum.ticketsystem.controller;

import com.museum.ticketsystem.model.User;
import com.museum.ticketsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        String result = userService.registerUser(user);
        response.put("message", result);
        return ResponseEntity.ok(response); // 返回注册结果
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        String result = userService.loginUser(user);
        response.put("message", result);
        return ResponseEntity.ok(response); // 返回登录结果
    }
}
