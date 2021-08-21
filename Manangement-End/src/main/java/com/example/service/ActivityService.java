package com.example.service;

import com.example.entity.Activity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.ActivityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActivityService extends ServiceImpl<ActivityMapper, Activity> {

    @Resource
    private ActivityMapper activityMapper;

}
