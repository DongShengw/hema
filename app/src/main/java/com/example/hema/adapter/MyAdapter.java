package com.example.hema.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hema.R;
import com.example.hema.demo.Cart;
import com.example.hema.demo.ContentData;
import com.example.hema.demo.Goods;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ContentData> dataList = new ArrayList<>();
    private ItemClickListener listener;
    private static final int HEADER_VIEW = 0;
    private static final int Class_VIEW = 1;
    private static final int Goods_VIEW = 2;
    //保存了所有图片播放数据集合
    private List<Integer> imgItem;
    //保存了所有标题数据集合
    private List<String> titleItem;
    public void setOnItemClickListener(MyAdapter.ItemClickListener listener) {
        this.listener = listener;
    }

    //头图片
    class BannerViewHolder extends RecyclerView.ViewHolder {
        Banner banner;
        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }
    }
    //类别
    static class ClassHolder extends RecyclerView.ViewHolder{
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
        public ClassHolder (View view){
            super(view);
        }
    }
    //商品
    static class GoodsHolder extends RecyclerView.ViewHolder{
        FrameLayout frameLayout;
        ImageView image;
        TextView goods_name;
        TextView goods_class;
        TextView goods_price;
        ImageButton add_cart;
        public GoodsHolder (View view){
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the normal items
                }
            });
        }
    }

    public MyAdapter(List<ContentData> dataList, List<Integer> image, List<String> title){
        this.dataList=dataList;
        this.imgItem=image;
        this.titleItem=title;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == HEADER_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_image_home, parent, false);
            BannerViewHolder Headerholder = new BannerViewHolder(view);
            return Headerholder;
        }
        else if(viewType == Class_VIEW){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scrolls_home, parent, false);
            ClassHolder classHolder = new ClassHolder(view);
            return classHolder;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_home, parent, false);
        GoodsHolder holder = new GoodsHolder(view);
        holder.goods_name = (TextView) view.findViewById(R.id.home_goods_name);
        holder.goods_class = (TextView) view.findViewById(R.id.home_goods_class);
        holder.goods_price = (TextView) view.findViewById(R.id.home_goods_price);
        holder.image = (ImageView) view.findViewById(R.id.home_goods_picture);
        holder.add_cart = (ImageButton) view.findViewById(R.id.home_cart);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        try {
            if (viewHolder instanceof GoodsHolder) {
                GoodsHolder holder = (GoodsHolder) viewHolder;
                ContentData data = dataList.get(position);

                holder.goods_name.setText(data.getGoods().getName());
                holder.image.setImageResource(data.getGoods().getId());
                holder.goods_class.setText(data.getGoods().getType());
                holder.goods_price.setText("￥"+data.getGoods().getPrice()+"/份");

                holder.add_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.ItemAddCommodity(v, holder.getPosition());
                    }
                });
            }
            else if (viewHolder instanceof ClassHolder) {
                ClassHolder classHolder = (ClassHolder) viewHolder;

            }
            else {
                BannerViewHolder bannerViewHolder  = (BannerViewHolder)viewHolder;
                bannerViewHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                bannerViewHolder.banner.setBannerTitles(titleItem);
                bannerViewHolder.banner.setImages(imgItem);
                bannerViewHolder.banner.setImageLoader(new GlideImageLoader());
                bannerViewHolder.banner.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class GlideImageLoader extends ImageLoader {

        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load(path).into(imageView);

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        }
        else if(position == 1){
            return Class_VIEW;
        }
        return Goods_VIEW;
    }
    public interface ItemClickListener{
        void ItemAddCommodity(View view, int position);
    }
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == HEADER_VIEW||getItemViewType(position) == Class_VIEW
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
}
