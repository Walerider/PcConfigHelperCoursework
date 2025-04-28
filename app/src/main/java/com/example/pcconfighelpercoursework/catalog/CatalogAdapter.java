package com.example.pcconfighelpercoursework.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfighelpercoursework.configurator.ConfigurerAdapter;
import com.example.pcconfighelpercoursework.configurator.ConfigurerItem;
import com.example.pcconfighelpercoursework.R;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int SL_TYPE_NOT = 0;
    public static final int SL_TYPE_YES = 1;
    private final LayoutInflater inflater;
    private List<CatalogItem> components;
    private boolean isConfigAdd;


    public CatalogAdapter(LayoutInflater inflater, List<CatalogItem> components, boolean isConfigAdd) {
        this.inflater = inflater;
        this.components = components;
        this.isConfigAdd = isConfigAdd;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SL_TYPE_NOT){

            View view = inflater.inflate(R.layout.not_selected_pc_config_item,parent,false);
            return new CatalogViewHolder(view);
        }
        else{
            View view = inflater.inflate(R.layout.selected_pc_config_item,parent,false);
            return new AddCatalogViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case SL_TYPE_NOT:
                CatalogAdapter.CatalogViewHolder catalogViewHolder = (CatalogAdapter.CatalogViewHolder)holder;
                break;
            case SL_TYPE_YES:
                CatalogAdapter.AddCatalogViewHolder addCatalogViewHolder = (CatalogAdapter.AddCatalogViewHolder)holder;
                break;
        }

    }


    @Override
    public int getItemCount() {
        return 0;
    }
    @Override
    public int getItemViewType(int position) {
        if(isConfigAdd){
            return SL_TYPE_YES;
        }else{
            return SL_TYPE_NOT;
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
            addButton = view.findViewById(R.id.addButtonCatalog);
            imageView = view.findViewById(R.id.imageView);
        }
    }
}
