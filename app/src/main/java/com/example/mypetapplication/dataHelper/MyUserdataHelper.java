package com.example.mypetapplication.dataHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyUserdataHelper extends SQLiteOpenHelper {
    private Context context;
    public MyUserdataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "userdata", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user" +
                "(userid integer primary key, " +
                "username TEXT," +
                "pwd TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS userinfo" +
                "(username TEXT, " +
                "userimg varchar(10000)," +
                "nickname TEXT," +
                "sign TEXT," +
                "address TEXT," +
                "FOREIGN KEY(username) REFERENCES user(username))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists userinfo");
    }
}
