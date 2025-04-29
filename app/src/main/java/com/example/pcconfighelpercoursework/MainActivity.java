package com.example.pcconfighelpercoursework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.pcconfighelpercoursework.configurator.ConfigurerItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;
    protected FragmentContainerView fragmentContainerView;
    private NavController navController;
    private static List<ConfigurerItem> components;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        components = new ArrayList<>();
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
        fillComponents(components,getResources());

    }

    public static List<ConfigurerItem> getComponents() {
        return components;
    }

    public static void setComponents(List<ConfigurerItem> components) {
        MainActivity.components = components;
    }
    public static void fillComponents(List<ConfigurerItem> components, Resources resources){
        components.add(new ConfigurerItem(resources.getString(R.string.cpu)));
        components.add(new ConfigurerItem(resources.getString(R.string.videocard)));

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