package com.irgsol.irg_crm.MyDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CartItems")
public class CartItems {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "itemImg")
    private int itemImg;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "qty")
    private String qty;

    @ColumnInfo(name = "price")
    private String price;

    public CartItems() { }

    public CartItems(int id, int itemImg, String title, String qty, String price) {
        Id = id;
        this.itemImg = itemImg;
        this.title = title;
        this.qty = qty;
        this.price = price;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getItemImg() {
        return itemImg;
    }

    public void setItemImg(int itemImg) {
        this.itemImg = itemImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
