package com.example.service;

import com.example.entity.Zhiding_moment;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.Zhiding_momentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Zhiding_momentService extends ServiceImpl<Zhiding_momentMapper, Zhiding_moment> {

    @Resource
    private Zhiding_momentMapper zhiding_momentMapper;

}
