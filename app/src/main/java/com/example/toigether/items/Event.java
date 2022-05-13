package com.example.toigether.items;

import java.util.ArrayList;

public class Event {
    private String id;
    private String name;
    private String category;
    private String description;
    private String cost;
    private ArrayList<String> images;
    private String menu;
    private ArrayList<String> services;
    private String decorations;
    private String organizator_id;

    public Event(String id, String name, String category, String description, String cost, ArrayList<String> images, String menu, ArrayList<String> services, String decorations, String organizator_id) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.cost = cost;
        this.images = images;
        this.menu = menu;
        this.services = services;
        this.decorations = decorations;
        this.organizator_id = organizator_id;
    }

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public String getDecorations() {
        return decorations;
    }

    public void setDecorations(String decorations) {
        this.decorations = decorations;
    }

    public String getOrganizator_id() {
        return organizator_id;
    }

    public void setOrganizator_id(String organizator_id) {
        this.organizator_id = organizator_id;
    }

    @Override
    public String toString() {
        return "Events{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", images=" + images +
                ", menu='" + menu + '\'' +
                ", services=" + services +
                ", decorations='" + decorations + '\'' +
                ", organizator_id='" + organizator_id + '\'' +
                '}';
    }
}
