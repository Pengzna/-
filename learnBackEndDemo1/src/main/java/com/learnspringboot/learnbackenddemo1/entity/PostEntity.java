package com.learnspringboot.learnbackenddemo1.entity;

import java.util.Date;


public class PostEntity {

    private Integer id;
    private String userName;
    private String postTime;
    private String postContent;
    private String postTitle;

    public String getPostTitle() { return postTitle; }

    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }


}
