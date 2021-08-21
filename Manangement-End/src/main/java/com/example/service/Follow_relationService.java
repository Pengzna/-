package com.example.service;

import com.example.entity.Follow_relation;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.Follow_relationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Follow_relationService extends ServiceImpl<Follow_relationMapper, Follow_relation> {

    @Resource
    private Follow_relationMapper follow_relationMapper;

}
