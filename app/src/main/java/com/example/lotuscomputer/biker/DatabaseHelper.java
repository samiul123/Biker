package com.example.lotuscomputer.biker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Lotus Computer on 01-Jun-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "bike5.db";
    public static final String TABLE_NAME = "bikers_info_5";
    public static  int id = 0;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (TITLE TEXT ,LOCATION TEXT,PRICE TEXT,POSTED_BY TEXT,POSTED_ON TEXT,DESCRIPTION TEXT,ADDRESS TEXT,PHONE TEXT,EMAIL TEXT,IMAGE BLOB,CATEGORY TEXT,ID INTEGER PRIMARY KEY AUTOINCREMENT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String title,String location,int price,String posted_by,String posted_on,String description,String address,String phone,String email,byte[] image,String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO "+ TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL)";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, location);
        statement.bindLong(3, price);
        statement.bindString(4, posted_by);
        statement.bindString(5, posted_on);
        statement.bindString(6, description);
        statement.bindString(7, address);
        statement.bindString(8, phone);
        statement.bindString(9, email);
        statement.bindBlob(10, image);
        statement.bindString(11,category);
        statement.executeInsert();

        return true;
    }

    public Cursor getAsRequested(String sql)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(sql,null);
        System.out.println(sql);
        if(res.getCount() == 0) System.out.println("nothing");
        return  res;
    }



}

