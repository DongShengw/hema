package com.example.hema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hema.R;
import com.example.hema.demo.Await;

import java.util.ArrayList;
import java.util.List;

public class AwaitAdapter extends RecyclerView.Adapter<AwaitAdapter.ViewHolder> {
    public List<Await> awaitList=new ArrayList<>();
    private AItemClickListener listener;
    public void setOnItemClickListener(AItemClickListener listener){
        this.listener=listener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_await1;
        ImageView img_await2;
        TextView txt_acount;
        TextView  txt_asum;
        TextView  txt_receive;
        public ViewHolder(View view){
            super(view);
            img_await1=(ImageView) view.findViewById(R.id.img_await1);
            img_await2=(ImageView) view.findViewById(R.id.img_await2);
            txt_acount=(TextView) view.findViewById(R.id.txt_acount);
            txt_asum=(TextView) view.findViewById(R.id.txt_asum);
            txt_receive=(TextView) view.findViewById(R.id.txt_receive);
        }
    }
    public AwaitAdapter(List<Await> awaitList){
        this.awaitList=awaitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order_await,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Await await=awaitList.get(position);
        holder.img_await1.setImageResource(await.getImgID1());
        holder.img_await2.setImageResource(await.getImgID2());
        holder.txt_acount.setText("共：2件商品");
        holder.txt_asum.setText(await.getAum());
        holder.txt_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                listener.AItemReceiveClickListener(view,holder.getPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return awaitList.size();
    }

    public interface AItemClickListener{
        void AItemReceiveClickListener(View view,int position);
    }


}
