package com.example.lotuscomputer.biker;

/**
 * Created by Lotus Computer on 04-Jun-17.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String title, String location,String price,String posted_by,String posted_on,String description,String address,String phone,String email, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO FOOD VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, location);
        statement.bindString(3, price);
        statement.bindString(4, posted_by);
        statement.bindString(5, posted_on);
        statement.bindString(6, description);
        statement.bindString(7, address);
        statement.bindString(8, phone);
        statement.bindString(9, email);
        statement.bindBlob(10, image);

        statement.executeInsert();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}