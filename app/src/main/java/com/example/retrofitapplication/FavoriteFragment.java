package com.example.retrofitapplication;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.retrofitapplication.Adapter.MyAdapter;
import com.example.retrofitapplication.Model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    List<Item> itemList, fav_list;
    CachedItemList cachedItemList;


    public FavoriteFragment() {
        // Required empty public constructor
        cachedItemList = CachedItemList.getInstance();
        itemList = cachedItemList.getMainList();
        fav_list = cachedItemList.getFavList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.frag_recycler);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fav_list = itemList.stream().filter(e->e.getFav()==true).collect(Collectors.toList());
        }*/
        MyAdapter myAdapter = new MyAdapter(fav_list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);

        return view;
    }
}