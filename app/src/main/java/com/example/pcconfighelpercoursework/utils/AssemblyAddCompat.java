package com.example.pcconfighelpercoursework.utils;

import android.content.res.Resources;
import android.util.Log;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.items.Component;

import java.util.stream.Collectors;

public class AssemblyAddCompat {
    public static synchronized void addCompat(Component component, Resources resources){
        Log.e("addCompat",resources.getString(R.string.cpu));
        Log.e("addCompat",component.getComponentType());


        if(component.getComponentType().equals(resources.getString(R.string.cpu))){
            AssemblyData.setInt(resources.getString(R.string.cpu),component.getId());
            Log.e("addCompat", String.valueOf(AssemblyData.getInt(resources.getString(R.string.cpu))));
            if(AssemblyData.getInt(resources.getString(R.string.motherboard)) == 0){
                AssemblyData.setString("socket",component.getAttributes().stream().filter(attr -> attr.getName().equals("Сокет")).collect(Collectors.toList()).get(0).getValue());
            }
            Log.e("addCompat",AssemblyData.getString("socket"));
            return;
        }
        if (component.getComponentType().equals(resources.getString(R.string.motherboard))) {
            AssemblyData.setInt(resources.getString(R.string.motherboard),component.getId());
            if(AssemblyData.getInt(resources.getString(R.string.cpu)) == 0){
                AssemblyData.setString("socket",component.getAttributes().stream().filter(attr -> attr.getName().equals("Сокет")).collect(Collectors.toList()).get(0).getValue());
            }
            if(AssemblyData.getInt(resources.getString(R.string.ram)) == 0){
                AssemblyData.setString("ddr type",component.getAttributes().stream().filter(attr -> attr.getName().equals("Тип поддерживаемой памяти")).collect(Collectors.toList()).get(0).getValue());
            }
            if(AssemblyData.getInt(resources.getString(R.string.pc_case)) == 0){
                AssemblyData.setString("form_factor",component.getAttributes().stream().filter(attr -> attr.getName().equals("Форм-фактор")).collect(Collectors.toList()).get(0).getValue());
            }
            return;
        }
        if(component.getComponentType().equals(resources.getString(R.string.ram))){
            AssemblyData.setInt(resources.getString(R.string.ram),component.getId());
            if(AssemblyData.getInt(resources.getString(R.string.motherboard)) == 0){
                AssemblyData.setString("ddr type",component.getAttributes().stream().filter(attr -> attr.getName().equals("Тип памяти")).collect(Collectors.toList()).get(0).getValue());
            }
            return;
        }
        if(component.getComponentType().equals(resources.getString(R.string.videocard))){
            AssemblyData.setInt(resources.getString(R.string.videocard),component.getId());
            return;
        }
        if (component.getComponentType().equals(resources.getString(R.string.storage_devices))){
            AssemblyData.setInt(resources.getString(R.string.storage_devices),component.getId());
            return;
        }
        if(component.getComponentType().equals(resources.getString(R.string.cpu_cooler))){
            AssemblyData.setInt(resources.getString(R.string.cpu_cooler),component.getId());
            return;
        }
        if(component.getComponentType().equals(resources.getString(R.string.power_supply))){
            AssemblyData.setInt(resources.getString(R.string.power_supply),component.getId());
            return;
        }
        if(component.getComponentType().equals(resources.getString(R.string.pc_case))){
            AssemblyData.setInt(resources.getString(R.string.pc_case),component.getId());
            if(AssemblyData.getString(resources.getString(R.string.motherboard)).isEmpty()){
                AssemblyData.setString("form_factor",component.getAttributes().stream().filter(attr -> attr.getName().equals("Форм-фактор")).collect(Collectors.toList()).get(0).getValue());
            }
        }
    }
}
