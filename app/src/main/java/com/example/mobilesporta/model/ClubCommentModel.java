package com.example.mobilesporta.model;

import java.sql.Date;

public class ClubCommentModel {

    private String user_id;
    private String content;
    private String date;
    private String user_name;
    private String avatar;

    public ClubCommentModel() {
    }

    public ClubCommentModel(String user_id, String content, String date, String user_name, String avatar) {
        this.user_id = user_id;
        this.content = content;
        this.date = date;
        this.user_name = user_name;
        this.avatar = avatar;
    }

    public String getUser_id() {
        return user_id;
    }

    public ClubCommentModel setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ClubCommentModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
