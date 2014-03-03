package com.nom;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Database extends SQLiteOpenHelper {
	 
    public Database(Context context)
    {
        super(context, "StoresDatabase.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String tableEmp="create table stores(id text,name text,address text, city text, postalCode text, imgUrl text)";
    	db.execSQL(tableEmp);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void insertData(String id,String name, String address, String city, String postalCode, String imgUrl)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("name",name);
        values.put("address", address);
        values.put("city",city);
        values.put("postalCode",postalCode);
        values.put("imgUrl", imgUrl);
        
        sqLiteDatabase.insert("stores",null,values);

    }
    public ArrayList<StoreData> fetchData()
    {
        ArrayList<StoreData> storeDataArrayList=new ArrayList<StoreData>();
        String fetchdata="select * from stores";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);
        
        System.out.println("Count" + cursor.getCount());
        
        if(cursor.moveToFirst()){
           do
            {
        	   if(!cursor.isNull(0)){
        		   storeDataArrayList.add(new StoreData(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5))); 	              
        	   }
             } while (cursor.moveToNext());
       }
        
        cursor.close();
    return storeDataArrayList;
    }
    
    public int getCount() {
        String fetchdata="SELECT id FROM stores"; 
        int count = 0; 
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);
        cursor.moveToNext();
        count = cursor.getCount();
        cursor.close();
        return count;
    }
}
