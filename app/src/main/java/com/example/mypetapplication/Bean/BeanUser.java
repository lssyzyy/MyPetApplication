package com.example.mypetapplication.Bean;

public class BeanUser {
    int userid;
    String username;
    String userpassword;

    public BeanUser(){};
    public BeanUser(int userid,String username,String userpassword){
        this.userid=userid;
        this.username=username;
        this.userpassword=userpassword;
    }
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
