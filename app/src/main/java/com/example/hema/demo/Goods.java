package com.example.hema.demo;

public class Goods {
    public Goods(int id, String name, String price, String type) {
        Id = id;
        Name = name;
        Price = price;
        Type = type;
    }
    private int Id;
    private String Name;
    private String Type;
    private String Price;



    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }



}
