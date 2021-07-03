package com.kgec.collegeadminapp.Users;

public class NoticeUsers {

    private String Time,date,imageUrl,title,uid;

    public NoticeUsers() {
    }

    public NoticeUsers(String time, String date, String imageUrl, String title, String uid) {
        Time = time;
        this.date = date;
        this.imageUrl = imageUrl;
        this.title = title;
        this.uid = uid;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
