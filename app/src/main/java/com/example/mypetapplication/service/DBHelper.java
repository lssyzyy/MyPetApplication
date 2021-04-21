package com.example.mypetapplication.service;

import android.util.Log;

import com.example.mypetapplication.Bean.BeanUser;

import java.sql.*;
import java.util.ArrayList;

public class DBHelper {
    private static String DBDRIVER = "com.mysql.jdbc.Driver";
    private static String DBURL = "jdbc:mysql://10.0.2.2:3306/petmanager?serverTimezone=UTC&verifyServerCertificate=false&useSSL=false";
    private static String DBUSER = "root";
    private static String DBPASSWORD = "123456";
    private static Connection conn;

    public static Connection getConnection() {
                conn=null;
                try {
                    Class.forName(DBDRIVER);
                    conn=DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
                    if(conn!=null)
                    {
                        Log.i("msg","连接成功");
                    }
                    else
                    {
                        Log.i("msg","连接失败");
                    }
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        return conn;
    }
    public boolean UserLogin(String username,String pwd){
        Connection conn = getConnection();
        String sql="select * from pet_user where username="+username+" and userpassword="+pwd;
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            rs.last();
            int rowCount=rs.getRow();
            if(rowCount==0){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }
    public static ArrayList<BeanUser> queryAllUserContent() {
        Connection conn = getConnection();
        ArrayList<BeanUser> datas = new ArrayList<>();
        String sql = "select * from pet_user";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BeanUser data = null;
                int userid = rs.getInt(1);
                String username = rs.getString(2);
                String userpassword = rs.getString(3);
                data = new BeanUser(userid, username, userpassword);
                datas.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }
    public int UserInsert(String username,String userpwd) {
        Connection conn =  getConnection();
        int i = 0;
        String sql = "insert into pet_user (username,userpassword) values(?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, userpwd);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    public int UserDelete(int userid) {
        Connection conn = getConnection();
        int i = 0;
        String sql = "delete from pet_user where userid=" + userid;
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}