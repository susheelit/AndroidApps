package com.aob.aobsalesman.activities.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.aob.aobsalesman.activities.model.DataBaseRecord;

import java.util.ArrayList;
import java.util.List;


public class MyDatabase extends SQLiteOpenHelper
{
    private Context context;
    //Database version
 public static final int DATABASE_VERSION=1;   //Constant Variable
    // Database name
 public static final String DATABASE_NAME="mydb";
 //table Name
 public static final String TABLE_NAME="User_Login_Detail";
 //Coloums name
 public static final String ID="id";
 public static final String EMAIL="email";
 public static final String USER_NAME="user_name";
 public static final String STATUS="status";
 public static final String DATE="date";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);//After coming from the MainActivity here super keyword will call SQLITEOPENHELPER construtor
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) //Factory Method
    {
        String query="create table "+TABLE_NAME+"("+EMAIL+" TEXT NOT NULL,"+USER_NAME+" TEXT,"+STATUS+" BOOLEAN,"+DATE+" TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //We are adding value using this function we will call it in MainActivity
    public void addRecord(DataBaseRecord record)
    {
        Cursor cursor=null;

        SQLiteDatabase  db = this.getReadableDatabase();
        try {
            String query = "Select count(*) from User_Login_Detail WHERE email=?";

               cursor = db.rawQuery(query, new String[] {record.getUserId() + ""});

            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.getInt(0) > 0) {
                    //    Toast.makeText(context, "user already exist", Toast.LENGTH_SHORT).show();
                    } else {
                        db = getWritableDatabase();
                        ContentValues values = new ContentValues();  // it will store value in it

                        values.put(EMAIL, record.getUserId());
                        values.put(USER_NAME, record.getUserName());
                        values.put(STATUS, record.isStatus());
                        values.put(DATE, record.getDate());

                        db.insert(TABLE_NAME, null, values);  // we are inserting data using it
                       // Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }
            db.close();
        }catch (Exception e) {

           Log.e("insetException=",e.toString());
        }
    }
    public void UpdateRecord(DataBaseRecord record,String Email) //We are adding value using this function we will call it in MainActivity
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();  // it will store value in it

        values.put(EMAIL, record.getUserId());
        values.put(USER_NAME, record.getUserName());
        values.put(STATUS, record.isStatus());
        values.put(DATE, record.getDate());

        db.update(TABLE_NAME, values, EMAIL + "="+Email,null);
    }
    public int getcount(int id)   //By using this we are getting single record//
    {
        int count = 0;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String query = "Select count(*) from User_Login_Detail WHERE user_id=?";
            Log.d("query", query);
            cursor = db.rawQuery(query, new String[]{id + ""});
            if (cursor != null) {
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return count;
    }

    public boolean getstatus(String Email)   //By using this we are getting single record
    {
        boolean status = false;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String query = "Select status from User_Login_Detail WHERE user_id=?";
            Log.d("query", query);
            cursor = db.rawQuery(query, new String[]{Email + ""});
            if (cursor != null) {
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    status = cursor.getInt(0) > 0;
                }
                cursor.close();
            }

            db.close();
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

        return status;
    }

    public ArrayList<String> getAllRecord() {

        ArrayList<String> data=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String query="Select * from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);//Raw query return the data but execQuery does not return anything

        if (cursor.moveToFirst()) {
                do {
                    data.add(cursor.getString(0));

                   /* data.add(new DataBaseRecord((cursor.getString(1)),
                            cursor.getString(2),
                            Boolean.parseBoolean(cursor.getString(3)),
                            cursor.getString(4)));*/
                    //add record to list
                }
                while (cursor.moveToNext());
        }
        return data;

        }
}
