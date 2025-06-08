package com.example.pcconfighelpercoursework;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.pcconfighelpercoursework.items.*;
import com.example.pcconfighelpercoursework.utils.AssemblyData;
import com.example.pcconfighelpercoursework.utils.NavigationData;
import com.example.pcconfighelpercoursework.utils.UserData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/*import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;*/
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    protected BottomNavigationView bottomNavigationView;
    protected FragmentContainerView fragmentContainerView;
    public static Resources resources;;
    private static List<Component> components;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        components = new ArrayList<>();
        resources = getResources();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationData.init(this);
        UserData.init(this);
        AssemblyData.init(this);
        setNavigationStartDestination();
        components = AssemblyData.loadList();
        Log.e("data is creating", String.valueOf(NavigationData.getBoolean("isCreating")));
        bottomNavigationLogic(bottomNavigationView,navController);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fillComponents(components);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("components save", Arrays.toString(components.toArray()));
        AssemblyData.saveList(components);
    }

    public static List<Component> getComponents() {
        return components;
    }

    public static void setComponents(List<Component> components) {
        MainActivity.components = components;
    }
    public static void fillComponents(List<Component> components){
        if(components.isEmpty()){
            components.add(new CPU(resources.getString(R.string.cpu)));
            components.add(new Videocard(resources.getString(R.string.videocard)));
            components.add(new Motherboard(resources.getString(R.string.motherboard)));
            components.add(new RAM(resources.getString(R.string.ram)));
            components.add(new PowerSupply(resources.getString(R.string.power_supply)));
            components.add(new CPUCooler(resources.getString(R.string.cpu_cooler)));
            components.add(new Cases(resources.getString(R.string.pc_case)));
            components.add(new StorageDevice(resources.getString(R.string.storage_devices)));
        }
    }
    public static boolean checkComponents(List<Component> components){
        AtomicInteger i = new AtomicInteger(0);
        components.forEach(c -> {
            if(c.getId() != 0){
                i.set(1);
            }
        });
        Log.e("check component",i.toString());
        if(i.get() == 0){
            components.clear();
            return false;
        }
        return true;
    }
    public void changeNavigationStartDestination(){
        NavGraph graph = navController.getNavInflater().inflate(R.navigation.bottom_navigation);
        graph.setStartDestination(R.id.configurerFragment);
        navController.setGraph(graph);
        NavigationData.setBoolean("isCreating",true);
    }
    private void setNavigationStartDestination(){
        if(NavigationData.getBoolean("isCreating")){
            NavGraph graph = navController.getNavInflater().inflate(R.navigation.bottom_navigation);
            graph.setStartDestination(R.id.configurerFragment);
            navController.setGraph(graph);
        }else{
            NavGraph graph = navController.getNavInflater().inflate(R.navigation.bottom_navigation);
            graph.setStartDestination(R.id.assemblyChoiceFragment);
            navController.setGraph(graph);
        }
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }


    private void bottomNavigationLogic(BottomNavigationView bottomNavigationView,NavController navController){

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.nav_home){
                if(!NavigationData.getBoolean("isCreating")){
                    navController.navigate(R.id.assemblyChoiceFragment);
                }else{
                    navController.navigate(R.id.configurerFragment);
                }
                Log.e("navigation", "Home");
                return true;
            }
            if (item.getItemId() == R.id.nav_profile){
                if(UserData.getBoolean("isLogin")){

                    navController.navigate(R.id.profileFragment);
                    return true;
                }
                navController.navigate(R.id.registerFragment);
                Log.e("navigation", "profile");
                return true;
            }
            if(item.getItemId() == R.id.nav_catalog && !NavigationData.getBoolean("add")){
                navController.navigate(R.id.catalogChoiceFragment);
                Log.e("navigation", "Catalog");
                return true;
            }
            if(item.getItemId() == R.id.nav_catalog && NavigationData.getBoolean("add")){
                Log.e("navigation", "Catalog");
                return true;
            }
            return false;
        });
    }

    public NavController getNavController() {
        return navController;
    }

}
