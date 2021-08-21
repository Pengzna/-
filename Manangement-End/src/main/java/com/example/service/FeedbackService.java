package com.example.service;

import com.example.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.FeedbackMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FeedbackService extends ServiceImpl<FeedbackMapper, Feedback> {

    @Resource
    private FeedbackMapper feedbackMapper;

}
