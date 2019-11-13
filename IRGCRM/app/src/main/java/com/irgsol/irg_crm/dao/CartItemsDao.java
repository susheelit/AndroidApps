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

    @Query("SELECT * FROM CartItems")
    List<CartItems> getCartItems();

    @Insert
    void insertCartItem(CartItems cartItems);

    @Update
    void updateCartItems(CartItems cartItems);

    @Delete
    void deleteCartItems(CartItems cartItems);
}
