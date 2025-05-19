package com.example.pcconfighelpercoursework.assemblies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.catalog.CatalogAdapter;
import com.example.pcconfighelpercoursework.items.Component;

import java.util.List;

public class AssemblyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int CATALOG = 0;;
    private final LayoutInflater inflater;
    private List<Component> components;
    private Component configurerComponent;
    private int choice;
    private CatalogAdapter.OnAddButtonClickListener onAddButtonClickListener;

    public AssemblyAdapter(Context context, List<Component> components) {
        this.inflater = LayoutInflater.from(context);
        this.components = components;
        this.choice = choice;
    }

    public AssemblyAdapter(Context context, List<Component> components, Component configurerComponent) {
        this.inflater = LayoutInflater.from(context);
        this.components = components;
        this.choice = choice;
        this.configurerComponent = configurerComponent;
        Log.e("constant catalog", String.valueOf(choice));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){

            case CATALOG:
                view = inflater.inflate(R.layout.catalog_item,parent,false);
                return new AssemblyAdapter.CatalogViewHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case CATALOG:
                CatalogAdapter.CatalogViewHolder catalogViewHolder = (CatalogAdapter.CatalogViewHolder)holder;
                catalogViewHolder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
                catalogViewHolder.productNameTextView.setText(components.get(position).getName());
                catalogViewHolder.productDescriptionTextView.setText(components.get(position).getDescription());
                catalogViewHolder.priceTextView.setText("От " + components.get(position).getPrice() + "р");
                break;
        }

    }


    @Override
    public int getItemCount() {
        return components.size();
    }
    @Override
    public int getItemViewType(int position) {
        return CATALOG;
    }

    public static class CatalogViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView productDescriptionTextView;
        public ImageView imageView;
        public TextView priceTextView;
        CatalogViewHolder(View view){
            super(view);
            productNameTextView = view.findViewById(R.id.productNameTextView);
            productDescriptionTextView = view.findViewById(R.id.productDescriptionTextView);
            imageView = view.findViewById(R.id.imageView);
            priceTextView = view.findViewById(R.id.priceCatalogTextView);
        }
    }
}