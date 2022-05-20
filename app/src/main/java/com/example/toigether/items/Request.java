package com.example.toigether.items;

import java.util.ArrayList;

public class Request {
    private String email;
    private String organizator_id;
    private String spec_services;

    public Request(String email, String organizator_id, String spec_services) {
        this.email = email;
        this.organizator_id = organizator_id;
        this.spec_services = spec_services;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganizator_id() {
        return organizator_id;
    }

    public void setOrganizator_id(String organizator_id) {
        this.organizator_id = organizator_id;
    }

    public String getSpec_services() {
        return spec_services;
    }

    public void setSpec_services(String spec_services) {
        this.spec_services = spec_services;
    }
}
