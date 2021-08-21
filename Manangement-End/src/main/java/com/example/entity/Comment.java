package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("comment")
public class Comment extends Model<Comment> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 用户id 
      */
    private Long userId;

    /**
      * 动态id 
      */
    private Long momentId;

    /**
      * 内容 
      */
    private String content;

    /**
      * 父评论id 
      */
    private Long parentId;

    /**
      * @用户id 
      */
    private Long replyToId;

    /**
      * 点赞数量 
      */
    private Integer likeCount;

    /**
      * 状态（0：已屏蔽，1：正常，2：已删除） 
      */
    private Integer status;

    /**
      * 演出者id 
      */
    private Long artistId;

    /**
      * 创建时间 
      */
    private Date createTime;

    /**
      * 更新时间 
      */
    private Date updateTime;

}