package com.example.demo.service;

import com.example.demo.common.result;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public result createUser(user USER){
        if(USER==null){
            return new result("400","Empty Entity",null);
        }
        int res= userDao.saveUser(USER);
        return res>0? new result("200","SuccessfullyCreate",USER):result.DB_ERROR;
    }

    public result delUserByName(String name){
        if(name==null) {
            return new result("400","nameNotFound",null);
        }
        int res=userDao.delUserByName(name);
        return res>0?  new result("200","SuccessfullyDelete",name):result.DB_ERROR;
        }

        public result UpdateUser(user User){
        if(User==null){
            return new result("400","emptyUser",null);
        }
        user FormerUser=userDao.GetUser(User.name);
        if(FormerUser==null){
            return new result("400","TheUserHaven'tCreateYet",null);
        }
        int res=userDao.UpdateUser(User);
        return res>0? new result("200","SuccessfullyUpdate",User):result.DB_ERROR;
        }

   public result GetUser(String name){
       if(name==null) {
           return new result("400","nameNotFound",null);
       }
       user res=userDao.GetUser(name);
       return res!=null?  new result("200","SuccessfullyGet",res):result.DB_ERROR;
   }
}
