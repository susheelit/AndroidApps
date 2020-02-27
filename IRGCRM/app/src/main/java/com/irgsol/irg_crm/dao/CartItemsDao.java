package com.irgsol.irg_crm.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.irgsol.irg_crm.models.ModelProduct;

import java.util.List;

@Dao
public interface CartItemsDao {

    @Query("SELECT * FROM ModelProduct where prod_id = :prod_id AND shopId = :shopId")
    List<ModelProduct> checkCartItem(int prod_id, String shopId);

    @Query("SELECT * FROM ModelProduct where shopId = :shopId")
    List<ModelProduct> getCartItem(String shopId);

    @Insert
    void insertCartItem(ModelProduct cartItems);

    @Update
    void updateCartItems(ModelProduct cartItems);

    @Delete
    void deleteCartItems(ModelProduct cartItems);
}
