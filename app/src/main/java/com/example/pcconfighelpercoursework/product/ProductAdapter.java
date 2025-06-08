package com.example.pcconfighelpercoursework.product;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;
import com.example.pcconfighelpercoursework.catalog.filters.FilterChoiceFragment;
import com.example.pcconfighelpercoursework.items.Component;

import java.util.Arrays;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private Component component;

    public ProductAdapter(Component component) {
        this.component = component;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        int i = 0;
        Log.e("component", String.valueOf(component));
        for (String key: component.getAttributes().keySet()) {
            if(i == position){
                holder.textView.setText(key + ": " + component.getAttributes().get(key));
            }
            i++;
        }
    }

    @Override
    public int getItemCount() {
        return component.getAttributes().keySet().size();
    }
    class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
