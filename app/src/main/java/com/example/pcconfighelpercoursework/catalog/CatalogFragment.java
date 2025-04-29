package com.example.pcconfighelpercoursework.catalog;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.configurator.ConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment{

    private static final String COMPOTENT_TYPE = "all";
    private CatalogAdapter catalogAdapter;
    private RecyclerView catalogRecyclerView;
    List<CatalogItem> products;
    private static final String ARG_CHOICE = "0";
    public CatalogFragment() {
    }


    public static CatalogFragment newInstance(String param1,int param2) {
        CatalogFragment fragment = new CatalogFragment();
        Bundle args = new Bundle();
        args.putString(COMPOTENT_TYPE,param1);
        args.putInt(ARG_CHOICE,param2);
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
        catalogAdapter = new CatalogAdapter(getContext(), products ,Integer.parseInt(ARG_CHOICE),this::onAddButtonClickListener);
        catalogRecyclerView = view.findViewById(R.id.catalogRecyclerView);
        catalogRecyclerView.setAdapter(catalogAdapter);
        return view;//todo подумать, убивать фрагмент или нет. Скорее всего да, чтобы адаптер менять
    }


    private void onAddButtonClickListener() {
        this.onDestroy();
    }
}