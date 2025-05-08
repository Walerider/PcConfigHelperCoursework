package com.example.pcconfighelpercoursework.configurator;

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
import com.example.pcconfighelpercoursework.items.CPU;
import com.example.pcconfighelpercoursework.items.CPUCooler;
import com.example.pcconfighelpercoursework.items.Cases;
import com.example.pcconfighelpercoursework.items.Component;
import com.example.pcconfighelpercoursework.items.Motherboard;
import com.example.pcconfighelpercoursework.items.PowerSupply;
import com.example.pcconfighelpercoursework.items.RAM;
import com.example.pcconfighelpercoursework.items.StorageDevice;
import com.example.pcconfighelpercoursework.items.Videocard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int SL_TYPE_NOT_CHOICE = 0;
    public static final int SL_TYPE_CHOICE = 1;
    public static final int SL_TYPE_STORAGE = 2;
    private List<Component> components;
    private Map<String, Component> emptyComponents;
    private final LayoutInflater inflater;
    private OnAddButtonClickListener onAddButtonClickListener;

    public ConfigurerAdapter(List<Component> components, Context context) {
        this.components = components;
        //Collections.reverse(this.components);
        Log.e("asdasd", Arrays.toString(components.toArray()));
        this.inflater = LayoutInflater.from(context);
        emptyComponents = new HashMap<>();
        emptyComponents.put(MainActivity.resources.getString(R.string.cpu),new CPU(MainActivity.resources.getString(R.string.cpu)));
        emptyComponents.put(MainActivity.resources.getString(R.string.videocard),new Videocard(MainActivity.resources.getString(R.string.videocard)));
        emptyComponents.put(MainActivity.resources.getString(R.string.motherboard),new Motherboard(MainActivity.resources.getString(R.string.motherboard)));
        emptyComponents.put(MainActivity.resources.getString(R.string.ram),new RAM(MainActivity.resources.getString(R.string.ram)));
        emptyComponents.put(MainActivity.resources.getString(R.string.power_supply),new PowerSupply(MainActivity.resources.getString(R.string.power_supply)));
        emptyComponents.put(MainActivity.resources.getString(R.string.storage_devices),new StorageDevice(MainActivity.resources.getString(R.string.storage_devices)));
        emptyComponents.put(MainActivity.resources.getString(R.string.cpu_cooler),new CPUCooler(MainActivity.resources.getString(R.string.cpu_cooler)));
        emptyComponents.put(MainActivity.resources.getString(R.string.pc_case),new Cases(MainActivity.resources.getString(R.string.pc_case)));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SL_TYPE_NOT_CHOICE) {

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
            case SL_TYPE_CHOICE:
                SelectedViewHolder selectedViewHolder = (SelectedViewHolder) holder;
                selectedViewHolder.productNameTextView.setText(components.get(position).getName());
                selectedViewHolder.productDescriptionTextView.setText(components.get(position).getDescription());
                selectedViewHolder.priceCatalogTextView.setText(String.valueOf(components.get(position).getPrice()) + "р");
                selectedViewHolder.imageView.setImageResource(!components.get(position).getImage().equals("") ? Integer.parseInt(components.get(position).getImage()) : R.drawable.ic_launcher_foreground);
                selectedViewHolder.clearButton.setOnClickListener(v ->{
                    components.set(position,emptyComponents.get(MainActivity.getComponents().get(position).getComponentType()));
                    //Log.e("components in delete button adapter",Arrays.toString(components.toArray()));
                    if(!MainActivity.checkComponents(components)){
                        MainActivity.fillComponents(components);
                    }
                    MainActivity.setComponents(components);
                    notifyDataSetChanged();
                });
                break;
            case SL_TYPE_NOT_CHOICE:
                NotSelectedViewHolder notSelectedViewHolder = (NotSelectedViewHolder) holder;
                notSelectedViewHolder.componentType.setText(components.get(position).getComponentType());
                notSelectedViewHolder.addButton.setOnClickListener(v -> {
                    onAddButtonClickListener.onAddClick(components.get(position));
                });
                break;
            case SL_TYPE_STORAGE:
                SelectedViewHolderStorage selectedViewHolderStorage = (SelectedViewHolderStorage) holder;
                selectedViewHolderStorage.productNameTextView.setText(components.get(position).getName());
                selectedViewHolderStorage.productDescriptionTextView.setText(components.get(position).getDescription());
                selectedViewHolderStorage.priceCatalogTextView.setText(String.valueOf(components.get(position).getPrice()) + "р");
                selectedViewHolderStorage.clearButton.setOnClickListener(v ->{
                    components.set(position,emptyComponents.get(MainActivity.getComponents().get(position).getComponentType()));
                    //Log.e("components in delete button adapter",Arrays.toString(components.toArray()));
                    if(!MainActivity.checkComponents(components)){
                        MainActivity.fillComponents(components);
                    }
                    MainActivity.setComponents(components);
                    notifyDataSetChanged();
                });
                break;
        }

    }


    @Override
    public int getItemCount() {
        return components.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (components.get(position).isSelected()) {
            if(components.get(position).getComponentType().equals(R.string.storage_devices)){
                return SL_TYPE_STORAGE;
            }
            return SL_TYPE_CHOICE;
        } else {
            return SL_TYPE_NOT_CHOICE;
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
            addButton = view.findViewById(R.id.addImageButton);
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
    public static class SelectedViewHolderStorage extends RecyclerView.ViewHolder {
        final TextView productNameTextView;
        final TextView productDescriptionTextView;
        final TextView priceCatalogTextView;
        final ImageButton clearButton;
        final ImageButton changeButton;
        final ImageView imageView;

        SelectedViewHolderStorage(View view) {
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
        void onAddClick(Component item);
    }

}