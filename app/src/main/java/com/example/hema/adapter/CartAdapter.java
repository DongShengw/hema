package com.example.hema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hema.R;
import com.example.hema.demo.Cart;
import com.google.android.material.slider.Slider;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.hema.R.drawable.*;
public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    public List<Cart> cartList=new ArrayList<>();
    private ItemClickListener listener;

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView image;
        TextView tv_name;
        TextView tv_price;
        TextView tv_amount;
        TextView tv_delete;
        Button btn_add;
        Button btn_reduce;
        ViewGroup layout_content;
        CheckBox check_Box;
        public ViewHolder (View view){
            super(view);
//            cardView=(CardView) view;
            image=(ImageView) view.findViewById(R.id.iv_adapter_list_pic);
            tv_name=(TextView) view.findViewById(R.id.tv_goods_name);
            tv_price=(TextView) view.findViewById(R.id.tv_goods_price);
            tv_amount=(TextView) view.findViewById(R.id.tv_goods_amount);
            tv_delete=(TextView) view.findViewById(R.id.tv_delete);
            btn_add=(Button) view.findViewById(R.id.btn_add);
            btn_reduce=(Button) view.findViewById(R.id.btn_reduce);
            layout_content = (ViewGroup) view.findViewById(R.id.layout_content);
            check_Box=(CheckBox) view.findViewById(R.id.check_box);
        }
    }
    public CartAdapter(List<Cart> cartList){
        this.cartList=cartList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_commodity,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart=cartList.get(position);
        holder.image.setImageResource(cart.getImgId());
        holder.tv_name.setText(cart.getName());
        DecimalFormat df   = new DecimalFormat("######0.00");
        holder.tv_price.setText("￥"+String.valueOf(df.format(Double.valueOf(cart.getPrice())*Integer.valueOf(cart.getAmount()))));
        holder.tv_amount.setText(cart.getAmount());
        holder.layout_content.getLayoutParams().width =1100;

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ItemAddClickListener(v, holder.getPosition());
            }
        });
        holder.btn_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ItemReduceClickListener(v, holder.getPosition());
            }

        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.ItemDeleteClickListener(v, holder.getPosition());

            }
        });
        holder.check_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.check_Box.isChecked()){
                    listener.ItemCheckboxClickListener(v,holder.getAdapterPosition(),true);
                }else{
                    listener.ItemCheckboxClickListener(v,holder.getAdapterPosition(),false);
                }
            }
        });
        if(cart.isFlag()){
            holder.check_Box.setChecked(true);
        }else {
            holder.check_Box.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
    public interface ItemClickListener {
        void ItemAddClickListener(View view, int position);
        void ItemDeleteClickListener(View view, int position);
        void ItemReduceClickListener(View view, int position);
        void ItemCheckboxClickListener(View view,int position,boolean bool);
    }
}
