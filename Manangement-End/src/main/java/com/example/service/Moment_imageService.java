package com.example.service;

import com.example.entity.Moment_image;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.Moment_imageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Moment_imageService extends ServiceImpl<Moment_imageMapper, Moment_image> {

    @Resource
    private Moment_imageMapper moment_imageMapper;

}
