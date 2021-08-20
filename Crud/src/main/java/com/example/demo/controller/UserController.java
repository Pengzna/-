package com.example.demo.controller;


import com.example.demo.common.result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;

@RestController
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/user")
    public result createUser(User user){  //@RequestBody---前端传入数据需要为json格式
        return userService.createUser(user);
    }

    @DeleteMapping("/user")
    public result delUserByName(BigInteger id){ //需要前端通过地址栏传参
        return userService.delUserByName(id);
    }

    @PostMapping ("/user/update")
    public result updateUser(User user)
    {
        return userService.updateUser(user);
    }

    @GetMapping("/user")
    public result getUser(BigInteger id){
        return userService.getUser(id);
    }

    @GetMapping("/userAll")
    public result getAllUser(){ return userService.getAllUser(); }
}
