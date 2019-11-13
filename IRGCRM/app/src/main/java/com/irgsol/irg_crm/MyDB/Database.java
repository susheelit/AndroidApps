package com.irgsol.irg_crm.MyDB;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.irgsol.irg_crm.dao.CartItemsDao;

@androidx.room.Database(entities = {CartItems.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static final String db_name = "irg_crmDB";
    private static Database instance;


    public static synchronized Database getInstance(Context context){

        if(instance == null) {

          /*  instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, db_name)
                    .fallbackToDestructiveMigration()
                    .build();*/

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, db_name)
                    .allowMainThreadQueries()
                    .build();


        }
        return instance;

    }

    public abstract CartItemsDao cartItemsDao();
}
