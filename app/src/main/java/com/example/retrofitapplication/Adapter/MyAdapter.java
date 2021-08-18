package com.example.retrofitapplication.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitapplication.CachedItemList;
import com.example.retrofitapplication.FavDataBase;
import com.example.retrofitapplication.Model.Item;
import com.example.retrofitapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    List<Item> itemList, fav_List;
    Context context;
    List<Item> backup;
    CachedItemList cachedItemList;
    FavDataBase favDataBase;

    public MyAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
        backup = (itemList);
        //cachedItemList = CachedItemList.getInstance();
        favDataBase = new FavDataBase(context);
        //fav_List = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cell, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.name.setText(item.getName());
        holder.fullname.setText(item.getFullName());
        holder.description.setText(item.getDescription());
        // holder.avatar.setImageURI(Uri.parse(item.getOwner().getAvatarUrl()));
        if (item.getOwner().getAvatarUrl()!="")
            Picasso.get().load(item.getOwner().getAvatarUrl()).placeholder(R.drawable.stars).into(holder.avatar);

        holder.fav_radioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (item.getFav() == 1) {
                    item.setFav(0);
                    //removeItem(item);
                    favDataBase.updateItem(item);
                    holder.fav_radioButton.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_border), null, null, null);
                } else {
                    item.setFav(1);
                    //addItem(item);
                    favDataBase.updateItem(item);
                    holder.fav_radioButton.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_rate), null, null, null);
                }
            }
        });
        //int resId = item.getFav() == 1 ? R.drawable.star_rate : R.drawable.star_border;
       // holder.fav_radioButton.setCompoundDrawablesWithIntrinsicBounds(getDrawable(resId), null, null, null);
    }

    private Drawable getDrawable(@DrawableRes int resId) {
        Drawable img = context.getResources().getDrawable(resId);
        return img;
    }

    private void removeItem(Item item) {
        fav_List.remove(item);
        List<Item> tempList = cachedItemList.getMainList();
        tempList.set(tempList.indexOf(item), item);
        cachedItemList.setMainList(tempList);
        cachedItemList.setFavList(fav_List);
    }

    private void addItem(Item item) {
        fav_List.add(item);
        List<Item> tempList = cachedItemList.getMainList();
        tempList.set(tempList.indexOf(item), item);
        cachedItemList.setMainList(tempList);
        cachedItemList.setFavList(fav_List);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //bg
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Item> filterdata = new ArrayList<>();

            if (charSequence.toString().isEmpty())
                filterdata.addAll(backup);
            else {
                //read and edit
                for (Item item : backup) {
                    if (item.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        filterdata.add(item);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterdata;
            return filterResults;
        }

        //ui
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            itemList.clear();
            itemList.addAll((ArrayList<Item>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public void updateList(List<Item> itemList){
        this.itemList = itemList;
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, fullname, description;
        public ImageView avatar;
        public Button fav_radioButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            fullname = itemView.findViewById(R.id.full_name);
            description = itemView.findViewById(R.id.description);
            avatar = itemView.findViewById(R.id.avatarImg);
            fav_radioButton = itemView.findViewById(R.id.radio_favbtn);
        }
    }

}
