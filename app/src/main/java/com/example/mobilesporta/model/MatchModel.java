package com.example.mobilesporta.model;

public class MatchModel {

    private String club_home_id;
    private String club_away_id;
    private String user_created_id;
    private String stadium_id;
    private String date;
    private String time;
    private String time_amount;
    private String status;
    private String description;
    private String phone_number;

    public MatchModel() {
    }

    public MatchModel(String club_home_id, String club_away_id, String user_created_id, String stadium_id, String date, String time, String time_amount, String status, String description, String phone_number) {
        this.club_home_id = club_home_id;
        this.club_away_id = club_away_id;
        this.user_created_id = user_created_id;
        this.stadium_id = stadium_id;
        this.date = date;
        this.time = time;
        this.time_amount = time_amount;
        this.status = status;
        this.description = description;
        this.phone_number = phone_number;
    }

    public String getClub_home_id() {
        return club_home_id;
    }

    public MatchModel setClub_home_id(String club_home_id) {
        this.club_home_id = club_home_id;
        return this;
    }

    public String getClub_away_id() {
        return club_away_id;
    }

    public MatchModel setClub_away_id(String club_away_id) {
        this.club_away_id = club_away_id;
        return this;
    }

    public String getUser_created_id() {
        return user_created_id;
    }

    public MatchModel setUser_created_id(String user_created_id) {
        this.user_created_id = user_created_id;
        return this;
    }

    public String getStadium_id() {
        return stadium_id;
    }

    public MatchModel setStadium_id(String stadium_id) {
        this.stadium_id = stadium_id;
        return this;
    }

    public String getDate() {
        return date;
    }

    public MatchModel setDate(String date) {
        this.date = date;
        return this;
    }

    public String getTime() {
        return time;
    }

    public MatchModel setTime(String time) {
        this.time = time;
        return this;
    }

    public String getTime_amount() {
        return time_amount;
    }

    public void setTime_amount(String time_amount) {
        this.time_amount = time_amount;
    }

    public String getStatus() {
        return status;
    }

    public MatchModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MatchModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public MatchModel setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }
}
