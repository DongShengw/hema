package com.example.hema;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hema.adapter.AwaitAdapter;
import com.example.hema.demo.Await;
import com.example.hema.demo.Wait;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Activity_await extends AppCompatActivity implements AwaitAdapter.AItemClickListener{
    public List<Await> awaitList=new ArrayList<>();
    AwaitAdapter adapter;
    Await await_rec;
    private static Connection conn = null;
    private static PreparedStatement pst = null;
    private Handler handler = new Handler() {
        @Override
        //书写handleMessage方法，接受信息
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj!=null){
                awaitList.addAll((List) msg.obj);
                adapter.notifyDataSetChanged();
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_await);
//        initAwait();
        Thread_initAwait thread_initAwait=new Thread_initAwait();
        thread_initAwait.start();
        RecyclerView recyclerView=(RecyclerView)  findViewById(R.id.recycle_await);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new AwaitAdapter(awaitList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

//    private void initAwait() {
//        Await await1=new Await("1",R.drawable.ic_wait,R.drawable.ic_wait,"32");
//        awaitList.add(await1);
//    }

    @Override
    public void AItemReceiveClickListener(View view,int position){
        await_rec=awaitList.get(position);
        awaitList.remove(position);
        adapter.notifyDataSetChanged();
        Thread_recAwait thread_recAwait=new Thread_recAwait();
        thread_recAwait.start();
    }
    class  Thread_initAwait extends Thread{
        public void run() {
            DecimalFormat df   = new DecimalFormat("######0.00");
            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn!=null) {
                    String sql="Select max(OrderId) from Await";
                    pst=conn.prepareStatement(sql);
                    ResultSet rs=pst.executeQuery(sql);
                    int a=0;
                    if(rs.next()){
                        a=rs.getInt(1);
                    }
                    List<Await> awaitList_1=new ArrayList<>();
                    for (int i = 1; i <=a; i++) {
                        sql ="Select OrderId,ImgId,Price,Amount from Await where OrderId='"+i+"'";
                        pst=conn.prepareStatement(sql);
                        rs=pst.executeQuery(sql);
                        int k=0;
                        double sum=0;
                        List<Integer> list=new ArrayList<>();
                        while(rs.next()){
                            sum=sum+Double.valueOf(rs.getString(3))*Integer.valueOf(rs.getString(4));
                            list.add(rs.getInt(2));
                        }
                        Await await;
                        if(list.size()>=2){
                            await=new Await(String.valueOf(a),list.get(0).intValue(),list.get(1).intValue(),String.valueOf(df.format(sum)),list.size());
                        }else{
                            await=new Await(String.valueOf(a),list.get(0).intValue(),0,String.valueOf(df.format(sum)),list.size());
                        }
                        awaitList_1.add(await);
                    }
                    Message message = handler.obtainMessage();
                    message.obj = awaitList_1;
                    handler.sendMessage(message);
                    conn.setAutoCommit(false);
                    conn.commit();
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    class  Thread_recAwait extends Thread{
        public void run() {
            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn!=null) {
                    conn.setAutoCommit(false);
                    String sql="Delete from Await where OrderId='"+await_rec.getOrderID()+"'";
                    pst=conn.prepareStatement(sql);
                    pst.execute();
                    conn.commit();
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}