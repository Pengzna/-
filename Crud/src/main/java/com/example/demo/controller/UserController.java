package com.example.demo.controller;


import com.example.demo.common.result;
import com.example.demo.entity.user;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/user")
    public result createUser(@RequestBody user User){  //@RequestBody---前端传入数据需要为json格式
        return userService.createUser(User);
    }

    @DeleteMapping("/user")
    public result delUserByName(String name){ //需要前端通过地址栏传参
        return userService.delUserByName(name);
    }

    @PostMapping ("/user/update")
    public result UpdateUser(user User)
    {
        return userService.UpdateUser(User);
    }

    @GetMapping("/user")
    public result GetUser(String name){
        return userService.GetUser(name);
    }


}
