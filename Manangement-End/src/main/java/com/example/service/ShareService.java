package com.example.service;

import com.example.entity.Share;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.ShareMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShareService extends ServiceImpl<ShareMapper, Share> {

    @Resource
    private ShareMapper shareMapper;

}
