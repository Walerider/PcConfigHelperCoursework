package com.example.pcconfighelpercoursework.assemblies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.catalog.CatalogAdapter;
import com.example.pcconfighelpercoursework.items.Component;

import java.util.List;
import java.util.zip.Inflater;

public class UserAssemblyLookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Component> components;
    LayoutInflater inflater;
    public UserAssemblyLookAdapter(List<Component> components, Context context) {
        this.components = components;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.catalog_item,parent,false);
        return new UserAssemblyLookAdapter.AssemblyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserAssemblyLookAdapter.AssemblyViewHolder catalogViewHolder = (UserAssemblyLookAdapter.AssemblyViewHolder)holder;
        if(components.get(position).getImage() != null){
            Log.e("image source",components.get(position).getImage());
            Glide.with(catalogViewHolder.itemView)
                    .load(components.get(position).getImage())
                    .into(catalogViewHolder.imageView);
        }else{
            catalogViewHolder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }

        catalogViewHolder.productNameTextView.setText(components.get(position).getName());
        Log.e("component",components.get(position).getName());
        catalogViewHolder.productDescriptionTextView.setText(components.get(position).getDescription());
        catalogViewHolder.priceTextView.setText("От " + components.get(position).getPrice() + "р");
    }

    @Override
    public int getItemCount() {
        return components.size();
    }
    public static class AssemblyViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView productDescriptionTextView;
        public ImageView imageView;
        public TextView priceTextView;
        AssemblyViewHolder(View view){
            super(view);
            productNameTextView = view.findViewById(R.id.productNameTextView);
            productDescriptionTextView = view.findViewById(R.id.productDescriptionTextView);
            imageView = view.findViewById(R.id.imageView);
            priceTextView = view.findViewById(R.id.priceCatalogTextView);
        }
    }
}
