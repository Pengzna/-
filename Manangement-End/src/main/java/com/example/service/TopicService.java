package com.example.service;

import com.example.entity.Topic;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.TopicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TopicService extends ServiceImpl<TopicMapper, Topic> {

    @Resource
    private TopicMapper topicMapper;

}
