package com.example.pcconfighelpercoursework.catalog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.items.*;
import com.example.pcconfighelpercoursework.configurator.HomeFragment;
import com.example.pcconfighelpercoursework.items.Videocard;
import com.example.pcconfighelpercoursework.utils.ItemDecoration;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment {

    private static final String COMPOTENT = "";
    private Component mComponent;
    private int mChoice;
    private CatalogAdapter catalogAdapter;
    private RecyclerView catalogRecyclerView;
    TextView toolbarTitleTextView;
    Toolbar toolbar;
    List<Component> products;
    private static final String ARG_CHOICE = "0";
    public CatalogFragment() {
    }


    public static CatalogFragment newInstance(Component param1, int param2) {
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
        if (getArguments() != null) {
            mComponent = getArguments().getParcelable(COMPOTENT);
            mChoice = getArguments().getInt(ARG_CHOICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        products = new ArrayList<>();
        View view =inflater.inflate(R.layout.fragment_catalog, container, false);
        int spacingInPixels = getActivity().getResources().getDimensionPixelSize(R.dimen.item_spacing);
        catalogRecyclerView = view.findViewById(R.id.catalogRecyclerView);
        catalogRecyclerView.addItemDecoration(new ItemDecoration(spacingInPixels));
        toolbarTitleTextView = view.findViewById(R.id.toolbarTitleTextView);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Component item = mComponent;
        fillproducts(item);
        toolbarTitleTextView.setText(item.getComponentType());
        switch (mChoice){
            case 1:
                Log.e("choice", String.valueOf(mChoice));
                catalogAdapter = new CatalogAdapter(getContext(), products ,mChoice,this::onAddButtonClickListener,item);//todo сделать определение для обознпчения совместимости
                break;
            default:
                catalogAdapter = new CatalogAdapter(getContext(), products ,mChoice);
        }//todo сделать фильтрацию
        catalogRecyclerView.setAdapter(catalogAdapter);
    }


    private void fillproducts(Component item){
        if (item.getComponentType().equals(getActivity().getResources().getString(R.string.videocard)) && products.isEmpty()) {
            products.add(new Videocard(1, "KFA2 GeForce RTX 4060 CORE Black", "", getActivity().getResources().getString(R.string.videocard), "PCIe 4.0 8 ГБ GDDR6, 128 бит, 3 x DisplayPort, HDMI, GPU 1830 МГц", 31_999,false,8,"RTX 40"));
            products.add(new Videocard(2, "Palit GeForce RTX 5070 Ti GamingPro", Integer.toString(R.drawable.palit_geforce_rtx_5070_ti_gamingpro), getActivity().getResources().getString(R.string.videocard), "PCIe 5.0 16 ГБ GDDR7, 256 бит, 3 x DisplayPort, HDMI, GPU 2295 МГц", 93_999,false,8,"RTX 20"));
        } else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.cpu))&& products.isEmpty()) {
            products.add(new CPU(1, "AMD Ryzen 5 5600X", "", getActivity().getResources().getString(R.string.cpu), "AM4, 6 x 3.7 ГГц, L2 - 3 МБ, L3 - 32 МБ, 2 х DDR4-3200 МГц, TDP 65 Вт", 9_499,false,6,"AM4"));
            Log.e("getting item", item.toString());
        } else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.storage_devices))&& products.isEmpty()) {
            products.add(new StorageDevice(1, "Kingston A400", "", getActivity().getResources().getString(R.string.storage_devices), "SATA, чтение - 500 Мбайт/сек, запись - 450 Мбайт/сек, 3D NAND 3 бит TLC, TBW - 160 ТБ", 3799,false,"SATA",480));
            Log.e("getting item", item.toString());
        }else {
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