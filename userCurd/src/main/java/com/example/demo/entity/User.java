package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 共21个字段，顺序、数据类型一一对应
     * 采用private封装
     * 删掉了address（因为表里没有这个字段）
     */
    private BigInteger id;
    private String name;
    private Integer gender;
    private String avatar_url;
    private String description;
    private String mini_weixin_union_id;
    private String mini_weixin_open_id;
    private String country;
    private String province;
    private String city;
    private Integer follow_count;
    private Integer fans_count;
    private Integer letter_unread;
    private Integer comment_unread;
    private Integer at_unread;
    private Integer notification_unread;
    private BigInteger artist_id;
    private Date create_time;
    private Date update_time;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMini_weixin_union_id() {
        return mini_weixin_union_id;
    }

    public void setMini_weixin_union_id(String mini_weixin_union_id) {
        this.mini_weixin_union_id = mini_weixin_union_id;
    }

    public String getMini_weixin_open_id() {
        return mini_weixin_open_id;
    }

    public void setMini_weixin_open_id(String mini_weixin_open_id) {
        this.mini_weixin_open_id = mini_weixin_open_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(Integer follow_count) {
        this.follow_count = follow_count;
    }

    public Integer getFans_count() {
        return fans_count;
    }

    public void setFans_count(Integer fans_count) {
        this.fans_count = fans_count;
    }

    public Integer getLetter_unread() {
        return letter_unread;
    }

    public void setLetter_unread(Integer letter_unread) {
        this.letter_unread = letter_unread;
    }

    public Integer getComment_unread() {
        return comment_unread;
    }

    public void setComment_unread(Integer comment_unread) {
        this.comment_unread = comment_unread;
    }

    public Integer getAt_unread() {
        return at_unread;
    }

    public void setAt_unread(Integer at_unread) {
        this.at_unread = at_unread;
    }

    public Integer getNotification_unread() {
        return notification_unread;
    }

    public void setNotification_unread(Integer notification_unread) {
        this.notification_unread = notification_unread;
    }

    public BigInteger getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(BigInteger artist_id) {
        this.artist_id = artist_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

}
