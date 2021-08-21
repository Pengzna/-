package com.example.service;

import com.example.entity.Moment;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.MomentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MomentService extends ServiceImpl<MomentMapper, Moment> {

    @Resource
    private MomentMapper momentMapper;

}
