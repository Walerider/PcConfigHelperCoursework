package com.example.pcconfighelpercoursework.catalog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;

import java.util.ArrayList;

public class CatalogFragment extends Fragment {

    private String componentType;
    private CatalogAdapter catalogAdapter;
    public CatalogFragment() {
    }

    public CatalogFragment(String componentType) {
        this.componentType = componentType;
    }

    public static CatalogFragment newInstance(String componentType) {
        CatalogFragment fragment = new CatalogFragment(componentType);
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_catalog, container, false);
        catalogAdapter = new CatalogAdapter(getContext(), new ArrayList<CatalogItem>(),false);
        //todo доделать
        return view;
    }
}