package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.result;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public result createUser(User user){
        if(user==null){
            return new result("400","emptyEntity",null);
        }
        int res= userDao.saveUser(user);
        return res>0? new result("200","Successfully create!",user):result.DB_ERROR;
    }

    public result delUserByName(BigInteger id){
        if(id==null) {
            return new result("400","idNotFound",null);
        }
        int res=userDao.delUserByName(id);
        return res>0?  new result("200","Successfully delete!",id):result.DB_ERROR;}

    public result updateUser(User user){
        if(user==null){
            return new result("400","emptyUser",null);
        }
        /**
         * 这里的formerUser是什么意思？
         * 原来是User formerUser=userDao.getUser(user.name);
         * user.name我改成了主键id:user.getId()
         */
        User formerUser=userDao.getUser(user.getId());
        if(formerUser==null){
            return new result("400","The user haven't been created yet",null);
        }
        int res=userDao.updateUser(user);
        return res>0? new result("200","Successfully update!",user):result.DB_ERROR;
        }

   public result getUser(BigInteger id){
       if(id==null) {
           return new result("400","nameNotFound",null);
       }
       User res=userDao.getUser(id);
       return res!=null?  new result("200","Successfully check!",res):result.DB_ERROR;
   }

   public result getAllUser(){
        List<User> allUsers = userDao.getAllUser();
        return new result("200", "Successfully check all users!", allUsers);
   }
}
