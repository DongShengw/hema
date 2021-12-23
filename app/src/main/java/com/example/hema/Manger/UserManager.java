package com.example.hema.Manger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserManager {
    private UserBaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public UserManager(Context c){
        context=c;
    }
    public UserManager open() throws SQLException {
        dbHelper = new UserBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void  close(){
        dbHelper.close();
    }
    public  void insert(String id,String pwd,String tel){//新建用户
        ContentValues values=new ContentValues();
        values.put(User.UserTable.ID,id);
        values.put(User.UserTable.Pwd,pwd);
        values.put(User.UserTable.Tel,tel);
        database.insert(User.Name,null,values);
    }
    public int resetPwd(String id,String pwd){//改密码
        database = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User.UserTable.Pwd,pwd);
        String[] args = {String.valueOf(id)};
        return database.update("User",cv,"UserID=?",args);
    }
    public boolean check(String id,String pwd){ //检查用户账户密码是否一致
        database = dbHelper.getWritableDatabase();
        String Query="select UserPwd,UserID from User where UserID= '"+id+"' and UserPwd ='"+pwd+"'";
        Cursor cursor=null;
        try{
            cursor=database.rawQuery(Query,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(cursor!=null&&cursor.getCount()>0){
            cursor.close();
            return  true;
        }else {
            return  false;
        }
    }

}
