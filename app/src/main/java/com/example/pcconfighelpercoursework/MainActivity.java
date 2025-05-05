package com.example.pcconfighelpercoursework;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.pcconfighelpercoursework.items.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;
    protected FragmentContainerView fragmentContainerView;
    private NavController navController;
    public static Resources resources;

    private static List<Component> components;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        components = new ArrayList<>();
        resources = getResources();
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
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
        components.stream().forEach(c -> {
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

    protected void getComponentsFromTXT(){//а кто запрещает?
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
        /*for (String key:getComponents().keySet()){
            data += key + "/" + getComponents().get(key) + "$";
        }*/
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName +".txt",MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e);
        }
    }
    private void bottomNavigationLogic(BottomNavigationView bottomNavigationView,NavController navController){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.nav_home){
                navController.navigate(R.id.homeFragment);

                return true;
            }
            if (item.getItemId() == R.id.nav_rating){
                navController.navigate(R.id.ratingFragment);
                return true;
            }
            return false;
        });
    }
}