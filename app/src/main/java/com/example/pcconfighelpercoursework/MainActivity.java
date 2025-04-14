package com.example.pcconfighelpercoursework;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;
    protected FragmentContainerView fragmentContainerView;
    public static Map<String,ConfigurerItem> components;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentContainerView = findViewById(R.id.fragmentContainerView);
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        components = new HashMap<>();
    }

    public static Map<String,ConfigurerItem> getComponents() {
        return components;
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
        for (String key:getComponents().keySet()){
            data += key + "/" + getComponents().get(key) + "$";
        }
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName +".txt",MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e);
        }
    }
}