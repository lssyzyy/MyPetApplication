package com.example.mypetapplication.Bean;

public class BeanFriendComment {
    private int friendid;
    private String friendcommentnickname;
    private String friendcommentcontent;


    public BeanFriendComment(){};
    public BeanFriendComment(int friendid,String friendcommentnickname,String friendcommentcontent){
        this.friendid=friendid;
        this.friendcommentnickname=friendcommentnickname;
        this.friendcommentcontent=friendcommentcontent;
    }

    public int getFriendid() {
        return friendid;
    }

    public void setFriendid(int friendid) {
        this.friendid = friendid;
    }

    public String getFriendcommentnickname() {
        return friendcommentnickname;
    }

    public void setFriendcommentnickname(String friendcommentnickname) {
        this.friendcommentnickname = friendcommentnickname;
    }

    public String getFriendcommentcontent() {
        return friendcommentcontent;
    }

    public void setFriendcommentcontent(String friendcommentcontent) {
        this.friendcommentcontent = friendcommentcontent;
    }
}
