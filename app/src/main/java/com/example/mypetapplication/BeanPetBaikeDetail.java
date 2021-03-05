package com.example.mypetapplication;

import android.widget.TextView;

public class BeanPetBaikeDetail {
    private String img;
    private String name;
    private String engname;
    private String characters;
    private String nation;
    private String easyOfDisease;
    private String life;
    private String price;
    private String feature;
    private String characterFeature;
    private String careKnowledge;
    private String feedPoints;
    public BeanPetBaikeDetail(String img, String name, String engname, String characters, String nation, String easyOfDisease, String life, String price, String feature, String characterFeature, String careKnowledge, String feedPoints) {
        this.img = img;
        this.name = name;
        this.engname = engname;
        this.characters = characters;
        this.nation = nation;
        this.easyOfDisease = easyOfDisease;
        this.life = life;
        this.price = price;
        this.feature = feature;
        this.characterFeature = characterFeature;
        this.careKnowledge = careKnowledge;
        this.feedPoints = feedPoints;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getEasyOfDisease() {
        return easyOfDisease;
    }

    public void setEasyOfDisease(String easyOfDisease) {
        this.easyOfDisease = easyOfDisease;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getCharacterFeature() {
        return characterFeature;
    }

    public void setCharacterFeature(String characterFeature) {
        this.characterFeature = characterFeature;
    }

    public String getCareKnowledge() {
        return careKnowledge;
    }

    public void setCareKnowledge(String careKnowledge) {
        this.careKnowledge = careKnowledge;
    }

    public String getFeedPoints() {
        return feedPoints;
    }

    public void setFeedPoints(String feedPoints) {
        this.feedPoints = feedPoints;
    }
}
