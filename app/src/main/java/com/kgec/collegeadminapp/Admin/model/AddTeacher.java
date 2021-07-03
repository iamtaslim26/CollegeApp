package com.kgec.collegeadminapp.Admin.model;

public class AddTeacher {

    private String email,imageUrl,key,name,post;

    public AddTeacher() {
    }

    public AddTeacher(String email, String imageUrl, String key, String name, String post) {
        this.email = email;
        this.imageUrl = imageUrl;
        this.key = key;
        this.name = name;
        this.post = post;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
