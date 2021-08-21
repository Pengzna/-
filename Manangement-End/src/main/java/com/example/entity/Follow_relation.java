package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("follow_relation")
public class Follow_relation extends Model<Follow_relation> {
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
      * 目标类型（USER：用户，TAG：标签） 
      */
    private String targetType;

    /**
      * 目标id 
      */
    private Long targetId;

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