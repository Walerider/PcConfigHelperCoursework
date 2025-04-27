package com.example.pcconfighelpercoursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class ConfigurerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int SL_TYPE_NOT = 0;
    public static final int SL_TYPE_YES = 1;
    private Map<String,ConfigurerItem> components;
    private final LayoutInflater inflater;

    public ConfigurerAdapter(Map<String,ConfigurerItem> components, Context context) {
        this.components = components;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SL_TYPE_NOT){

            View view = inflater.inflate(R.layout.not_selected_pc_config_item,parent,false);
            NotSelectedViewHolder viewHolder = new NotSelectedViewHolder(view);
            return viewHolder;
        }
        else{
            View view = inflater.inflate(R.layout.selected_pc_config_item,parent,false);
            SelectedViewHolder viewHolder = new SelectedViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case SL_TYPE_YES:
                SelectedViewHolder selectedViewHolder = (SelectedViewHolder)holder;
                break;
            case SL_TYPE_NOT:
                NotSelectedViewHolder notSelectedViewHolder = (NotSelectedViewHolder)holder;
                break;
        }

    }


    @Override
    public int getItemCount() {
        return 0;
    }
    @Override
    public int getItemViewType(int position) { // TODO: 20.09.2024 написать определение того, выбрал ли человек комплектуху 
        if(components.get(position).isSelected()){
            return SL_TYPE_YES;
        }else{
            return SL_TYPE_NOT;
        }
    }
    public static class NotSelectedViewHolder extends RecyclerView.ViewHolder {
        public TextView componentType;
        public ImageButton addButton;
        NotSelectedViewHolder(View view){
            super(view);
            componentType = view.findViewById(R.id.componentName);
            addButton = view.findViewById(R.id.addButton);
            addButton.setOnClickListener(v -> {
                FragmentManager fragmentManager = FragmentManager.findFragment(view).getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.fragmentContainerView,);
            });
        }
    }
    public static class SelectedViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public ImageButton clearButton;
        public ImageButton changeButton;
        public ImageView imageView;
        SelectedViewHolder(View view){
            super(view);
            productName = view.findViewById(R.id.productName);
            clearButton = view.findViewById(R.id.clearButton);
            changeButton = view.findViewById(R.id.changeButton);
            imageView = view.findViewById(R.id.imageView);
        }
    }
}
