package com.learnspringboot.learnbackenddemo1.controller;

import com.alibaba.fastjson.JSON;
import com.learnspringboot.learnbackenddemo1.dao.PostDao;
import com.learnspringboot.learnbackenddemo1.entity.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostDao postDao;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(PostEntity postEntity){
        int count = postDao.addPost(postEntity);
        if(count > 0){
            System.out.println("总共增加了" + count + "条推送");
            return "add ok";
        }
        return "add failed.";
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE)
    public String deletePost(Integer id){
        int count = postDao.deletePostByID(id);
        if(count > 0){
            System.out.println("总共删除了" + count + "条推送");
            return "delete ok";
        }
        return "delete failed.";
    }

    @RequestMapping(value = "/deleteByTitle", method = RequestMethod.DELETE)
    public String deletePost(String postTitle){
        int count = postDao.deletePostByTitle(postTitle);
        if(count > 0){
            System.out.println("总共删除了" + count + "条推送");
            return "delete ok";
        }
        return "delete failed.";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updatePost(PostEntity postEntity, Integer id){
        int count = postDao.updatePost(postEntity);
        if(count > 0){
            System.out.println("总共更新了" + count + "条推送");
            return "update ok";
        }
        return "update failed.";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String getPost(String postTitle){
        PostEntity postEntity = postDao.getPost(postTitle);
        String postJson = JSON.toJSONString(postEntity);
        return postJson;
    }

    @RequestMapping(value = "/checkAll", method = RequestMethod.GET)
    public String getALlPost(){
        List<PostEntity> postEntities = postDao.getAllPost();
        String postsJson = JSON.toJSONString(postEntities);
        return postsJson;
    }
}
