package com.example.retrofitapplication;

import androidx.recyclerview.widget.RecyclerView;

public interface FragmentListener {
    void onFragmentLoad();
    void getAdapterFromFragment(RecyclerView.Adapter adapter);
}
