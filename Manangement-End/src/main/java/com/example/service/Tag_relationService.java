package com.example.service;

import com.example.entity.Tag_relation;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.Tag_relationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Tag_relationService extends ServiceImpl<Tag_relationMapper, Tag_relation> {

    @Resource
    private Tag_relationMapper tag_relationMapper;

}
