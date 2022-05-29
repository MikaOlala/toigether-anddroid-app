package com.example.toigether.items;

public class Service {
    private String name;
    private String description;
    private String gen_services;
    private String duration;
    private String price; // why is dis string....
    private String organizator_id;

    public Service(String name, String description, String gen_services,
                   String duration, String price, String organizator_id) {
        this.name = name;
        this.description = description;
        this.gen_services = gen_services;
        this.duration = duration;
        this.price = price;
        this.organizator_id = organizator_id;
    }

    public Service(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Service() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGen_services() {
        return gen_services;
    }

    public void setGen_services(String gen_services) {
        this.gen_services = gen_services;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrganizator_id() {
        return organizator_id;
    }

    public void setOrganizator_id(String organizator_id) {
        this.organizator_id = organizator_id;
    }
}
