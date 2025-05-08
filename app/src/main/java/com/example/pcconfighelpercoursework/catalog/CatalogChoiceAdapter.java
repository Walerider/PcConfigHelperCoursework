package com.example.pcconfighelpercoursework.catalog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfighelpercoursework.R;

import java.util.*;

public class CatalogChoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> componenList;
    private final LayoutInflater inflater;
    private final OnItemClickListener clickListener;
    public CatalogChoiceAdapter(List<String> componenList, Context context, OnItemClickListener clickListener) {
        this.componenList = componenList;
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.catalog_choice_item,parent,false);
        Log.e("Choice","aaaaaaa");
        return new ChoiceCatalogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChoiceCatalogViewHolder viewHolder = (ChoiceCatalogViewHolder)holder;
        viewHolder.productNameTextView.setText(componenList.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return componenList.size();
    }
    public static class ChoiceCatalogViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        ChoiceCatalogViewHolder(View view){
            super(view);
            productNameTextView = view.findViewById(R.id.productNameTextView);

        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
