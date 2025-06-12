package com.example.pcconfighelpercoursework.assemblies;

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
import com.example.pcconfighelpercoursework.api.items.UserAssemblyDAO;
import com.example.pcconfighelpercoursework.catalog.CatalogAdapter;
import com.example.pcconfighelpercoursework.items.Component;

import java.util.ArrayList;
import java.util.List;

public class AssemblyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int CATALOG = 0;;
    private final LayoutInflater inflater;
    List<UserAssemblyDAO> userAssemblies;

    public AssemblyAdapter(Context context, List<UserAssemblyDAO> userAssemblies) {
        this.inflater = LayoutInflater.from(context);
        this.userAssemblies = userAssemblies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.assembly_adapter_item,parent,false);
        return new AssemblyAdapter.AssemblyViewHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AssemblyAdapter.AssemblyViewHolder assemblyViewHolder = (AssemblyAdapter.AssemblyViewHolder)holder;
        assemblyViewHolder.assemblyNameTextView.setText(userAssemblies.get(position).getName());
        String components = "Процессор:\n" + userAssemblies.get(position).getUserAssemblyComponents().stream().filter(ua -> ua.getComponentCategory().equals("Процессор")).findFirst().get().getProductName()
                + "\nВидеокарта:\n" + userAssemblies.get(position).getUserAssemblyComponents().stream().filter(ua -> ua.getComponentCategory().equals("Видеокарта")).findFirst().get().getProductName() +
                "\nМатеринская плата:\n" + userAssemblies.get(position).getUserAssemblyComponents().stream().filter(ua -> ua.getComponentCategory().equals("Материнская плата")).findFirst().get().getProductName();
        assemblyViewHolder.assemblyMainComponentsTextView.setText(components);
        assemblyViewHolder.caseImageView.setImageResource(R.drawable.ic_launcher_foreground);
        assemblyViewHolder.priceTextView.setText(userAssemblies.get(position).getPrice() + "р");
        assemblyViewHolder.itemView.setOnClickListener(v ->{

        });
    }


    @Override
    public int getItemCount() {
        return userAssemblies.size();
    }


    public static class AssemblyViewHolder extends RecyclerView.ViewHolder {
        public TextView assemblyNameTextView;
        public TextView assemblyMainComponentsTextView;
        public ImageView caseImageView;
        public TextView priceTextView;
        AssemblyViewHolder(View view){
            super(view);
            assemblyNameTextView = view.findViewById(R.id.assemblyNameTextView);
            assemblyMainComponentsTextView = view.findViewById(R.id.assemblyMainComponentsTextView);
            caseImageView = view.findViewById(R.id.caseImageView);
            priceTextView = view.findViewById(R.id.priceTextView);
        }
    }
}