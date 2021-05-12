package com.example.mypetapplication.Bean;

public class BeanFriend {
    private int id;
    private String friendname;
    private int friendimg;
    private String friendcontent;

    public BeanFriend(){};
    public BeanFriend(int id, String friendname, int friendimg, String friendcontent){
        this.id=id;
        this.friendname=friendname;
        this.friendimg=friendimg;
        this.friendcontent=friendcontent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }

    public int getFriendimg() {
        return friendimg;
    }

    public void setFriendimg(int friendimg) {
        this.friendimg = friendimg;
    }

    public String getFriendcontent() {
        return friendcontent;
    }

    public void setFriendcontent(String friendcontent) {
        this.friendcontent = friendcontent;
    }
}