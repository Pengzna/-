package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("tag")
public class Tag extends Model<Tag> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 标签名称 
      */
    private String name;

    /**
      * 创建人id 
      */
    private Long createUserId;

    /**
      * 粉丝人数 
      */
    private Integer fansCount;

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