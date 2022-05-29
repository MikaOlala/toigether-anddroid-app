package com.example.toigether.items;

import java.util.ArrayList;

public class Request {
    private String email;
    private String phone;
    private String organizator;
    private String spec_services;
    private String communicationMethod;
    private String timestamp;

    public Request(String email, String phone, String organizator, String spec_services, String communicationMethod, String timestamp) {
        this.email = email;
        this.phone = phone;
        this.organizator = organizator;
        this.spec_services = spec_services;
        this.communicationMethod = communicationMethod;
        this.timestamp = timestamp;
    }

    public Request(String email, String phone, String organizator, String spec_services, String timestamp) {
        this.email = email;
        this.phone = phone;
        this.organizator = organizator;
        this.spec_services = spec_services;
        this.timestamp = timestamp;
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

    public String getOrganizator() {
        return organizator;
    }

    public void setOrganizator(String organizator) {
        this.organizator = organizator;
    }

    public String getSpec_services() {
        return spec_services;
    }

    public void setSpec_services(String spec_services) {
        this.spec_services = spec_services;
    }

    public String getCommunicationMethod() {
        return communicationMethod;
    }

    public void setCommunicationMethod(String communicationMethod) {
        this.communicationMethod = communicationMethod;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
