package com.example.pcconfighelpercoursework.catalog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcconfighelpercoursework.R;

public class CatalogFragment extends Fragment {

    private String componentType;

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

        return view;
    }
}