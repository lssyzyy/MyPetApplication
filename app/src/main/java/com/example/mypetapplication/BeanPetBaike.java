package com.example.mypetapplication;

public class BeanPetBaike {
    private String imageView;
    private String name;
    private String engname;
    private String price;

    public BeanPetBaike(String imageView, String name, String engname, String price) {
        this.imageView = imageView;
        this.name = name;
        this.engname = engname;
        this.price = price;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngname() {
        return engname;
    }

    public void setEngname(String engname) {
        this.engname = engname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
