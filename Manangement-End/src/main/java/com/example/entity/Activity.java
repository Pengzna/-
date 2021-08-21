package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("activity")
public class Activity extends Model<Activity> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 演出名称 
      */
    private String name;

    /**
      * 演出起始时间 
      */
    private Date showStartTime;

    /**
      * 演出结束时间 
      */
    private Date showEndTime;

    /**
      * 场地城市 
      */
    private String venueCity;

    /**
      * 场地名称 
      */
    private String venueName;

    /**
      * 场地地址 
      */
    private String venueAddress;

    /**
      * 海报图片 
      */
    private String posterImage;

    /**
      * 数据来源渠道（秀动：SHOWSTART） 
      */
    private String sourceChannel;

    /**
      * 数据来源渠道-对象id 
      */
    private String sourceId;

    /**
      * 来源url 
      */
    private String sourceUrl;

    /**
      * 线上活动 
      */
    private Integer online;

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