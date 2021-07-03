package com.kgec.collegeadminapp.Users;


public class UsersGallery {

    private String Date,ImageUrl,Time,uid;

    public UsersGallery(){}

    public UsersGallery(String Date,String ImageUrl,String Time,String uid){

        this.Date=Date;
        this.ImageUrl= ImageUrl;
        this.Time=Time;
        this.uid=uid;

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
