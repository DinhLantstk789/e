package com.example.mobilesporta.model;

public class StadiumCommentModel {

    private String user_id;
    private String content;

    public StadiumCommentModel() {
    }

    public StadiumCommentModel(String user_id, String content) {
        this.user_id = user_id;
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public StadiumCommentModel setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public StadiumCommentModel setContent(String content) {
        this.content = content;
        return this;
    }
}
