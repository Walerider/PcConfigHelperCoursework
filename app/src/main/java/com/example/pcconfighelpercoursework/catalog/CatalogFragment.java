package com.example.pcconfighelpercoursework.catalog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.configurator.items.ConfigurerItem;
import com.example.pcconfighelpercoursework.configurator.HomeFragment;

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
        args.putParcelable(COMPOTENT,param1);
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
        View view =inflater.inflate(R.layout.fragment_catalog, container, false);
        catalogRecyclerView = view.findViewById(R.id.catalogRecyclerView);

        return view;//todo подумать, убивать фрагмент или нет. Скорее всего да, чтобы адаптер менять
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        ConfigurerItem item = getArguments().getParcelable(COMPOTENT);
        fillproducts(item);
        switch (getArguments().getInt(ARG_CHOICE)){
            case 1:
                Log.e("choice",ARG_CHOICE);
                catalogAdapter = new CatalogAdapter(getContext(), products ,getArguments().getInt(ARG_CHOICE),this::onAddButtonClickListener,item);
                break;
            default:
                catalogAdapter = new CatalogAdapter(getContext(), products ,getArguments().getInt(ARG_CHOICE));
        }
        catalogRecyclerView.setAdapter(catalogAdapter);
    }

    private void fillproducts(ConfigurerItem item){
        if (item.getComponentType().equals(getActivity().getResources().getString(R.string.videocard))) {
            products.add(new CatalogItem(1, "Videocard", "1050ti", "", getActivity().getResources().getString(R.string.videocard), "description", 20000));
            products.add(new CatalogItem(2, "Videocard", "2060 super", "", getActivity().getResources().getString(R.string.videocard), "description", 40000));
        } else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.cpu))) {
            products.add(new CatalogItem(1, "CPU", "Ryzen 5 5700x", "", getActivity().getResources().getString(R.string.cpu), "[AM5, 6 x 3.7 ГГц, L2 - 6 МБ, L3 - 32 МБ, 2 х DDR5-5200 МГц, TDP 65 Вт]", 20000));
            Log.e("getting item", item.toString());
        } else {
            Log.e("getting item", item.toString());
        }//todo Переместить это в utils и заполнять через запросы к api
    }
    private void onAddButtonClickListener() {
        HomeFragment homeFragment = (HomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag("home_fragment");
        if(homeFragment != null){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, homeFragment, "home_fragment")
                    .addToBackStack("home_fragment_backstack")
                    .commit();

        }
    }
}