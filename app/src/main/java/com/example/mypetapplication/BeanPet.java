package com.example.mypetapplication;

public class BeanPet {
    private String petimg;
    private String pettitle;
    private String pettopic;
    private String petprice;
    private String petcontent;
    public BeanPet(){};
    public BeanPet(String pettitle,String pettopic,String petprice,String petcontent){
        this.pettitle=pettitle;
        this.pettopic=pettopic;
        this.petprice=petprice;
        this.petcontent=petcontent;
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
}
