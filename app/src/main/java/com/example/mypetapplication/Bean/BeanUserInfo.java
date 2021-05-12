package com.example.mypetapplication.Bean;

public class BeanUserInfo {
    private String username;
    private String userimg;
    private String nickname;
    private String sign;
    private String address;

    public BeanUserInfo(){};
    public BeanUserInfo(String username,String userimg,String nickname,String address,String sign){
        this.username=username;
        this.userimg=userimg;
        this.nickname=nickname;
        this.address=address;
        this.sign=sign;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
