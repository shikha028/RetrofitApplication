package com.example.retrofitapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    
    public TextView name, fullname, description;
    public ImageView avatar;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        fullname = itemView.findViewById(R.id.full_name);
        description = itemView.findViewById(R.id.description);
        avatar = itemView.findViewById(R.id.avatarImg);
    }
}
