package com.example.toigether.items;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Objects;

public class Organization{
    private String id;
    private String name;
    private String description;
    private String image;

    private float rating;
    private String town;
    private int budget;
    private String location;
    private String organizator_id;
    private ArrayList<String> gen_services;
    private ArrayList<String> categories;

    public Organization(String id, String name, String description, String image, float rating, String town, int budget, String location, String organizator_id, ArrayList<String> gen_services, ArrayList<String> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.town = town;
        this.budget = budget;
        this.location = location;
        this.organizator_id = organizator_id;
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

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganizator_id() {
        return organizator_id;
    }

    public void setOrganizator_id(String organizator_id) {
        this.organizator_id = organizator_id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
