package com.example.mypetapplication.Bean;

public class BeanFriend {
    private int id;
    private String friendname;
    private String friendnickname;
    private String friendimg;
    private String friendcontent;
    private String friendcontentimg;
    private String frienddate;

    public BeanFriend(){};
    public BeanFriend(int id, String friendname,String friendnickname, String friendimg, String friendcontent,String friendcontentimg,String frienddate){
        this.id=id;
        this.friendname=friendname;
        this.friendnickname=friendnickname;
        this.friendimg=friendimg;
        this.friendcontent=friendcontent;
        this.friendcontentimg=friendcontentimg;
        this.frienddate=frienddate;
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

    public String getFriendnickname() {
        return friendnickname;
    }

    public void setFriendnickname(String friendnickname) {
        this.friendnickname = friendnickname;
    }

    public String getFriendimg() {
        return friendimg;
    }

    public void setFriendimg(String friendimg) {
        this.friendimg = friendimg;
    }

    public String getFriendcontent() {
        return friendcontent;
    }

    public void setFriendcontent(String friendcontent) {
        this.friendcontent = friendcontent;
    }

    public String getFriendcontentimg() {
        return friendcontentimg;
    }

    public void setFriendcontentimg(String friendcontentimg) {
        this.friendcontentimg = friendcontentimg;
    }

    public String getFrienddate() {
        return frienddate;
    }

    public void setFrienddate(String frienddate) {
        this.frienddate = frienddate;
    }
}