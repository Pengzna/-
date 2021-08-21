package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("topic")
public class Topic extends Model<Topic> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 话题名称 
      */
    private String name;

    /**
      * 封面url 
      */
    private String coverUrl;

    /**
      * 创建人id 
      */
    private Long createUserId;

    /**
      * 最新更新者id 
      */
    private Long lastUpdateUserId;

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