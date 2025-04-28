package com.example.pcconfighelpercoursework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class CatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int SL_TYPE_NOT = 0;
    public static final int SL_TYPE_YES = 1;
    private final LayoutInflater inflater;
    private List<ConfigurerItem> components;

    public CatalogAdapter(LayoutInflater inflater, List<ConfigurerItem> components) {
        this.inflater = inflater;
        this.components = components;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SL_TYPE_NOT){

            View view = inflater.inflate(R.layout.not_selected_pc_config_item,parent,false);
            ConfigurerAdapter.NotSelectedViewHolder viewHolder = new ConfigurerAdapter.NotSelectedViewHolder(view);
            return viewHolder;
        }
        else{
            View view = inflater.inflate(R.layout.selected_pc_config_item,parent,false);
            ConfigurerAdapter.SelectedViewHolder viewHolder = new ConfigurerAdapter.SelectedViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case SL_TYPE_YES:
                ConfigurerAdapter.SelectedViewHolder selectedViewHolder = (ConfigurerAdapter.SelectedViewHolder)holder;
                break;
            case SL_TYPE_NOT:
                ConfigurerAdapter.NotSelectedViewHolder notSelectedViewHolder = (ConfigurerAdapter.NotSelectedViewHolder)holder;
                break;
        }

    }


    @Override
    public int getItemCount() {
        return 0;
    }
    @Override
    public int getItemViewType(int position) {
        if(components.get(position).isSelected()){
            return SL_TYPE_YES;
        }else{
            return SL_TYPE_NOT;
        }
    }
    public static class CatalogViewHolder extends RecyclerView.ViewHolder {
        public TextView componentType;
        public ImageButton addButton;
        CatalogViewHolder(View view){
            super(view);
            componentType = view.findViewById(R.id.componentName);
            addButton = view.findViewById(R.id.addButton);
            //todo Доделать каталог 28/29!!!!!!!
        }
    }
    public static class AddViewHolder extends RecyclerView.ViewHolder {
        public TextView priceTextView;
        public ImageButton addButton;
        public ImageView imageView;
        AddViewHolder(View view){
            super(view);
            priceTextView = view.findViewById(R.id.priceCatalogTextView);
            addButton = view.findViewById(R.id.addButtonCatalog);
            imageView = view.findViewById(R.id.imageView);
        }
    }
}
