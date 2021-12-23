package com.example.hema.Manger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CommodityBaseHelper extends SQLiteOpenHelper {
    public CommodityBaseHelper(Context context) {
        super(context, "Commodity.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Commodity.Name + "(" +Commodity.CommodityTable.ID +" TEXT primary key, "+Commodity.CommodityTable.Name+" TEXT,"+Commodity.CommodityTable.Amo+" TEXT,"+Commodity.CommodityTable.Pri+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
