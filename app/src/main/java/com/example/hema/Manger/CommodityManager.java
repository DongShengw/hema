package com.example.hema.Manger;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CommodityManager {
    private CommodityBaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public CommodityManager(Context c){
        context=c;
    }
    public CommodityManager open()throws SQLException {
        dbHelper = new CommodityBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void  close(){
        dbHelper.close();
    }
    public void insert(String id,String name,String amo,String pri){
        ContentValues values=new ContentValues();
        values.put(Commodity.CommodityTable.ID,id);
        values.put(Commodity.CommodityTable.Name,name);
        values.put(Commodity.CommodityTable.Amo,amo);
        values.put(Commodity.CommodityTable.Pri,pri);
        database.insert(Commodity.Name,null,values);
    }

}
