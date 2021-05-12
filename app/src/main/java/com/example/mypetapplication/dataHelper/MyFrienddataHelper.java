package com.example.mypetapplication.dataHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyFrienddataHelper extends SQLiteOpenHelper {
    private Context context;
    public MyFrienddataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "friendrdata", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS friend" +
                "(id integer primary key, " +
                "friendname TEXT," +
                "friendimg TEXT," +
                "friendconteng TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists friend");
    }
}
