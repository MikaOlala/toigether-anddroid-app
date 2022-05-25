package com.example.toigether.items;

public class Category {
    private String name;
    private String image;
    private int interestIndex;

    public Category(String name, String image, int interestIndex) {
        this.name = name;
        this.image = image;
        this.interestIndex = interestIndex;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getInterestIndex() {
        return interestIndex;
    }

    public void setInterestIndex(int interestIndex) {
        this.interestIndex = interestIndex;
    }
}
