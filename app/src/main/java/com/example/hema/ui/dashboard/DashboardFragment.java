package com.example.hema.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hema.MainActivity;
import com.example.hema.MySQLConnections;
import com.example.hema.R;
import com.example.hema.adapter.CartAdapter;
import com.example.hema.adapter.CartAdapter1;
import com.example.hema.databinding.FragmentDashboardBinding;
import com.example.hema.demo.Cart;
import com.example.hema.demo.Cart1;
import com.example.hema.ui.home.HomeFragment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment implements CartAdapter.ItemClickListener,CartAdapter1.ItemClickListener {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    public List<Cart> cartList=new ArrayList<>();
    public List<Cart1> cart1List=new ArrayList<>();
    public List<Cart>  cartList_pay=new ArrayList<>();
    public List<Cart>  cartList_del=new ArrayList<>();
    Cart cart_del;
    Cart cart_rec;
    Cart cart_add;
    private static Connection conn = null;
    private static PreparedStatement pst = null;
    CartAdapter adapter;
    CartAdapter1 adapter1;
    View root;
    private Handler handler = new Handler() {
        @Override
        //书写handleMessage方法，接受信息
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj!=null){
                cartList.addAll((List) msg.obj);
                adapter.notifyDataSetChanged();
            }

        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        RadioButton btn_often_buy=root.findViewById(R.id.btn_often_buy);
        RadioButton btn_shopping_cart=root.findViewById(R.id.btn_shopping_cart);
        Button btn_delete=root.findViewById(R.id.delete);
        TextView tv_total_price=root.findViewById(R.id.tv_total_price);
        LinearLayout layout_content_all=root.findViewById(R.id.layout_content_all);
        CheckBox checkbox_all=root.findViewById(R.id.chekbox_all);
        TextView tv_go_to_pay=root.findViewById(R.id.tv_go_to_pay);
        btn_shopping_cart.setTextColor(Color.parseColor("#2196F3"));
        btn_shopping_cart.performClick();

        //线程
        Thread_initCart thread_initCart=new Thread_initCart();
        thread_initCart.start();

//        initCart();

        SelectCommodity();

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(cartList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);


        initCart1();

        RecyclerView recyclerView1 = (RecyclerView) root.findViewById(R.id.recycle1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter1 = new CartAdapter1(cart1List);
        adapter1.setOnItemClickListener(this);
        recyclerView1.setAdapter(adapter1);

        btn_often_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                recyclerView1.setVisibility(View.VISIBLE);
                layout_content_all.setVisibility(View.GONE);
                btn_often_buy.setTextColor(Color.parseColor("#2196F3"));
                btn_shopping_cart.setTextColor(Color.parseColor("#FF000000"));
                btn_delete.setVisibility(View.GONE);
            }
        });

        btn_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_delete.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView1.setVisibility(View.GONE);
                layout_content_all.setVisibility(View.VISIBLE);
                btn_shopping_cart.setTextColor(Color.parseColor("#2196F3"));
                btn_often_buy.setTextColor(Color.parseColor("#FF000000"));
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=true;
                int number=0;
                for (int i = 0; i < cartList.size(); i++) {
                    if(cartList.get(i).isFlag()==false){
                        number++;
                    }
                }
                if(number==cartList.size()){
                    flag=false;
                }
                if(flag==false){
                    new AlertDialog.Builder(getContext()).setTitle("提示")
                            .setMessage("请选择需要删除的商品")
                            .setPositiveButton("确定",new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }).show();
                    flag=false;
                }
                else {
                    new AlertDialog.Builder(getContext()).setTitle("删除选定商品")
                            .setMessage("确定删除选定商品")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                @Override

                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    int i=0;
                                    while(i<cartList.size()){
                                        if(cartList.get(i).isFlag()){
                                            cartList_del.add(cartList.get(i));
                                            cartList.remove(i);
//                                            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycle);
//                                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                                            adapter = new CartAdapter(cartList);
//                                            //adapter.setOnItemClickListener(this);
//                                            recyclerView.setAdapter(adapter);
                                        }else {
                                            i++;
                                        }
                                    }
                                    Thread_initBtndel initBtndel=new Thread_initBtndel();
                                    initBtndel.start();
                                    adapter.notifyDataSetChanged();
                                    SelectCommodity();
                                    if(cartList.size()==0){
                                        checkbox_all.setChecked(false);
                                    }

                                }
                            }).setNegativeButton("取消",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    }).show();
                }

            }
        });

        checkbox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox_all.isChecked()){
                    for (int i = 0; i <cartList.size(); i++) {
                        cartList.get(i).setFlag(true);
                    }
                    adapter.notifyDataSetChanged();
                    SelectCommodity();

                }else {
                    for (int i = 0; i <cartList.size(); i++) {
                        cartList.get(i).setFlag(false);
                    }
                    adapter.notifyDataSetChanged();
                    SelectCommodity();

                }
            }
        });

        tv_go_to_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=true;
                int number=0;
                for (int i = 0; i < cartList.size(); i++) {
                    if(cartList.get(i).isFlag()==false){
                        number++;
                    }
                }
                if(number==cartList.size()){
                    flag=false;
                }
                if(flag==false){
                    new AlertDialog.Builder(getContext()).setTitle("提示")
                            .setMessage("请选择要结算的商品")
                            .setPositiveButton("确定",new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }).show();
                    flag=false;
                }
                else {
                    new AlertDialog.Builder(getContext()).setTitle("结算选定商品")
                            .setMessage("确定结算选定商品")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                @Override

                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    int i=0;
                                    while(i<cartList.size()){
                                        if(cartList.get(i).isFlag()){
                                            cartList_pay.add(cartList.get(i));
                                            cartList.remove(i);
//                                            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycle);
//                                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                                            adapter = new CartAdapter(cartList);
//                                            //adapter.setOnItemClickListener(this);
//                                            recyclerView.setAdapter(adapter);

                                        }else {
                                            i++;
                                        }
                                    }
                                    Thread_initPay initPay=new Thread_initPay();
                                    initPay.start();
                                    adapter.notifyDataSetChanged();
                                    SelectCommodity();
                                    if(cartList.size()==0){
                                        checkbox_all.setChecked(false);
                                    }
                                }
                            }).setNegativeButton("取消",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    }).show();
                    Thread_initPay thread_initPay=new Thread_initPay();
                    thread_initPay.start();
                }

            }

        });

        return root;
    }

    private void initCart(){
        Cart cart=new Cart("香蕉",R.drawable.shopping_car,"11","1");
        Cart cart1=new Cart("苹果",R.drawable.shopping_car,"10","2");
        Cart cart2=new Cart("哈密瓜",R.drawable.shopping_car,"11","1");
        Cart cart3=new Cart("西瓜",R.drawable.shopping_car,"10","2");
        cartList.add(cart);
        cartList.add(cart1);
        cartList.add(cart2);
        cartList.add(cart3);
        //cartList.add(homeFragment.AddCart());
    }
    private void initCart1(){
        Cart1 cart1=new Cart1("草莓",R.drawable.shopping_car,"22","2");
        Cart1 cart2=new Cart1("梨",R.drawable.shopping_car,"20","1");
        Cart1 cart3=new Cart1("枣",R.drawable.shopping_car,"22","2");
        // Cart1 cart4=new Cart1("www",R.drawable.shopping_car,"20","1");

        //cart1List.add(cart4);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void ItemAddClickListener(View view, int position) {
        cart_add = cartList.get(position);
        cart_add.setAmount(String.valueOf(Integer.valueOf(cart_add.getAmount())+1));
        cartList.set(position,cart_add);
        adapter.notifyDataSetChanged();
        SelectCommodity();
        Thread_initadd thread_initadd=new Thread_initadd();
        thread_initadd.start();
    }

    @Override
    public void ItemDeleteClickListener(View view, int position) {
        cart_del=cartList.get(position);
        cartList.remove(position);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(cartList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        SelectCommodity();
        Thread_initDel initDel=new Thread_initDel();
        initDel.start();
    }

    @Override
    public void ItemReduceClickListener(View view, int position) {
         cart_rec = cartList.get(position);
        if(Integer.valueOf(cart_rec.getAmount())==1){
            Toast.makeText(getContext(), "亲，商品数量不能再减少了", Toast.LENGTH_SHORT).show();
        }else{
            cart_rec.setAmount(String.valueOf(Integer.valueOf(cart_rec.getAmount())-1));
            cartList.set(position,cart_rec);
            adapter.notifyDataSetChanged();
            Thread_initrec thread_initrec=new Thread_initrec();
            thread_initrec.start();
        }
        SelectCommodity();
    }

    @Override
    public void ItemCheckboxClickListener(View view, int position, boolean bool) {
        cartList.get(position).setFlag(bool);
        SelectCommodity();
    }

    @Override
    public void ItemAddCommodity(View view, int position) {
        Cart1 cart1=cart1List.get(position);
        Cart cart=new Cart(cart1.getName(),cart1.getImgId(),cart1.getPrice(),"1");
        cartList.add(cart);
        adapter.notifyDataSetChanged();

        cart1List.remove(position);
        adapter1.notifyDataSetChanged();
    }
    private  void SelectCommodity(){
        DecimalFormat df   = new DecimalFormat("######0.00");
        double sum=0.0;
        int number=0;
        for (int i = 0; i < cartList.size(); i++) {
            if(cartList.get(i).isFlag()){
                sum=sum+Double.valueOf(cartList.get(i).getPrice())*Integer.valueOf(cartList.get(i).getAmount());
                number++;
            }else {
                CheckBox checkbox_all=root.findViewById(R.id.chekbox_all);
                checkbox_all.setChecked(false);
            }
        }
        if(number==cartList.size()){
            CheckBox checkbox_all=root.findViewById(R.id.chekbox_all);
            checkbox_all.setChecked(true);
        }
        if(cartList.size()==0){
            CheckBox checkbox_all=root.findViewById(R.id.chekbox_all);
            checkbox_all.setChecked(false);
        }
        TextView tv_total_price=root.findViewById(R.id.tv_total_price);
        tv_total_price.setText("￥"+String.valueOf(df.format(sum)));
        TextView tv_go_to_pay=root.findViewById(R.id.tv_go_to_pay);
        tv_go_to_pay.setText("结算("+String.valueOf(number)+")");
    }

    class  Thread_initCart extends Thread{
        public void run() {

            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn!=null) {
                    String sql ="Select name,imgId,price,amount from Cart";
                    pst = conn.prepareStatement(sql);
                    conn.setAutoCommit(false);
                    ResultSet rs = pst.executeQuery();//创建数据对象
                    List<Cart> cartList_1=new ArrayList<>();
                    while (rs.next()) {
                        Cart cart=new Cart(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getString(4));
                        cartList_1.add(cart);
                    }
                    Message message = handler.obtainMessage();
                    message.obj = cartList_1;
                    handler.sendMessage(message);
                    conn.setAutoCommit(false);
                    conn.commit();
                }
            }catch (Exception e){
                System.out.println(e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this,"请输入正确的语句",Toast.LENGTH_SHORT).show();
                        System.out.println("err_cart");
                    }
                });
            }
        }
    }

    class  Thread_initPay extends Thread{
        public void run() {

            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn!=null) {
                    conn.setAutoCommit(false);
                    String sql="Select max(OrderId) from Wait";
                    ResultSet rs=pst.executeQuery(sql);
                    int a;
                    if(rs.next()){
                        a=rs.getInt(1)+1;
                    }
                    else {
                        a=1;
                    }

                    for (int i = 0; i < cartList_pay.size(); i++) {
                        sql ="Insert into Wait(OrderId,Name,ImgId,Price,Amount) values(?,?,?,?,?)";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1,a);
                        pst.setString(2,cartList_pay.get(i).getName());
                        pst.setInt(3,cartList_pay.get(i).getImgId());
                        pst.setString(4,cartList_pay.get(i).getPrice());
                        pst.setString(5,cartList_pay.get(i).getAmount());
                        pst.execute();
                        conn.commit();

                        sql="Delete from Cart where imgId=?";
                        pst=conn.prepareStatement(sql);
                        pst.setInt(1,cartList_pay.get(i).getImgId());
                        pst.execute();
                        conn.commit();
                    }

                }

            }catch (Exception e){
                System.out.println(e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this,"请输入正确的语句",Toast.LENGTH_SHORT).show();
                        System.out.println("err_pay");
                    }
                });
            }
        }
    }

    class  Thread_initDel extends Thread{
        public void run() {

            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn!=null) {

                    conn.setAutoCommit(false);
                    String sql="Delete from Cart where imgId=?";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,cart_del.getImgId());
                    pst.execute();
                    conn.commit();
                }
            }catch (Exception e){
                System.out.println(e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this,"请输入正确的语句",Toast.LENGTH_SHORT).show();
                        System.out.println("err_del");
                    }
                });
            }
        }
    }

    class  Thread_initBtndel extends Thread{
        public void run() {

            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn!=null) {
                    conn.setAutoCommit(false);
                    int a;
                    for (int i = 0; i < cartList_del.size(); i++) {
                        System.out.println(cartList_del.get(i).getName());
                        String sql="Delete from Cart where imgId=?";
                        pst=conn.prepareStatement(sql);
                        pst.setInt(1,cartList_del.get(i).getImgId());
                        pst.execute();
                        conn.commit();
                    }

                }

            }catch (Exception e){
                System.out.println(e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this,"请输入正确的语句",Toast.LENGTH_SHORT).show();
                        System.out.println("err_btnd");
                    }
                });
            }
        }
    }

    class  Thread_initadd extends Thread{
        public void run() {

            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn!=null) {
                    String sql ="Update Cart set amount=amount+1 where name='"+cart_add.getName()+"'";
                    pst = conn.prepareStatement(sql);
                    conn.setAutoCommit(false);
                    pst.execute();
                    conn.commit();
                }
            }catch (Exception e){
                System.out.println(e);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this,"请输入正确的语句",Toast.LENGTH_SHORT).show();
                        System.out.println("err_add");
                    }
                });
            }
        }
    }

    class  Thread_initrec extends Thread{
        public void run() {

            try {
                conn = MySQLConnections.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (conn!=null) {
                    String sql ="Update Cart set amount=amount-1 where name='"+cart_rec.getName()+"'";
                    pst = conn.prepareStatement(sql);
                    conn.setAutoCommit(false);
                    pst.execute();
                    conn.commit();
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