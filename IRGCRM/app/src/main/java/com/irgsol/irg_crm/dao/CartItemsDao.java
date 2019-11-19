package com.irgsol.irg_crm.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.irgsol.irg_crm.MyDB.CartItems;

import java.util.List;

@Dao
public interface CartItemsDao {

    @Query("SELECT * FROM CartItems where prodId = :prodId AND shopId = :shopId")
    List<CartItems> checkCartItem(int prodId, String shopId);

    @Query("SELECT * FROM CartItems where shopId = :shopId")
    List<CartItems> getCartItem(String shopId);

    @Insert
    void insertCartItem(CartItems cartItems);

    @Update
    void updateCartItems(CartItems cartItems);

    @Delete
    void deleteCartItems(CartItems cartItems);
}
