package com.example.pcconfighelpercoursework.api.items;

public class PriceDAO {
    long id;
    int price;
    String source;

    public PriceDAO(long id, int price, String source) {
        this.id = id;
        this.price = price;
        this.source = source;
    }

    public long getId() {
        return id;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
