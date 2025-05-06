package com.example.pcconfighelpercoursework.catalog;

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
import com.example.pcconfighelpercoursework.items.*;

import java.util.Arrays;
import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int CATALOG = 0;
    public static final int ADD_CONFIG = 1;//Константы для определения, каталог для добавления, изменения или просто просмотра
    public static final int CHANGE_CONFIG = 2;
    private final LayoutInflater inflater;
    private List<Component> components;
    private Component configurerComponent;
    private int choice;
    private OnAddButtonClickListener onAddButtonClickListener;

    public CatalogAdapter(Context context, List<Component> components, int choice) {
        this.inflater = LayoutInflater.from(context);
        this.components = components;
        this.choice = choice;
    }

    public CatalogAdapter(Context context, List<Component> components, int choice, OnAddButtonClickListener onAddButtonClickListener, Component configurerComponent) {
        this.inflater = LayoutInflater.from(context);
        this.components = components;
        this.choice = choice;
        this.onAddButtonClickListener = onAddButtonClickListener;
        this.configurerComponent = configurerComponent;
        Log.e("constant catalog", String.valueOf(choice));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case CHANGE_CONFIG:
            view = inflater.inflate(R.layout.catalog_item,parent,false);
            return new CatalogViewHolder(view);

            case ADD_CONFIG:
            view = inflater.inflate(R.layout.add_catalog_item,parent,false);
            return new AddCatalogViewHolder(view);
            default:
                view = inflater.inflate(R.layout.catalog_item,parent,false);
                return new CatalogViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case CATALOG:
                CatalogViewHolder catalogViewHolder = (CatalogViewHolder)holder;
                break;
            case ADD_CONFIG:
                AddCatalogViewHolder addCatalogViewHolder = (AddCatalogViewHolder)holder;
                addCatalogViewHolder.productNameTextView.setText(components.get(position).getName());
                addCatalogViewHolder.productDescriptionTextView.setText(components.get(position).getDescription());
                addCatalogViewHolder.priceTextView.setText("От " + components.get(position).getPrice() + "p");
                addCatalogViewHolder.addButton.setOnClickListener(v -> {
                    Log.e("configurerComponent",configurerComponent.toString());
                    List<Component> l = addConfigurerItem(MainActivity.getComponents(),configurerComponent,position);
                    MainActivity.setComponents(l);
                    onAddButtonClickListener.onAddButtonClick();
                });
                Log.e("aa", String.valueOf(getItemCount()));
                break;
        }

    }


    @Override
    public int getItemCount() {
        return components.size();
    }
    @Override
    public int getItemViewType(int position) {
        switch (choice){
            case ADD_CONFIG:
                return ADD_CONFIG;
            case CHANGE_CONFIG:
                return CHANGE_CONFIG;
            default:
                return CATALOG;
        }
    }
    public static class CatalogViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView productDescriptionTextView;
        public ImageView imageView;
        CatalogViewHolder(View view){
            super(view);
            productNameTextView = view.findViewById(R.id.productNameTextView);
            productDescriptionTextView = view.findViewById(R.id.productDescriptionTextView);
            imageView = view.findViewById(R.id.imageView);

        }
    }
    public static class AddCatalogViewHolder extends RecyclerView.ViewHolder {
        public TextView priceTextView;
        public TextView productNameTextView;
        public TextView productDescriptionTextView;
        public ImageButton addButton;
        public ImageView imageView;
        AddCatalogViewHolder(View view){
            super(view);
            priceTextView = view.findViewById(R.id.priceCatalogTextView);
            productNameTextView = view.findViewById(R.id.productNameTextView);
            productDescriptionTextView = view.findViewById(R.id.productDescriptionTextView);
            addButton = view.findViewById(R.id.addButtonCatalogImageView);
            imageView = view.findViewById(R.id.imageView);

        }
    }
    public interface OnAddButtonClickListener{
        void onAddButtonClick();
    }
    private List<Component> addConfigurerItem(List<Component> list, Component configurerComponent, int position){

        list.replaceAll(c ->
                c.equals(configurerComponent)
                        ? components.get(position).createUpdatedComponent(configurerComponent.getComponentType())
                        : c

        );
        Log.e("list change", Arrays.toString(list.toArray()));

        return list;

    }
}
