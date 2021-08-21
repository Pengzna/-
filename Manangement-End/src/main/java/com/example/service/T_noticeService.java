package com.example.service;

import com.example.entity.T_notice;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.T_noticeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class T_noticeService extends ServiceImpl<T_noticeMapper, T_notice> {

    @Resource
    private T_noticeMapper t_noticeMapper;

}
