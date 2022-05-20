package com.example.toigether.items;

import java.util.ArrayList;

public class User {
    private String email;
    private String phone;
    private String name;
    private String avatar;
    private ArrayList<String> favourite;

    public User(String email, String phone, String name, String avatar, ArrayList<String> favourite) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.avatar = avatar;
        this.favourite = favourite;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getFavourite() {
        return favourite;
    }

    public void setFavourite(ArrayList<String> favourite) {
        this.favourite = favourite;
    }
}
