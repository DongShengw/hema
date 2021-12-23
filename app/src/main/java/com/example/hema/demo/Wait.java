package com.example.hema.demo;

public class Wait {

    private String OrderID;
    private int ImgID1;
    private int ImgID2;
    private String sum;
    private int num;
    public int getImgID1() {
        return ImgID1;
    }

    public void setImgID1(int imgID1) {
        ImgID1 = imgID1;
    }

    public int getImgID2() {
        return ImgID2;
    }

    public void setImgID2(int imgID2) {
        ImgID2 = imgID2;
    }
    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
    public Wait(String orderID, int imgID1, int imgID2, String sum,int num) {
        OrderID = orderID;
        ImgID1 = imgID1;
        ImgID2 = imgID2;
        this.sum = sum;
        this.num=num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
