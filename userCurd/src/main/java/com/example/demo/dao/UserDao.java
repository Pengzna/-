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
     * 已改
     * @param user
     * @return
     */
    @Insert("insert into user(name," +
            "gender," +
            "avatar_url," +
            "description," +
            "mini_weixin_union_id," +
            "mini_weixin_open_id," +
            "country," +
            "province," +
            "city," +
            "follow_count," +
            "fans_count," +
            "letter_unread," +
            "comment_unread," +
            "at_unread," +
            "notification_unread," +
            "artist_id," +
            "create_time," +
            "update_time)" +
            " VALUES(#{name}," +
            "#{gender}," +
            "#{avatar_url}," +
            "#{description}," +
            "#{mini_weixin_union_id}," +
            "#{mini_weixin_open_id}," +
            "#{country}," +
            "#{province}," +
            "#{city}," +
            "#{follow_count}," +
            "#{fans_count}," +
            "#{letter_unread}," +
            "#{comment_unread}," +
            "#{at_unread}," +
            "#{notification_unread}," +
            "#{artist_id}," +
            "#{create_time}," +
            "#{update_time}) ")
    int saveUser(User user);

    /**
     * 已改
     * @param id
     * @return
     */
    @Delete("delete from user where id =#{id}")
    int delUserByName(@Param("id") BigInteger id);

    /**
     * 已改
     * @param user
     * @return
     */
    @Update("update user set name=#{name}," +
            "gender=#{gender}," +
            "avatar_url=#{avatar_url}," +
            "description=#{description}," +
            "mini_weixin_union_id=#{mini_weixin_union_id}," +
            "mini_weixin_open_id=#{mini_weixin_open_id}," +
            "country=#{country}," +
            "province=#{province}," +
            "city=#{city}," +
            "follow_count=#{follow_count}," +
            "fans_count=#{fans_count}," +
            "letter_unread=#{letter_unread}," +
            "comment_unread=#{comment_unread}," +
            "at_unread=#{at_unread}," +
            "notification_unread=#{notification_unread}," +
            "artist_id=#{artist_id}," +
            "create_time=#{create_time}," +
            "update_time=#{update_time}" +
            " where id =#{id}")
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
