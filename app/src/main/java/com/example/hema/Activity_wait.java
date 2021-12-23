package com.example.hema;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hema.adapter.WaitAdapter;
import com.example.hema.demo.Cart;
import com.example.hema.demo.Order;
import com.example.hema.demo.Wait;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Activity_wait extends AppCompatActivity implements WaitAdapter.ItemClickListener{
    public List<Wait> waitList=new ArrayList<>();
    private static Connection conn = null;
    private static PreparedStatement pst = null;
    WaitAdapter adapter;
    Wait wait_del,wait_cont;
    private Handler handler = new Handler() {
        @Override
        //书写handleMessage方法，接受信息
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj!=null){
                waitList.addAll((List) msg.obj);
                adapter.notifyDataSetChanged();
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
//        initWait();
        Thread_initWait thread_initWait=new Thread_initWait();
        thread_initWait.start();
        RecyclerView  recyclerView=(RecyclerView)  findViewById(R.id.recycle_wait);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new WaitAdapter(waitList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void initWait() {
        Wait  wait1=new Wait("1",R.drawable.ic_wait,R.drawable.ic_wait,"32.7",1);
        waitList.add(wait1);
        waitList.add(wait1);
    }

    @Override
    public void ItemStopClickListener(View view, int position) {
        wait_del=waitList.get(position);
        waitList.remove(position);
        adapter.notifyDataSetChanged();
        Thread_delWait thread_delWait=new Thread_delWait();
        thread_delWait.start();
    }

    @Override
    public void ItemContiClickListener(View view, int position) {
        wait_cont=waitList.get(position);
        waitList.remove(position);
        adapter.notifyDataSetChanged();
        Thread_conWait thread_conWait=new Thread_conWait();
        thread_conWait.start();
    }

    class  Thread_initWait extends Thread{
        public void run() {
            DecimalFormat df   = new DecimalFormat("######0.00");
            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn!=null) {
                    String sql="Select max(OrderId) from Wait";
                    pst=conn.prepareStatement(sql);
                    ResultSet rs=pst.executeQuery(sql);
                    int a=0;
                    if(rs.next()){
                        a=rs.getInt(1);
                    }
                    List<Wait> waitList_1=new ArrayList<>();
                    for (int i = 1; i <=a; i++) {
                        sql ="Select OrderId,ImgId,Price,Amount from Wait where OrderId='"+i+"'";
                        pst=conn.prepareStatement(sql);
                        rs=pst.executeQuery(sql);
                        int k=0;
                        double sum=0;
                        List<Integer> list=new ArrayList<>();
                        while(rs.next()){
                            sum=sum+Double.valueOf(rs.getString(3))*Integer.valueOf(rs.getString(4));
                            list.add(rs.getInt(2));
                        }
                        Wait wait;
                        if(list.size()>=2){
                            wait=new Wait(String.valueOf(a),list.get(0).intValue(),list.get(1).intValue(),String.valueOf(df.format(sum)),list.size());
                        }else{
                            wait=new Wait(String.valueOf(a),list.get(0).intValue(),0,String.valueOf(df.format(sum)),list.size());
                        }
                        waitList_1.add(wait);
                    }
                    Message message = handler.obtainMessage();
                    message.obj = waitList_1;
                    handler.sendMessage(message);
                    conn.setAutoCommit(false);
                    conn.commit();
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    class  Thread_delWait extends Thread{
        public void run() {
            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn!=null) {
                    conn.setAutoCommit(false);
                    String sql="Delete from Wait where OrderId='"+wait_del.getOrderID()+"'";
                    pst=conn.prepareStatement(sql);
                    pst.execute();

                    conn.commit();
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    class  Thread_conWait extends Thread {
        public void run() {

            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn != null) {
                    List<Order> orderList=new ArrayList<>();
                    conn.setAutoCommit(false);
                    String sql = "Select * from Wait where OrderId='"+wait_cont.getOrderID()+"'";
                    pst = conn.prepareStatement(sql);
                    ResultSet rs=pst.executeQuery();
                    while(rs.next()){
                        Order order=new Order(String.valueOf(rs.getInt(1)),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getInt(5));
                        orderList.add(order);
                    }
                    for (int i = 0; i < orderList.size(); i++) {
                        sql = "Insert into Await(OrderId,Name,ImgId,Price,Amount) values(?,?,?,?,?)";
                        pst=conn.prepareStatement(sql);
                        pst.setInt(1,Integer.valueOf(orderList.get(i).getOrderId()));
                        pst.setString(2,orderList.get(i).getName());
                        pst.setInt(3,orderList.get(i).getImgId());
                        pst.setString(4,orderList.get(i).getSum());
                        pst.setInt(5,orderList.get(i).getNum());
                        pst.execute();
                    }
                    sql="Delete from Wait where OrderId='"+wait_cont.getOrderID()+"'";
                    pst=conn.prepareStatement(sql);
                    pst.execute();
                    conn.commit();

                }

            }catch (Exception e) {
                System.out.println(e);
                System.out.println(1111);
            }
        }
    }
}