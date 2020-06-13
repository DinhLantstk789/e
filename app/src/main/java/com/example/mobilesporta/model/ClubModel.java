package com.example.mobilesporta.model;

import java.util.ArrayList;
import java.util.List;

public class ClubModel {

    private String club_name;
    private String image;
    private String slogan;
    private String description;
    private String user_created_id;
    private String background;
//    private Integer match_count;
    private ArrayList<ClubCommentModel> listComments;

    public ClubModel() {
    }

    public ClubModel(String club_name, String image, String slogan, String description, String user_created_id, ArrayList<ClubCommentModel> listComments, String background) {
        this.club_name = club_name;
        this.image = image;
        this.slogan = slogan;
        this.description = description;
        this.user_created_id = user_created_id;
//        this.match_count = match_count;
        this.listComments = listComments;
        this.background = background;
    }

    public String getClub_name() {
        return club_name;
    }

    public ClubModel setClub_name(String club_name) {
        this.club_name = club_name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ClubModel setImage(String image) {
        this.image = image;
        return this;
    }

    public String getSlogan() {
        return slogan;
    }

    public ClubModel setSlogan(String slogan) {
        this.slogan = slogan;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ClubModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUser_created_id() {
        return user_created_id;
    }

    public ClubModel setUser_created_id(String user_created_id) {
        this.user_created_id = user_created_id;
        return this;
    }

    public ArrayList<ClubCommentModel> getListComments() {
        return listComments;
    }

    public void setListComments(ArrayList<ClubCommentModel> listComments) {
        this.listComments = listComments;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
