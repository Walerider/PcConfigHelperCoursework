package com.example.pcconfighelpercoursework.product;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfighelpercoursework.items.Component;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Component component;

    public ProductAdapter(Component component) {
        this.component = component;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
    return 0;
    }
}
