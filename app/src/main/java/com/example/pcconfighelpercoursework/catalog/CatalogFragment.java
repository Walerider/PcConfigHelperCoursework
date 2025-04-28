package com.example.pcconfighelpercoursework.catalog;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;

import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment {

    private String componentType;
    private CatalogAdapter catalogAdapter;
    private RecyclerView catalogRecyclerView;
    List<CatalogItem> products;

    public CatalogFragment() {
    }

    public CatalogFragment(String componentType) {
        this.componentType = componentType;//todo передавать не только тип компонента, но и просмотр каталога( типо просматривают для добавления или просто
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
        products = new ArrayList<>();
        products.add(new CatalogItem(1, "Videocard","1050ti","","Videocard","description",20000));
        View view =inflater.inflate(R.layout.fragment_catalog, container, false);
        catalogAdapter = new CatalogAdapter(getContext(), products ,true);
        catalogRecyclerView = view.findViewById(R.id.catalogRecyclerView);
        catalogRecyclerView.setAdapter(catalogAdapter);
        return view;//todo подумать, убивать фрагмент или нет. Скорее всего да, чтобы адаптер менять
    }
}