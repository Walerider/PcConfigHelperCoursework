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
import com.example.pcconfighelpercoursework.configurator.ConfigurerItem;

import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment{

    private static final String COMPOTENT = "";
    private CatalogAdapter catalogAdapter;
    private RecyclerView catalogRecyclerView;
    List<CatalogItem> products;
    private static final String ARG_CHOICE = "0";
    public CatalogFragment() {
    }


    public static CatalogFragment newInstance(ConfigurerItem param1, int param2) {
        CatalogFragment fragment = new CatalogFragment();
        Bundle args = new Bundle();
        args.putParcelable(COMPOTENT,param1);//то
        args.putInt(ARG_CHOICE,param2);
        fragment.setArguments(args);
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
        ConfigurerItem item = getArguments().getParcelable(COMPOTENT);
        fillproducts(item);
        View view =inflater.inflate(R.layout.fragment_catalog, container, false);
        catalogAdapter = new CatalogAdapter(getContext(), products ,getArguments().getInt(ARG_CHOICE),this::onAddButtonClickListener,item);
        catalogRecyclerView = view.findViewById(R.id.catalogRecyclerView);
        catalogRecyclerView.setAdapter(catalogAdapter);
        return view;//todo подумать, убивать фрагмент или нет. Скорее всего да, чтобы адаптер менять
    }


    private void fillproducts(ConfigurerItem item){
        if (item.getComponentType().equals(getActivity().getResources().getString(R.string.videocard))) {
            products.add(new CatalogItem(1, "Videocard", "1050ti", "", "Videocard", "description", 20000));
            products.add(new CatalogItem(2, "Videocard", "2060 super", "", "Videocard", "description", 40000));
        } else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.cpu))) {
            products.add(new CatalogItem(1, "CPU", "Ryzen 5 5700x", "", "CPU", "[AM5, 6 x 3.7 ГГц, L2 - 6 МБ, L3 - 32 МБ, 2 х DDR5-5200 МГц, TDP 65 Вт]", 20000));
            Log.e("getting item", item.toString());
        } else {
            Log.e("getting item", item.toString());
        }
    }
    private void onAddButtonClickListener() {
        this.onDestroy();
    }
}