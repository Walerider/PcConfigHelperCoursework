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
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.items.*;
import com.example.pcconfighelpercoursework.utils.AssemblyAddCompat;
import com.example.pcconfighelpercoursework.utils.AssemblyData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int CATALOG = 0;
    public static final int ADD_CONFIG = 1;//Константы для определения, каталог для добавления, изменения или просто просмотра
    public static final int CHANGE_CONFIG = 2;
    public static final int FILTER_LAYOUT = 3;
    private final LayoutInflater inflater;
    private List<Component> components;
    private Component configurerComponent;
    private int choice;
    private final NavController navController;
    private OnAddButtonClickListener onAddButtonClickListener;

    public CatalogAdapter(Context context, List<Component> components, int choice, NavController navController) {
        this.inflater = LayoutInflater.from(context);
        this.components = components;
        this.choice = choice;
        this.navController = navController;
    }

    public CatalogAdapter(Context context, List<Component> components, int choice, OnAddButtonClickListener onAddButtonClickListener, Component configurerComponent, NavController navController) {
        this.inflater = LayoutInflater.from(context);
        this.components = components;
        this.choice = choice;
        this.onAddButtonClickListener = onAddButtonClickListener;
        this.configurerComponent = configurerComponent;
        this.navController = navController;
        Log.e("constant catalog", String.valueOf(choice));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case FILTER_LAYOUT:
                view = inflater.inflate(R.layout.filter_catalog_layout,parent,false);
                return new FilterViewHolder(view);
            case CHANGE_CONFIG:
            view = inflater.inflate(R.layout.change_catalog_item,parent,false);
            Log.e("viewType createView", String.valueOf(viewType));
            return new ChangeCatalogViewHolder(view);

            case ADD_CONFIG:
            view = inflater.inflate(R.layout.add_catalog_item,parent,false);
            return new AddCatalogViewHolder(view);
            case CATALOG:
                view = inflater.inflate(R.layout.catalog_item,parent,false);
                return new CatalogViewHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.e("item view type", String.valueOf(holder.getItemViewType()));
        switch (holder.getItemViewType()){
            case FILTER_LAYOUT:
                FilterViewHolder filterViewHolder = (FilterViewHolder)holder;
                filterViewHolder.filterTextView.setText(inflater.getContext().getResources().getString(R.string.filter));
                filterViewHolder.filterTextView.setOnClickListener(v -> {
                    navController.navigate(R.id.configurerFragment,null);
                });
                break;
            case CATALOG:
                CatalogViewHolder catalogViewHolder = (CatalogViewHolder)holder;
                catalogViewHolder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
                catalogViewHolder.productNameTextView.setText(components.get(position-1).getName());
                Log.e("component",components.get(position-1).getName());
                Log.e("component id ", String.valueOf(components.get(position-1).getId()));
                Log.e("component price", String.valueOf(components.get(position-1).getPrice()));
                /*Log.e("component attributes", Arrays.toString(components.get(position-1).getAttributes().toArray()));*/
                //Log.e("socket",components.get(position-1).getAttributes().stream().filter(attr -> attr.getName().equals("Сокет")).collect(Collectors.toList()).get(0).getValue());
                catalogViewHolder.productDescriptionTextView.setText(components.get(position-1).getDescription());
                catalogViewHolder.priceTextView.setText("От " + components.get(position-1).getPrice() + "р");
                break;
            case ADD_CONFIG:
                AddCatalogViewHolder addCatalogViewHolder = (AddCatalogViewHolder)holder;
                addCatalogViewHolder.productNameTextView.setText(components.get(position-1).getName());
                addCatalogViewHolder.productDescriptionTextView.setText(components.get(position-1).getDescription());
                addCatalogViewHolder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
                addCatalogViewHolder.priceTextView.setText("От " + components.get(position-1).getPrice() + "p");
                addCatalogViewHolder.addButton.setOnClickListener(v -> {
                    Log.e("configurerComponent",configurerComponent.toString());
                    List<Component> l = addConfigurerItem(MainActivity.getComponents(),configurerComponent,position);
                    AssemblyData.setString(configurerComponent.getComponentType(),components.get(position-1).getName());
                    if(configurerComponent.getComponentType().equals(R.string.cpu) || configurerComponent.getComponentType().equals(R.string.motherboard)){
                        AssemblyData.setString("socket",components.get(position-1).getAttributes().stream().filter(attr -> attr.getName().equals("Сокет")).collect(Collectors.toList()).get(0).getValue());
                    }
                    AssemblyAddCompat.addCompat(components.get(position-1),inflater.getContext().getResources());
                    MainActivity.setComponents(l);
                    onAddButtonClickListener.onAddButtonClick();
                });
                Log.e("aa", String.valueOf(getItemCount()));
                break;
            case CHANGE_CONFIG:
                ChangeCatalogViewHolder changeCatalogViewHolder = (ChangeCatalogViewHolder) holder;
                changeCatalogViewHolder.productNameTextView.setText(components.get(position-1).getName());
                AssemblyAddCompat.addCompat(components.get(position-1),inflater.getContext().getResources());
                changeCatalogViewHolder.productDescriptionTextView.setText(components.get(position-1).getDescription());
                changeCatalogViewHolder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
                changeCatalogViewHolder.priceTextView.setText("От " + components.get(position-1).getPrice() + "p");
                changeCatalogViewHolder.changeButton.setOnClickListener(v -> {
                    Log.e("configurerComponent",configurerComponent.toString());
                    List<Component> l = addConfigurerItem(MainActivity.getComponents(),configurerComponent,position);
                    MainActivity.setComponents(l);
                    onAddButtonClickListener.onAddButtonClick();
                });
                break;
        }

    }


    @Override
    public int getItemCount() {
        return components.size()+1;
    }
    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return FILTER_LAYOUT;
        }
        switch (choice){
            case ADD_CONFIG:
                return ADD_CONFIG;
            case CHANGE_CONFIG:
                return CHANGE_CONFIG;
            default:
                return CATALOG;
        }
    }
    public void setCatalog(List<Component> components){
        this.components.addAll(components);
    };
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
    public static class FilterViewHolder extends RecyclerView.ViewHolder {
        public TextView filterTextView;
        FilterViewHolder(View view){
            super(view);
            filterTextView = view.findViewById(R.id.filterTextView);
            
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
    public static class ChangeCatalogViewHolder extends RecyclerView.ViewHolder {
        public TextView priceTextView;
        public TextView productNameTextView;
        public TextView productDescriptionTextView;
        public ImageButton changeButton;
        public ImageView imageView;
        ChangeCatalogViewHolder(View view){
            super(view);
            priceTextView = view.findViewById(R.id.priceCatalogTextView);
            productNameTextView = view.findViewById(R.id.productNameTextView);
            productDescriptionTextView = view.findViewById(R.id.productDescriptionTextView);
            changeButton = view.findViewById(R.id.changeButtonCatalogImageView);
            imageView = view.findViewById(R.id.imageView);

        }
    }
    public interface OnAddButtonClickListener{
        void onAddButtonClick();
    }
    private List<Component> addConfigurerItem(List<Component> list, Component configurerComponent, int position){
        list.replaceAll(c ->
                c.getComponentType().equals(configurerComponent.getComponentType())
                        ? components.get(position-1).createUpdatedComponent(configurerComponent.getComponentType())
                        : c
        );
        Log.e("list change", Arrays.toString(list.toArray()));

        return list;

    }
}
