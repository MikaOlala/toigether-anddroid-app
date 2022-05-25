package com.example.toigether.items;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {
    private String id;
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

    protected User(Parcel in) {
        id = in.readString();
        email = in.readString();
        phone = in.readString();
        name = in.readString();
        avatar = in.readString();
        favourite = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(name);
        parcel.writeString(avatar);
        parcel.writeList(favourite);
    }

}
