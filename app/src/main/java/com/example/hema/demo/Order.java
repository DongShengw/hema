package com.example.hema.demo;

public class Order {
    private String OrderId;
    private String name;
    private int ImgId;
    private String sum;
    private int num;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public int getImgId() {
        return ImgId;
    }

    public void setImgId(int imgId) {
        ImgId = imgId;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order(String orderId, String name, int imgId, String sum, int num) {
        OrderId = orderId;
        ImgId = imgId;
        this.sum = sum;
        this.num = num;
        this.name=name;
    }
}
