package com.example.asimg.sams;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

public class LoginData extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SAMS.db";
    public static final String TABLE_NAME = "Admin";
    public static final String COL_1 = "Name";
    public static final String COL_2 = "ID";
    public static final String COL_3 = "Desg";
    public static final String COL_4 = "HQ";
    public static final String COL_5 = "Pwd";
    public static final String COL_6 = "Email";
    public static final String COL_7 = "Mob";
    public static final String COL_8 = "Admn";

    public LoginData(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + "(Name TEXT, ID TEXT PRIMARY KEY, Desg TEXT, HQ TEXT, Pwd TEXT, Email TEXT, Mob INTEGER, Admn String)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    public boolean insertdata(String name, String id, String desg, String hq, String pwd, String email, String mob, String admn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,id);
        contentValues.put(COL_3,desg);
        contentValues.put(COL_4,hq);
        contentValues.put(COL_5,pwd);
        contentValues.put(COL_6,email);
        contentValues.put(COL_7,mob);
        contentValues.put(COL_8,admn);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
}
