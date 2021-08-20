package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Repository
public interface UserDao {
    /**
     * TODO: insert方法字段改全，共21个
     */

    @Insert("insert into user(name,age,address) VALUES(#{name},#{age},#{address}) ")
    int saveUser(User user);

    /**
     * 已改
     * @param id
     * @return
     */
    @Delete("delete from user where id =#{id}")
    int delUserByName(@Param("id") BigInteger id);

    /**
     * TODO: update方法字段改全，共21个
     * TIPS: where已改为主键id
     */
    @Update("update user set age=#{age},address=#{address} where id =#{id}")
    int updateUser(User user);

    /**
     * 已改
     * @param id
     * @return
     */
    @Select("select * from user where id =#{id}")
    User getUser(@Param("id") BigInteger id);

    /**
     * 新增方法，查所有用户
     * @return
     */
    @Select("select * from user")
    List<User> getAllUser();
}
