package com.example.pcconfighelpercoursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConfigurerAdapter extends RecyclerView.Adapter<ConfigurerAdapter.ViewHolder>{
    public static final int SL_TYPE_NOT = 0;
    public static final int SL_TYPE_YES = 1;
    private List<ConfigurerItem> components;
    private final LayoutInflater inflater;

    public ConfigurerAdapter(List<ConfigurerItem> components, Context context) {
        this.components = components;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SL_TYPE_NOT){
            View view = inflater.inflate(R.layout.not_selected_pc_config_item,parent,false);
            return new ViewHolder(view,SL_TYPE_NOT);
        }
        else{
            View view = inflater.inflate(R.layout.selected_pc_config_item,parent,false);
            return new ViewHolder(view,SL_TYPE_YES);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { // TODO: 20.09.2024 написать определение того, выбрал ли человек комплектуху

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
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView
        ViewHolder(View view,int type){
            super(view);
        }
    }
}
