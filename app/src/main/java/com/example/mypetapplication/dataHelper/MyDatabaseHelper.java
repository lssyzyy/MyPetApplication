package com.example.mypetapplication.dataHelper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "petdata", null, 1);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS petsdb" +
                "(id integer primary key, " +
                "petimg varchar(10000),"+
                "pettitle text," +
                "pettopic text," +
                "petprice text," +
                "petcontent text," +
                "petyimiao text," +
                "petusername text)");
        db.execSQL("CREATE TABLE IF NOT EXISTS petsadopt" +
                "(id integer primary key, " +
                "petimg varchar(10000),"+
                "pettitle text," +
                "pettopic text," +
                "petprice text," +
                "petcontent text," +
                "petyimiao text," +
                "petusername text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists petsdb");
        db.execSQL("drop table if exists petsadopt");
    }
}
