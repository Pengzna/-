package com.example.demo.dao;

import com.example.demo.entity.user;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

    @Insert("insert into user(name,age,address) VALUES(#{name},#{age},#{address}) ")
    int saveUser(user USER);

    @Delete("delete from user where name =#{name}")
    int delUserByName(String name);

    @Update("update user set age=#{age},address=#{address} where name =#{name}")
    int UpdateUser(user User);

    @Select("select * from user where name =#{name}")
    user GetUser(String name);
}
