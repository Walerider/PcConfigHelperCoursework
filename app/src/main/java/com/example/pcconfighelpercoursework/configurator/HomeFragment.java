package com.example.pcconfighelpercoursework.configurator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.catalog.CatalogFragment;

import java.util.List;

public class HomeFragment extends Fragment implements ConfigurerAdapter.OnAddButtonClickListener{

    private List<ConfigurerItem> components;
    RecyclerView recyclerView;
    ConfigurerAdapter configurerAdapter;

    public HomeFragment() {
    }
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        components = MainActivity.getComponents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_home, container, false);
        configurerAdapter = new ConfigurerAdapter(components,getContext());
        configurerAdapter.setOnAddButtonClickListener(this::onAddClick);
        recyclerView = mainView.findViewById(R.id.compView);
        recyclerView.setAdapter(configurerAdapter);
        return mainView;
    }

    @Override
    public void onAddClick(String componentType) {
        CatalogFragment catalogFragment = CatalogFragment.newInstance(componentType);
        Log.e("asdasdfgfg",componentType);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, catalogFragment)
                .addToBackStack("fragmentContainerView")
                .commit();
    }
}