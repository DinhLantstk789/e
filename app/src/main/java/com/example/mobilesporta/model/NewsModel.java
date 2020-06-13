package com.example.mobilesporta.model;

public class NewsModel {

    String title,image,desc, url;

    public NewsModel(){

    }

    public NewsModel(String title, String image, String desc, String url) {
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.url = url;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
