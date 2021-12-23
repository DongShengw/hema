package com.example.hema.demo;

public class Cart1 {
    private String name;
    private int imgId;
    private String price;
    private String times;
    public Cart1(String name,int imgId,String price,String times){
        this.name=name;
        this.imgId=imgId;
        this.price=price;
        this.times=times;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
