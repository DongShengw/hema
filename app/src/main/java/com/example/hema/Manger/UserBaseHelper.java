package com.example.hema.Manger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserBaseHelper extends SQLiteOpenHelper {
    public UserBaseHelper(Context context) {
        super(context, "User.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + User.Name + "(" +User.UserTable.ID +" TEXT primary key, "+User.UserTable.Pwd+" TEXT,"+User.UserTable.Tel+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
