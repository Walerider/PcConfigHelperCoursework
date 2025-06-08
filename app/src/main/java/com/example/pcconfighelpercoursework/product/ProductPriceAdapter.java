package com.example.pcconfighelpercoursework.product;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.items.Component;

public class ProductPriceAdapter extends RecyclerView.Adapter<ProductPriceAdapter.ProductViewHolder>{
    private Component component;

    public ProductPriceAdapter(Component component) {
        this.component = component;
    }

    @NonNull
    @Override
    public ProductPriceAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_price_item,parent,false);
        return new ProductPriceAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductPriceAdapter.ProductViewHolder holder, int position) {
        holder.sourceTextView.setText("Ретейлеры");
        holder.sourceTextView.setTextColor(holder.itemView.getContext().getColor(R.color.orange));
        holder.priceTextView.setText(component.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView sourceTextView;
        TextView priceTextView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            sourceTextView = itemView.findViewById(R.id.sourceTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }
    }
}
