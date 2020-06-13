package com.example.mobilesporta.model;

import java.util.ArrayList;

public class StadiumModel {
    private String stadium_name;
    private String description;
    private String address;
    private String amount;
    private String cost;
    private String image;
    private String phone_number;
    private String time_open;
    private String time_close;
    private String location_x;
    private String location_y;

    private ArrayList<StadiumCommentModel> listComments;
    private String zone;

    public StadiumModel() {
    }

    public StadiumModel(String stadium_name, String description, String address, String amount, String cost, String image, String phone_number, String time_open, String time_close, String location_x, String location_y) {
        this.stadium_name = stadium_name;
        this.description = description;
        this.address = address;
        this.amount = amount;
        this.cost = cost;
        this.image = image;
        this.phone_number = phone_number;
        this.time_open = time_open;
        this.time_close = time_close;
        this.location_x = location_x;
        this.location_y = location_y;
//        this.listComments = listComments;
//        this.zone = zone;
    }

    public String getStadium_name() {
        return stadium_name;
    }

    public void setStadium_name(String stadium_name) {
        this.stadium_name = stadium_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getTime_open() {
        return time_open;
    }

    public void setTime_open(String time_open) {
        this.time_open = time_open;
    }

    public String getTime_close() {
        return time_close;
    }

    public void setTime_close(String time_close) {
        this.time_close = time_close;
    }

    public String getLocation_x() {
        return location_x;
    }

    public void setLocation_x(String location_x) {
        this.location_x = location_x;
    }

    public String getLocation_y() {
        return location_y;
    }

    public void setLocation_y(String location_y) {
        this.location_y = location_y;
    }

    public ArrayList<StadiumCommentModel> getListComments() {
        return listComments;
    }

    public void setListComments(ArrayList<StadiumCommentModel> listComments) {
        this.listComments = listComments;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
