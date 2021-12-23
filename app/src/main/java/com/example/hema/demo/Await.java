package com.example.hema.demo;

public class Await {
    private String OrderID;
    private int ImgID1;
    private int ImgID2;
    private String Aum;
    private int sum;

    public Await(String orderID, int imgID1, int imgID2, String aum,int num) {
        OrderID = orderID;
        ImgID1 = imgID1;
        ImgID2 = imgID2;
        Aum = aum;
        this.sum=sum;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

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

    public String getAum() {
        return Aum;
    }

    public void setAum(String aum) {
        Aum = aum;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
