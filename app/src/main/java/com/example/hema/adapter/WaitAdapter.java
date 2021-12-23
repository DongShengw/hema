package com.example.hema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hema.R;
import com.example.hema.demo.Wait;

import java.util.ArrayList;
import java.util.List;

public class WaitAdapter extends RecyclerView.Adapter<WaitAdapter.ViewHolder>{
    public List<Wait> waitlist=new ArrayList<>();
    private  ItemClickListener  listener;
    Wait wait_sum;
    public void setOnItemClickListener(ItemClickListener  listener){
        this.listener=listener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView   img_wait1;
        ImageView   img_wait2;
        TextView    txt_count;
        TextView    txt_sum;
        TextView    txt_stop_pay;
        TextView    txt_conti_pay;
        public ViewHolder(View  view){
            super(view);
            img_wait1=(ImageView) view.findViewById(R.id.img_wait1);
            img_wait2=(ImageView) view.findViewById(R.id.img_wait2);
            txt_count=(TextView) view.findViewById(R.id.txt_count);
            txt_sum=(TextView) view.findViewById(R.id.txt_sum);
            txt_stop_pay=(TextView) view.findViewById(R.id.txt_stop_pay);
            txt_conti_pay=(TextView) view.findViewById(R.id.txt_conti_pay);
        }
    }
    public WaitAdapter(List<Wait> waitlist){
        this.waitlist=waitlist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order_wait,parent,false);
        ViewHolder  holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wait wait= waitlist.get(position);
        holder.img_wait1.setImageResource(wait.getImgID1());
        holder.img_wait2.setImageResource(wait.getImgID2());
        holder.txt_count.setText("共："+wait.getNum()+"件商品");
        holder.txt_sum.setText(wait.getSum());
        holder.txt_stop_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.ItemStopClickListener(view,holder.getPosition());
            }
        });
        holder.txt_conti_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.ItemContiClickListener(view,holder.getPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return waitlist.size();
    }
    public interface  ItemClickListener{
        void ItemStopClickListener(View view,int position);
        void ItemContiClickListener(View view,int position);
    }
}
