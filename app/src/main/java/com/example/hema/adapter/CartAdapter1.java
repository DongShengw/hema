package com.example.hema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hema.R;
import com.example.hema.demo.Cart1;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter1  extends RecyclerView.Adapter<CartAdapter1.ViewHolder>{
    List<Cart1> cart1List=new ArrayList<>();
    private ItemClickListener listener;
    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView tv_name;
        TextView tv_price;
        TextView tv_times;
        ImageButton ib_cart;
        public ViewHolder (View view){
            super(view);
            image=(ImageView) view.findViewById(R.id.iv_adapter_list_pic1);
            tv_name=(TextView) view.findViewById(R.id.tv_goods_name1);
            tv_price=(TextView) view.findViewById(R.id.tv_goods_price1);
            tv_times=(TextView) view.findViewById(R.id.tv_goos_times1);
            ib_cart=(ImageButton) view.findViewById(R.id.ib_cart);
        }
    }
    public CartAdapter1 (List<Cart1> cart1List){
        this.cart1List=cart1List;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_commodity1,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Cart1 cart1=cart1List.get(position);
            holder.image.setImageResource(cart1.getImgId());
            holder.tv_name.setText(cart1.getName());
            holder.tv_price.setText(cart1.getPrice());
            holder.tv_times.setText("最近购买"+cart1.getTimes()+"次");
            holder.ib_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.ItemAddCommodity(v, holder.getPosition());
                }
            });
    }

    @Override
    public int getItemCount() {
       return cart1List.size();
    }
    public interface ItemClickListener{
        void ItemAddCommodity(View view, int position);
    }
}
