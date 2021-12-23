package com.example.hema.ui.home;

import static com.example.hema.databinding.FragmentHomeBinding.*;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hema.MySQLConnections;
import com.example.hema.R;

import com.example.hema.adapter.MyAdapter;
import com.example.hema.databinding.FragmentHomeBinding;
import com.example.hema.demo.Cart;
import com.example.hema.demo.ContentData;
import com.example.hema.demo.Goods;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class HomeFragment extends Fragment implements MyAdapter.ItemClickListener{

    private FragmentHomeBinding binding;
    private List<Integer> image=new ArrayList<>();
    private List<String> title=new ArrayList<>();
    private Cart cart;
    MyAdapter adapter;
    private Handler mHandler=new Handler();
    private List<ContentData> dataList = new ArrayList<>();
    private static Connection conn = null;
    private static PreparedStatement pst = null;
    View root;

    private Handler handler = new Handler() {
        @Override
        //书写handleMessage方法，接受信息
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj!=null){
                dataList.addAll((List) msg.obj);
                adapter.notifyDataSetChanged();
            }

        }
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = inflate(inflater, container, false);
        root = binding.getRoot();

//        ContentData contentData_1 = new ContentData(null,0);
//        ContentData contentData_2 = new ContentData(null,1);
//        dataList.add(contentData_1);
//        dataList.add(contentData_2);

        Thread_initGoods initGoods=new Thread_initGoods();
        initGoods.start();

        //轮播初始化
        initData();


        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycle_home);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MyAdapter(dataList,image,title);
        adapter.setOnItemClickListener((MyAdapter.ItemClickListener) this);
        recyclerView.setAdapter(adapter);

        return root;
    }
    private void initData() {

        image.add(R.drawable.head_home);
        image.add(R.drawable.head_hanbao);
        image.add(R.drawable.head_mixian);
//        image.add(R.drawable.lb_three);
//        image.add(R.drawable.lb_five);
        title.add("不一样的火锅");
        title.add("美味汉堡");
        title.add("米线推荐");
//        title.add("家具打折");
//        title.add("手表上新");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void ItemAddCommodity(View view, int position) {
        ContentData data = dataList.get(position);
        cart=new Cart(data.getGoods().getName(),data.getGoods().getId(),data.getGoods().getPrice(),"1");
        Thread_AddGoods addGoods=new Thread_AddGoods();
        addGoods.start();
        adapter.notifyDataSetChanged();
//        dataList.remove(position);
    }
    public Cart AddCart(){
        return cart;
    }

    class  Thread_initGoods extends Thread{
        public void run() {

            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String sql ="Select CommodityID,CommodityName,CommodityPrice,CommodityType from Commodity";
                if (conn!=null) {
                    pst = conn.prepareStatement(sql);
                    conn.setAutoCommit(false);
                    ResultSet rs = pst.executeQuery();
                    List<ContentData> DateList_1=new ArrayList<>();
                    while (rs.next()) {
                        Goods goods=new Goods(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                        ContentData contentData=new ContentData(goods,0);
                        DateList_1.add(contentData);
                    }
                    Message message = handler.obtainMessage();
                    message.obj = DateList_1;
                    handler.sendMessage(message);
                    conn.commit();
                    rs.close();
                    pst.close();
                }
            }catch (Exception e){
                System.out.println(e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this,"请输入正确的语句",Toast.LENGTH_SHORT).show();
                        System.out.println("1111");
                    }
                });
            }
        }
    }

    class  Thread_AddGoods extends Thread{
        public void run() {

            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String sql ="Select * from Cart where name='"+cart.getName()+"'";
                if (conn!=null) {
                    pst = conn.prepareStatement(sql);
                    conn.setAutoCommit(false);
                    ResultSet rs = pst.executeQuery();//创建数据对象
                    if(rs.next()) {
                       sql="Update Cart set amount=amount+1 where name= '"+cart.getName()+"'";
                       pst=conn.prepareStatement(sql);
                       pst.execute(sql);
                    }else{
                       sql="Insert into Cart values('"+cart.getName()+"','"+cart.getImgId()+"','"+cart.getPrice()+"','"+cart.getAmount()+"')";
                       pst=conn.prepareStatement(sql);
                       pst.execute(sql);
                    }
                    conn.commit();
                    rs.close();
                    pst.close();
                }
            }catch (Exception e){
                System.out.println(e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this,"请输入正确的语句",Toast.LENGTH_SHORT).show();
                        System.out.println("1111");
                    }
                });
            }
        }
    }
}