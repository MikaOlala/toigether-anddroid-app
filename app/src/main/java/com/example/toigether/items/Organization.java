package com.example.toigether.items;

public class Organization {
    private Long id;
    private String name;
    private String description;
    private int image;

    private float rating;
    private String town;

    public Organization(Long id, String name, String description, int image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Organization(Long id, String name, String description, int image, float rating, String town) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.town = town;
    }

    public Organization() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "Organization: \n" +
                "id=" + id +
                "\n name=" + name +
                "\n description=" + description +
                "\n image=" + image +
                "\n rating=" + rating +
                "\n town=" + town;
    }
}
