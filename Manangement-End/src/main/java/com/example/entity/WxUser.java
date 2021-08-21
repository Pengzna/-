package com.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

@Data
@TableName("user")
public class WxUser extends Model<WxUser> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 用户名 
      */
    private String name;

    /**
      * 性别（0：女，1：男） 
      */
    private Integer gender;

    /**
      * 头像url 
      */
    //@JsonProperty("avatarUrl")
    private String avatarUrl;

    /**
      * 个人简介 
      */
    private String description;

    /**
      * 微信小程序unionId 
      */
    //@JsonProperty("miniWeixinUnionId")
    private String miniWeixinUnionId;

    /**
      * 微信小程序openId 
      */
    //@JsonProperty("miniWeixinOpenId")
    private String miniWeixinOpenId;

    /**
      * QQ小程序unionId 
      */
    //@JsonProperty("miniQqUnionId")
    private String miniQqUnionId;

    /**
      * QQ小程序openId 
      */
    //@JsonProperty("miniQqOpenId")
    private String miniQqOpenId;

    /**
      * 国家 
      */

    private String country;

    /**
      * 省 
      */
    private String province;

    /**
      * 市 
      */
    private String city;

    /**
      * 大学 
      */
    private String university;

    /**
      * 关注人数 
      */
    //@JsonProperty("followCount")
    private Integer followCount;

    /**
      * 粉丝人数 
      */
    //@JsonProperty("fansCount")
    private Integer fansCount;

    /**
      * 私信未读 
      */
    //@JsonProperty("letterUnread")
    private Integer letterUnread;

    /**
      * 评论未读 
      */
    //@JsonProperty("commentUnread")
    private Integer commentUnread;

    /**
      * @未读 
      */
    //@JsonProperty("atUnread")
    private Integer atUnread;

    /**
      * 通知未读 
      */
    //@JsonProperty("notificationUnread")
    private Integer notificationUnread;

    /**
      * 演出者id 
      */
    //@JsonProperty("artistId")
    private Long artistId;

    /**
      * 创建时间 
      */
    //@JsonProperty("createTime")
    private Date createTime;

    /**
      * 更新时间 
      */
    //@JsonProperty("updateTime")
    private Date updateTime;

    /**
      * 大学id 
      */
    //@JsonProperty("uniId")
    private String uniId;

}