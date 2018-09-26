package com.source.net.mokashoppincart.models;

import java.io.Serializable;

public class CartItemModel implements Serializable {

    private int quantity;
    private int id;
    private String title;
    private String item_cost;
    private float discount;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemCost() {
        return item_cost;
    }

    public void setItemCost(String item_cost) {
        this.item_cost = item_cost;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

}
