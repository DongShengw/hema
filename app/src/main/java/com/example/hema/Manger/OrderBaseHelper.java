package com.example.hema.Manger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderBaseHelper extends SQLiteOpenHelper {

    public OrderBaseHelper(Context context) {
        super(context, "Orders.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Order.Name + "(" +Order.OrderTable.ID +" TEXT primary key, "+Order.OrderTable.CID+" TEXT,"+Order.OrderTable.Tel+" TEXT,"+Order.OrderTable.Time+" TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
