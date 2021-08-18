package com.learnspringboot.learnbackenddemo1.dao;

import com.learnspringboot.learnbackenddemo1.entity.PostEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

/**
 * Created by Peng Junzhi in 20210818
 * Dao层
 */

@Mapper
@Repository
public interface PostDao {

    /**
     * 增加一条记录
     * @param postEntity 推送实体
     * @return
     */
    @Insert("insert into demo1(userName, postTime, postContent, postTitle) VALUES(#{userName}, #{postTime}, #{postContent}, #{postTitle})")
    int addPost(PostEntity postEntity);

    /**
     * 删除一条记录
     * @param id 推送id
     * @return
     */
    @Delete("delete from demo1 where id =#{id} ")
    int deletePostByID(@Param("id") Integer id);

    /**
     * 删除一条记录
     * @param postTitle 推送标题
     * @return
     */
    @Delete("delete from demo1 where postTitle =#{postTitle} ")
    int deletePostByTitle(@Param("postTitle") String postTitle);

    /**
     * 修改一条推送
     * @param postEntity 推送实体
     * @param id 推送id
     * @return
     */
    /**
     * TODO: 改掉：Parameter 'userName' not found. Available parameters are [postEntity, id, param1, param2] bug
     * @return
     */
    @Update("update demo1 set userName=#{userName}, postTime=#{postTime}, postContent=#{postContent}, postTitle=#{postTitle} where id=#{id}")
    int updatePost(PostEntity postEntity);

    /**
     * 查看推送
     * @param postTitle 推送标题
     * @return
     */
    @Select("select * from demo1 where postTitle= #{postTitle}")
    PostEntity getPost(@Param("postTitle") String postTitle);

    /**
     * 查看所有推送
     * @param
     * @return
     */
    @Select("select * from demo1")
    List<PostEntity> getAllPost();

}
