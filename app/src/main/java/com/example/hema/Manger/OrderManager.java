package com.example.hema.Manger;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class OrderManager {
    private OrderBaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public OrderManager(Context c){
        context=c;
    }
    public OrderManager open() throws SQLException {
        dbHelper = new OrderBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void  close(){
        dbHelper.close();
    }
    public  void insert(String id,String cid,String tel,String time){//新建用户
        ContentValues values=new ContentValues();
        values.put(Order.OrderTable.ID,id);
        values.put(Order.OrderTable.CID,cid);
        values.put(Order.OrderTable.Time,time);
        values.put(Order.OrderTable.Tel,tel);
        database.insert(Order.Name,null,values);
    }
}
