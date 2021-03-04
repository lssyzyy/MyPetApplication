package com.example.mypetapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String PET_TITLE = "pettitle";
    public static final String PET_TOPIC = "pettopic";
    public static final String PET_PRICE = "petprice";
    public static final String PET_CONTENT = "petcontent";
    public static final String PETDB = "petsdb";
    private Context context;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "petdata", null, 1);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS petsdb" +
                "(id integer primary key, "+
                "pettitle text," +
                "pettopic text," +
                "petprice text," +
                "petcontent text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists petsdb");
    }
}
