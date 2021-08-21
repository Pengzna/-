package com.example.service;

import com.example.entity.WxUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.WxUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WxUserService extends ServiceImpl<WxUserMapper, WxUser> {

    @Resource
    private WxUserMapper wxUserMapper;

}
