package com.example.mypetapplication.Bean;

public class BeanPet {
    private String petimg;
    private String pettitle;
    private String pettopic;
    private String petprice;
    private String petcontent;
    private String petyimiao;
    public BeanPet(){};
    public BeanPet(String petimg,String pettitle,String pettopic,String petprice,String petcontent,String petyimiao){
        this.petimg=petimg;
        this.pettitle=pettitle;
        this.pettopic=pettopic;
        this.petprice=petprice;
        this.petcontent=petcontent;
        this.petyimiao=petyimiao;
    }

    public String getPetimg() {
        return petimg;
    }

    public void setPetimg(String petimg) {
        this.petimg = petimg;
    }

    public String getPettitle() {
        return pettitle;
    }

    public void setPettitle(String pettitle) {
        this.pettitle = pettitle;
    }

    public String getPettopic() {
        return pettopic;
    }

    public void setPettopic(String pettopic) {
        this.pettopic = pettopic;
    }

    public String getPetprice() {
        return petprice;
    }

    public void setPetprice(String petprice) {
        this.petprice = petprice;
    }

    public String getPetcontent() {
        return petcontent;
    }

    public void setPetcontent(String petcontent) {
        this.petcontent = petcontent;
    }

    public String getPetyimiao() {
        return petyimiao;
    }

    public void setPetyimiao(String petyimiao) {
        this.petyimiao = petyimiao;
    }

}
