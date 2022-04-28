package com.example.toigether.items;

public class TeamMember {
    private String id;
    private String email;
    private String name;
    private String phone;
    private String position;
    private String service;

    public TeamMember(String id, String email, String name, String phone, String position, String service) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.service = service;
    }

    public TeamMember() {
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "TeamMember{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", service='" + service + '\'' +
                '}';
    }
}
