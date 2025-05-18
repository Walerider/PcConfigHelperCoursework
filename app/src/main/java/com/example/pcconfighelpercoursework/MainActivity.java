package com.example.pcconfighelpercoursework;


import android.content.Context;
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

import com.example.pcconfighelpercoursework.api.API;
import com.example.pcconfighelpercoursework.api.APIClient;
import com.example.pcconfighelpercoursework.api.items.ProductDAO;
import com.example.pcconfighelpercoursework.items.*;
import com.example.pcconfighelpercoursework.utils.ComponentRepository;
import com.example.pcconfighelpercoursework.utils.DbSingleton;
import com.example.pcconfighelpercoursework.utils.NavigationData;
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
    private ComponentRepository componentRepository;
    private NavController navController;
    protected BottomNavigationView bottomNavigationView;
    protected FragmentContainerView fragmentContainerView;

    public static Resources resources;
    private final Integer[] CATEGORY_COMPONENTS_ID = {1,2,3,4,5,6,7,8};
    private String[] categoryComponentsName;
    private static List<Component> components;
    private static List<List<Component>> catalogComponentsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryComponentsName = new String[]{getResources().getString(R.string.videocard), getResources().getString(R.string.cpu),
                getResources().getString(R.string.motherboard), getResources().getString(R.string.ram), getResources().getString(R.string.pc_case),
                getResources().getString(R.string.power_supply), getResources().getString(R.string.cpu_cooler), getResources().getString(R.string.storage_devices)};
        components = new ArrayList<>();
        resources = getResources();
        componentRepository = new ComponentRepository(this);


        if(!componentRepository.hasAnyComponents()){
            fetchItems(CATEGORY_COMPONENTS_ID[0],categoryComponentsName[0]);
            fetchItems(CATEGORY_COMPONENTS_ID[1],categoryComponentsName[1]);
        }else{
            catalogComponentsList.add(componentRepository.getComponentsByCategory(CATEGORY_COMPONENTS_ID[0],categoryComponentsName[0]));
            catalogComponentsList.add(componentRepository.getComponentsByCategory(CATEGORY_COMPONENTS_ID[1],categoryComponentsName[1]));
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationData.init(this);
        setNavigationStartDestination();
        Log.e("data is creating", String.valueOf(NavigationData.getBoolean("isCreating")));
        bottomNavigationLogic(bottomNavigationView,navController);
    }

    public static List<List<Component>> getCatalogComponentsList() {
        return catalogComponentsList;
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
                navController.navigate(R.id.registerFragment);
                Log.e("navigation", "Rating");
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

    private void fetchItems(int categoryId,String componentType) {
        API apiService = APIClient.getApi();
        Call<List<ProductDAO>> call = apiService.getProductsByCategory(categoryId);
        List<ProductDAO> list = new ArrayList<>();
        call.enqueue(new Callback<List<ProductDAO>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductDAO>> call, @NonNull Response<List<ProductDAO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("API", "Success");
                    list.clear();
                    list.addAll(response.body());
                    componentRepository.insertComponents(list, categoryId);
                    catalogComponentsList.add(list.stream().map(p ->
                            new Component((int) p.getId(), p.getName(), p.getDescription(), componentType,p.getPrice())
                    ).collect(Collectors.toList()));
                    Log.e("API", String.valueOf(catalogComponentsList.get(catalogComponentsList.size()-1).size()));

                } else {
                    Toast.makeText(MainActivity.this, "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ProductDAO>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API", "Ошибка запроса", t);
            }
        });
    }

    public NavController getNavController() {
        return navController;
    }
}
/*protected void getComponentsFromTXT(){//а кто запрещает?
            InputStream fin = null;
            try {
                fin = this.openFileInput("login.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(fin,"UTF8"));
                String name = "";
                String i;
                while((i=in.readLine()) != null){
                    name += i;
                }
                in.close();
            }catch (FileNotFoundException e){
            }
            catch (IOException e) {
                Log.e("login123123", "Can not read file: " + e);
            }
            finally {
                try {
                    if(fin != null)
                        fin.close();
                } catch (IOException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        private void saveComponentsInTxt(Context context, String fileName) {
            String data = "";
            *//*for (String key:getComponents()){
            data += key + "/" + getComponents().get(key) + "$";
        }*//*
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName +".txt",MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e);
        }
    }*/