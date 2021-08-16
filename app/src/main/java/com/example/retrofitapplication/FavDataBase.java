package com.example.retrofitapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.retrofitapplication.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class FavDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "local database";
    private static final int DATABASE_VERSION = 1;
    //
    private final String FAVORITE_TABLE = "ITEM";
    private final String ID = "id";
    private final String NAMEID = "nameid";
    private final String FULLNAME = "fullname";
    private final String NAME = "name";
    private final String FAV = "fav";


    public FavDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + FAVORITE_TABLE + " (" + ID + " INTEGER PRIMARY KEY, "
                + NAMEID + " TEXT, " + FULLNAME + " TEXT, " + NAME + " TEXT, " + FAV + " INTEGER)";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveItem(Item item) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, item.getId());
        contentValues.put(NAMEID, item.getNodeId());
        contentValues.put(NAME, item.getName());
        contentValues.put(FULLNAME, item.getFullName());
        contentValues.put(FAV, item.getFav());

        sqLiteDatabase.insert(FAVORITE_TABLE, null, contentValues);
        sqLiteDatabase.close();
    }

    public void updateItem(Item item) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, item.getId());
        contentValues.put(NAMEID, item.getNodeId());
        contentValues.put(NAME, item.getName());
        contentValues.put(FULLNAME, item.getFullName());
        contentValues.put(FAV, item.getFav());


        sqLiteDatabase.update(FAVORITE_TABLE, contentValues, ID + "=?", new String[]{String.valueOf(item.getId())});
        sqLiteDatabase.close();
    }

    public void deleteItem(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(FAVORITE_TABLE, ID + "=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public List<Item> getItemList() {
        List<Item> itemList = new ArrayList<>();

        String query = "Select * FROM " + FAVORITE_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            item.setNodeId(cursor.getString(cursor.getColumnIndex(NAMEID)));
            item.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            item.setFullName(cursor.getString(cursor.getColumnIndex(FULLNAME)));
            item.setFav(cursor.getInt(cursor.getInt(cursor.getColumnIndex(FAV))));
            itemList.add(item);
        }
        sqLiteDatabase.close();
        return itemList;
    }

    @SuppressLint("Range")
    public List<Item> getItemList(int fav) {
        List<Item> itemList = new ArrayList<>();

        String query = "Select * FROM " + FAVORITE_TABLE + " where " + FAV + "=" + fav;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            item.setNodeId(cursor.getString(cursor.getColumnIndex(NAMEID)));
            item.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            item.setFullName(cursor.getString(cursor.getColumnIndex(FULLNAME)));
            item.setFav(cursor.getInt(cursor.getInt(cursor.getColumnIndex(FAV))));
            itemList.add(item);
        }
        sqLiteDatabase.close();
        return itemList;
    }


}
