package com.example.pcconfighelpercoursework.utils;

import android.content.res.Resources;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.items.Component;

public class AssemblyDeleteCompat {
    public static synchronized void deleteCompat(Component component,Resources resources){
        if(component.getComponentType().equals(resources.getString(R.string.cpu))){
            AssemblyData.setInt(resources.getString(R.string.cpu),0);
            if(AssemblyData.getInt(resources.getString(R.string.motherboard)) == 0){
                AssemblyData.setString("socket","");
            }
            return;
        }
        if (component.getComponentType().equals(resources.getString(R.string.motherboard))) {
            AssemblyData.setInt(resources.getString(R.string.motherboard),0);
            if(AssemblyData.getInt(resources.getString(R.string.cpu)) == 0){
                AssemblyData.setString("socket","");
            }
            if(AssemblyData.getInt(resources.getString(R.string.ram)) == 0){
                AssemblyData.setString("ddr type","");
            }
            if(AssemblyData.getInt(resources.getString(R.string.pc_case)) == 0){
                AssemblyData.setString("form_factor","");
            }
            return;
        }
        if(component.getComponentType().equals(resources.getString(R.string.ram))){
            AssemblyData.setInt(resources.getString(R.string.ram),0);
            if(AssemblyData.getInt(resources.getString(R.string.motherboard)) == 0){
                AssemblyData.setString("ddr type","");
            }
            return;
        }
        if(component.getComponentType().equals(resources.getString(R.string.videocard))){
            AssemblyData.setInt(resources.getString(R.string.videocard),0);
            return;
        }
        if (component.getComponentType().equals(resources.getString(R.string.storage_devices))){
            AssemblyData.setInt(resources.getString(R.string.storage_devices),0);
            return;
        }
        if(component.getComponentType().equals(resources.getString(R.string.cpu_cooler))){
            AssemblyData.setInt(resources.getString(R.string.cpu_cooler),0);
            return;
        }
        if(component.getComponentType().equals(resources.getString(R.string.power_supply))){
            AssemblyData.setInt(resources.getString(R.string.power_supply),0);
            return;
        }
        if(component.getComponentType().equals(resources.getString(R.string.pc_case))){
            AssemblyData.setInt(resources.getString(R.string.pc_case),0);
            if(AssemblyData.getInt(resources.getString(R.string.motherboard)) == 0){
                AssemblyData.setString("form_factor","");
            }
        }
    }/*
    public static synchronized void deleteAllCompat(Resources resources) {

        AssemblyData.setString("socket", null);
        AssemblyData.setString("ddr type", null);
        AssemblyData.setString("form_factor", null);
        AssemblyData.setString(resources.getString(R.string.cpu), null);
        AssemblyData.setString(resources.getString(R.string.motherboard), null);
        AssemblyData.setString(resources.getString(R.string.ram), null);
        AssemblyData.setString(resources.getString(R.string.videocard), null);
       AssemblyData.setString(resources.getString(R.string.storage_devices), null);
        AssemblyData.setString(resources.getString(R.string.cpu_cooler), null);
        AssemblyData.setString(resources.getString(R.string.power_supply), null);
        AssemblyData.setString(resources.getString(R.string.pc_case), null);

    }*/
}