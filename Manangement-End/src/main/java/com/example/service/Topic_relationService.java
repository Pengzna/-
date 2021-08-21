package com.example.service;

import com.example.entity.Topic_relation;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.Topic_relationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Topic_relationService extends ServiceImpl<Topic_relationMapper, Topic_relation> {

    @Resource
    private Topic_relationMapper topic_relationMapper;

}
