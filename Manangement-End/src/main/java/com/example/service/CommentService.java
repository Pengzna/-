package com.example.service;

import com.example.entity.Comment;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {

    @Resource
    private CommentMapper commentMapper;

}
