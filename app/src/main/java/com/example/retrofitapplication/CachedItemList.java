package com.example.retrofitapplication;

import com.example.retrofitapplication.Model.Item;

import java.util.ArrayList;
import java.util.List;
//Singleton
public class CachedItemList {

    private List<Item> mainList;
    private List<Item> favList;
    private static CachedItemList cachedItemList;

    private CachedItemList(){
        mainList = new ArrayList<>();
        favList = new ArrayList<>();
    }

    public static CachedItemList getInstance(){
        if (cachedItemList == null)
            cachedItemList = new CachedItemList();
        return cachedItemList;
    }

    public List<Item> getMainList() {
        return mainList;
    }

    public void setMainList(List<Item> mainList) {
        this.mainList = mainList;
    }

    public List<Item> getFavList() {
        return favList;
    }

    public void setFavList(List<Item> favList) {
        this.favList = favList;
    }
}
