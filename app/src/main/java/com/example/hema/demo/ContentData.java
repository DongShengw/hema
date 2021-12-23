package com.example.hema.demo;

import androidx.annotation.NonNull;

import java.util.List;

public class ContentData {


    public ContentData(Goods goods, int itemize) {
        Goods = goods;
        this.itemize = itemize;
    }
    private int itemize;

    public com.example.hema.demo.Goods getGoods() {
        return Goods;
    }

    public void setGoods(com.example.hema.demo.Goods goods) {
        Goods = goods;
    }

    private Goods Goods;

    public int getItemize() {
        return itemize;
    }

    public void setItemize(int itemize) {
        this.itemize = itemize;
    }




}
