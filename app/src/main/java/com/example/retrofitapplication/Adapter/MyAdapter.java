package com.example.retrofitapplication.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitapplication.Model.Item;
import com.example.retrofitapplication.MyViewHolder;
import com.example.retrofitapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<Item> itemList;
    Context context;

    public MyAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_cell,parent,false);
        MyViewHolder myViewHolder= new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item=itemList.get(position);

        holder.name.setText(item.getName());
        holder.fullname.setText(item.getFullName());
        holder.description.setText(item.getDescription());
       // holder.avatar.setImageURI(Uri.parse(item.getOwner().getAvatarUrl()));
        Picasso.get().load(item.getOwner().getAvatarUrl()).into(holder.avatar);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
