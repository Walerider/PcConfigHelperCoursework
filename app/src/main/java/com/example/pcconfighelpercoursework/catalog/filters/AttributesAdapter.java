package com.example.pcconfighelpercoursework.catalog.filters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;
import com.example.pcconfighelpercoursework.utils.SharedViewModel;

import java.util.Arrays;
import java.util.Map;

public class AttributesAdapter  extends RecyclerView.Adapter<AttributesAdapter.AttributesViewHolder>{
    private String filterName;
    private Map<String,Integer> subItems;

    public AttributesAdapter(String filterName, Map<String, Integer> subItems) {
        this.filterName = filterName;
        this.subItems = subItems;
    }

    @NonNull
    @Override
    public AttributesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_sub_item,parent,false);
        return new AttributesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AttributesViewHolder holder, int position) {
        int i = 0;
        for (String key: subItems.keySet()) {
            if(i == position){
                holder.bind(key,subItems.get(key));
                holder.checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                    ProductAttributeDAO attribute = new ProductAttributeDAO(filterName, key);
                    if(isChecked){
                        FilterChoiceFragment.filtersSend.add(attribute);
                        Log.e("filtersSend", Arrays.toString(FilterChoiceFragment.filtersSend.toArray()));
                    }else{
                        FilterChoiceFragment.filtersSend.removeIf(item ->
                                item.getName().equals(filterName) && item.getValue().equals(key)
                        );
                        Log.e("filtersSend", Arrays.toString(FilterChoiceFragment.filtersSend.toArray()));
                    }
                }));
                break;
            }
            i++;
        }
    }

    @Override
    public int getItemCount() {
        return subItems.keySet().size();
    }

    class AttributesViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        private final TextView countTextView;
        public AttributesViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            countTextView = itemView.findViewById(R.id.countTextView);
        }

        public void bind(String name,Integer value) {
            checkBox.setText(name);
            countTextView.setText("(" + value + ")");
        }
    }
}
