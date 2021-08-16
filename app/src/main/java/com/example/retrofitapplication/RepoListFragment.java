package com.example.retrofitapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.retrofitapplication.Adapter.MyAdapter;
import com.example.retrofitapplication.Model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class RepoListFragment extends Fragment {

    RecyclerView recyclerView;
    List<Item> itemList;
    CachedItemList cachedItemList;
    FragmentListener fragmentListener;
    FloatingActionButton floatingActionButton;

    FavDataBase favDataBase;
    private MyAdapter myAdapter;

    public RepoListFragment(Context context) {
        // Required empty public constructor
        cachedItemList = CachedItemList.getInstance();
        favDataBase = new FavDataBase(context);
        //itemList = cachedItemList.getMainList();
        itemList = favDataBase.getItemList();
        fragmentListener = (FragmentListener) context;
        //fragmentListener = (FragmentListener) getContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new MyAdapter(itemList, getContext());
        floatingActionButton = view.findViewById(R.id.float_favoriteBtn);
        recyclerView.setAdapter(myAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentListener.onFragmentLoad();
            }
        });
        fragmentListener.getAdapterFromFragment(myAdapter);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        itemList = favDataBase.getItemList();
        myAdapter.notifyDataSetChanged();
    }
}