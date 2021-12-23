package com.example.hema.demo;

public class Cart {
    private String name;
    private int imgId;
    private String price;
    private String amount;
    private  boolean flag;
    public Cart(String name,int imgId,String price,String amount){
        this.name=name;
        this.imgId=imgId;
        this.price=price;
        this.amount=amount;
    }

    public  Cart() {

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
