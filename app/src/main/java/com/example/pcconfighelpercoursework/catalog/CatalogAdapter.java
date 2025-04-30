package com.example.pcconfighelpercoursework.catalog;

import android.content.Context;
import android.content.res.Resources;
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
import com.example.pcconfighelpercoursework.catalog.items.CatalogItem;
import com.example.pcconfighelpercoursework.configurator.items.*;
import com.example.pcconfighelpercoursework.R;

import java.util.Arrays;
import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int CATALOG = 0;
    public static final int ADD_CONFIG = 1;//Константы для определения, каталог для добавления, изменения или просто просмотра
    public static final int CHANGE_CONFIG = 2;
    private final LayoutInflater inflater;
    private List<CatalogItem> components;
    private ConfigurerItem configurerComponent;
    private int choice;
    private OnAddButtonClickListener onAddButtonClickListener;

    public CatalogAdapter(Context context, List<CatalogItem> components, int choice) {
        this.inflater = LayoutInflater.from(context);
        this.components = components;
        this.choice = choice;
    }

    public CatalogAdapter(Context context,List<CatalogItem> components, int choice, OnAddButtonClickListener onAddButtonClickListener,ConfigurerItem configurerComponent) {
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
                    List<ConfigurerItem> l = addConfigurerItem(MainActivity.getComponents(),configurerComponent,position);;
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
            //todo Доделать каталог 28/29!!!!!!!
        }
    }
    private List<ConfigurerItem> addConfigurerItem(List<ConfigurerItem> list,ConfigurerItem configurerComponent,int position){
        String cpu = MainActivity.resources.getString(R.string.cpu);
        String videocard = MainActivity.resources.getString(R.string.videocard);
        String cpuCooler = MainActivity.resources.getString(R.string.cpu_cooler);
        String motherboard = MainActivity.resources.getString(R.string.motherboard);
        String powerSupply = MainActivity.resources.getString(R.string.power_supply);
        String storageDevice = MainActivity.resources.getString(R.string.storage_devices);
        String pcCase = MainActivity.resources.getString(R.string.pc_case);
        String ram = MainActivity.resources.getString(R.string.ram);
        if (configurerComponent.getComponentType().equals(cpu)) {
            list.replaceAll(c ->
                    c.equals(configurerComponent)
                            ? new CPU(
                            components.get(position).getId(),
                            components.get(position).getName(),
                            components.get(position).getImage(),
                            components.get(position).getComponentType(),
                            components.get(position).getDescription(),
                            components.get(position).getPrice(),
                            true,
                            8,
                            "AM5")
                            : c
            );/*
            list.stream()
                    .filter(c -> c.equals(configurerComponent))
                    .forEach(c -> {
                        c = new CPU(
                                components.get(position).getId(),
                                components.get(position).getName(),
                                components.get(position).getImage(),
                                components.get(position).getComponentType(),
                                components.get(position).getDescription(),
                                components.get(position).getPrice(),
                                true,
                                8,
                                "AM5");

                        Log.e("aaa", c.toString());
                    });*/
            Log.e("list change", Arrays.toString(list.toArray()));

            return list;
        } else if (configurerComponent.getComponentType().equals(videocard)) {
            list.replaceAll(c ->
                    c.equals(configurerComponent)
                            ? new Videocard(
                            components.get(position).getId(),
                            components.get(position).getName(),
                            components.get(position).getImage(),
                            components.get(position).getComponentType(),
                            components.get(position).getDescription(),
                            components.get(position).getPrice(),
                            true,
                            8,
                            "RTX 20")
                            : c
            );
            Log.e("list change", Arrays.toString(list.toArray()));
            return list;
        } else {
            list.stream()
                    .filter(c -> c.equals(configurerComponent))
                    .forEach(c -> {
                        c.setId(components.get(position).getId());
                        c.setName(components.get(position).getName());
                        c.setImage(components.get(position).getImage());
                        c.setComponentType(components.get(position).getComponentType());
                        c.setSelected(true);
                        c.setPrice(components.get(position).getPrice());
                        c.setDescription(components.get(position).getDescription());
                        Log.e("aaa",c.toString());
                    });
            Log.e("list change", Arrays.toString(list.toArray()));
            return list;
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

}
