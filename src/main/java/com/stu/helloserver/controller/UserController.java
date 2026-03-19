package com.stu.helloserver.controller;

import com.stu.helloserver.common.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        String data = "查询成功, 正在返回 ID 为 " + id + " 的用户信息";
        return Result.success(data);
    }

    @PostMapping
    public Result<String> createUser() {
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result<String> login() {
        return Result.success("登录成功");
    }
}
