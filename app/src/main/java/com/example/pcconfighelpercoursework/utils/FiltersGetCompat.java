package com.example.pcconfighelpercoursework.utils;

import android.content.res.Resources;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;
import com.example.pcconfighelpercoursework.items.Component;

import java.util.ArrayList;
import java.util.List;

public class FiltersGetCompat {
    public static synchronized List<ProductAttributeDAO> getFiltersFromShared(Component component, Resources resources){
        List<ProductAttributeDAO> filtersList = new ArrayList<>();
        if(component.getComponentType().equals(resources.getString(R.string.cpu))){
            if(AssemblyData.getInt(resources.getString(R.string.motherboard)) != 0){
                filtersList.add(new ProductAttributeDAO("Сокет",AssemblyData.getString("socket")));
            }
            return filtersList;
        }
        if (component.getComponentType().equals(resources.getString(R.string.motherboard))) {
            if(AssemblyData.getInt(resources.getString(R.string.cpu)) != 0){
                filtersList.add(new ProductAttributeDAO("Сокет",AssemblyData.getString("socket")));
            }
            if(AssemblyData.getInt(resources.getString(R.string.ram)) != 0){
                filtersList.add(new ProductAttributeDAO("Тип поддерживаемой памяти",AssemblyData.getString("ddr type")));
            }
            if(AssemblyData.getInt(resources.getString(R.string.pc_case)) != 0){
                if(AssemblyData.getString("form_factor").contains(",")){
                    String[] split = AssemblyData.getString("form_factor").split(",");
                    for (String s : split) {
                        filtersList.add(new ProductAttributeDAO("Форм-фактор",s));
                    }
                }
            }
            return filtersList;
        }
        if(component.getComponentType().equals(resources.getString(R.string.ram))){;
            if(AssemblyData.getInt(resources.getString(R.string.motherboard)) != 0){
                filtersList.add(new ProductAttributeDAO("Тип памяти",AssemblyData.getString("ddr type")));
            }
            return filtersList;
        }
        if(component.getComponentType().equals(resources.getString(R.string.videocard))){
            return filtersList;
        }
        if (component.getComponentType().equals(resources.getString(R.string.storage_devices))){
            return filtersList;
        }
        if(component.getComponentType().equals(resources.getString(R.string.cpu_cooler))){
            if(AssemblyData.getInt(resources.getString(R.string.cpu)) != 0 || AssemblyData.getInt(resources.getString(R.string.motherboard)) != 0){
                filtersList.add(new ProductAttributeDAO("Сокет",AssemblyData.getString("socket")));
            }
            return filtersList;
        }
        if(component.getComponentType().equals(resources.getString(R.string.power_supply))){
            return filtersList;
        }
        if(component.getComponentType().equals(resources.getString(R.string.pc_case))){
            if(AssemblyData.getInt(resources.getString(R.string.motherboard)) != 0){
                filtersList.add(new ProductAttributeDAO("Форм-фактор совместимых плат",AssemblyData.getString("form_factor")));
            }
            return filtersList;
        }
        return filtersList;
    }
}
