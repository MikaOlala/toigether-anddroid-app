package com.example.toigether.items;

import java.util.ArrayList;

public class Organization {
    private String id;
    private String name;
    private String description;
    private String image;

    private float rating;
    private String town;
    private ArrayList<String> gen_services;
    private ArrayList<String> categories;

    public Organization(String id, String name, String description, String image, float rating, String town,
                        ArrayList<String> gen_services, ArrayList<String> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.town = town;
        this.gen_services = gen_services;
        this.categories = categories;
    }

    public Organization(String town, ArrayList<String> gen_services, ArrayList<String> categories) {
        this.town = town;
        this.gen_services = gen_services;
        this.categories = categories;
    }

    public Organization() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<String> getGen_services() {
        return gen_services;
    }

    public void setGen_services(ArrayList<String> gen_services) {
        this.gen_services = gen_services;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }


    @Override
    public String toString() {
        return "Org: " +
                "\n id=" + id +
                "\n name=" + name +
                "\n description=" + description +
                "\n image=" + image +
                "\n rating=" + rating +
                "\n town=" + town +
                "\n services=" + gen_services +
                "\n categories=" + categories;
    }
}
