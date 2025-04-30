package com.example.pcconfighelpercoursework.configurator;

import android.annotation.SuppressLint;
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

import java.util.Arrays;
import java.util.List;

public class ConfigurerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int SL_TYPE_NOT = 0;
    public static final int SL_TYPE_YES = 1;
    private List<ConfigurerItem> components;
    private ConfigurerItem emptyComponent;
    private final LayoutInflater inflater;
    private OnAddButtonClickListener onAddButtonClickListener;

    public ConfigurerAdapter(List<ConfigurerItem> components, Context context) {
        this.components = components;
        //Collections.reverse(this.components);
        Log.e("asdasd", Arrays.toString(components.toArray()));
        this.inflater = LayoutInflater.from(context);
        emptyComponent = new ConfigurerItem();
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SL_TYPE_NOT) {

            View view = inflater.inflate(R.layout.not_selected_pc_config_item, parent, false);
            NotSelectedViewHolder viewHolder = new NotSelectedViewHolder(view);

            return viewHolder;
        } else {
            View view = inflater.inflate(R.layout.selected_pc_config_item, parent, false);
            SelectedViewHolder viewHolder = new SelectedViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case SL_TYPE_YES:
                SelectedViewHolder selectedViewHolder = (SelectedViewHolder) holder;
                selectedViewHolder.productNameTextView.setText(components.get(position).getName());
                selectedViewHolder.productDescriptionTextView.setText(components.get(position).getDescription());
                selectedViewHolder.priceCatalogTextView.setText(String.valueOf(components.get(position).getPrice()) + "р");
                selectedViewHolder.clearButton.setOnClickListener(v ->{
                    emptyComponent.setComponentType(MainActivity.getComponents().get(position).getComponentType());
                    MainActivity.getComponents().set(position,emptyComponent);
                    //Log.e("components in delete button adapter",Arrays.toString(components.toArray()));
                    if(!MainActivity.checkComponents(components)){
                        MainActivity.fillComponents(components);
                    }
                    MainActivity.setComponents(components);
                    notifyDataSetChanged();
                });
                break;
            case SL_TYPE_NOT:
                NotSelectedViewHolder notSelectedViewHolder = (NotSelectedViewHolder) holder;
                notSelectedViewHolder.componentType.setText(components.get(position).getComponentType());
                notSelectedViewHolder.addButton.setOnClickListener(v -> {
                    onAddButtonClickListener.onAddClick(components.get(position));
                });
                break;
        }

    }


    @Override
    public int getItemCount() {
        return components.size();
    }

    @Override
    public int getItemViewType(int position) { // TODO: 20.09.2024 написать определение того, выбрал ли человек комплектуху 
        if (components.get(position).isSelected()) {
            return SL_TYPE_YES;
        } else {
            return SL_TYPE_NOT;
        }
    }

    public void setOnAddButtonClickListener(OnAddButtonClickListener AddButtonListener) {
        this.onAddButtonClickListener = AddButtonListener;
    }

    public OnAddButtonClickListener getOnAddButtonClickListener() {
        return onAddButtonClickListener;
    }

    public static class NotSelectedViewHolder extends RecyclerView.ViewHolder {
        final TextView componentType;
        final ImageButton addButton;

        NotSelectedViewHolder(View view) {
            super(view);
            componentType = view.findViewById(R.id.productNameTextView);
            addButton = view.findViewById(R.id.addButton);
        }
    }

    public static class SelectedViewHolder extends RecyclerView.ViewHolder {
        final TextView productNameTextView;
        final TextView productDescriptionTextView;
        final TextView priceCatalogTextView;
        final ImageButton clearButton;
        final ImageButton changeButton;
        final ImageView imageView;

        SelectedViewHolder(View view) {
            super(view);
            productNameTextView = view.findViewById(R.id.productNameTextView);
            productDescriptionTextView = view.findViewById(R.id.productDescriptionTextView);
            priceCatalogTextView = view.findViewById(R.id.priceCatalogTextView);
            clearButton = view.findViewById(R.id.clearButton);
            changeButton = view.findViewById(R.id.changeButton);
            imageView = view.findViewById(R.id.imageView);
        }

    }

    public interface OnAddButtonClickListener {
        void onAddClick(ConfigurerItem item);
    }

}