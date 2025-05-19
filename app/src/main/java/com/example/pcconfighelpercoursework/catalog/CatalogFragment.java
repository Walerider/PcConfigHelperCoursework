package com.example.pcconfighelpercoursework.catalog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.configurator.ConfigurerFragment;
import com.example.pcconfighelpercoursework.items.*;
import com.example.pcconfighelpercoursework.utils.ItemDecoration;
import com.example.pcconfighelpercoursework.utils.NavigationData;

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
        Log.e("args catalog",param1.toString());
        Bundle args = new Bundle();
        args.putParcelable("component", param1);
        args.putInt("type", param2);
        Log.e("bundleGet",args.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mComponent = getArguments().getParcelable("component");
            mChoice = getArguments().getInt("type");
            Log.e("asdasfgghh",getArguments().toString());
            Log.e("mChoice", String.valueOf(mChoice));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        products = new ArrayList<>();
        Log.e("mChoice", String.valueOf(mChoice));
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
        Log.e("mChoice", String.valueOf(mChoice));
        switch (mChoice){
            case 0:
                catalogAdapter = new CatalogAdapter(getContext(), products ,mChoice);
                break;
            case 1:
                Log.e("choice", String.valueOf(mChoice));
            if(!products.isEmpty()){
                catalogAdapter = new CatalogAdapter(getContext(), products, mChoice, this::onAddButtonClickListener, item);
            }else{
                Toast.makeText(this.getContext(),"Убедитесь в подключении к интернету", Toast.LENGTH_LONG);
                }
                //todo сделать определение для обознпчения совместимости
                break;
            case 2:

                if(!products.isEmpty()){
                    catalogAdapter = new CatalogAdapter(getContext(), products, mChoice, this::onAddButtonClickListener, item);
                }else{
                    Toast.makeText(this.getContext(),"Убедитесь в подключении к интернету", Toast.LENGTH_LONG);
                }
                break;


        }//todo сделать фильтрацию
        catalogRecyclerView.setAdapter(catalogAdapter);
        MainActivity activity = (MainActivity)this.getActivity();
        if(activity.getBottomNavigationView().getSelectedItemId() != R.id.nav_catalog){
            activity.getBottomNavigationView().setSelectedItemId(R.id.nav_catalog);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        NavigationData.init(getContext());
        NavigationData.setBoolean("add",false);
    }

    private void fillproducts(Component item){
        if (item.getComponentType().equals(getActivity().getResources().getString(R.string.videocard)) && products.isEmpty()) {
            products.addAll(MainActivity.getCatalogComponentsList().get(0));
            Log.e("getting item", item.getComponentType());

        } else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.cpu))&& products.isEmpty()) {
            products.addAll(MainActivity.getCatalogComponentsList().get(1));
            Log.e("getting item", item.getComponentType());
        } else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.motherboard))&& products.isEmpty()) {
            products.addAll(MainActivity.getCatalogComponentsList().get(2));
            Log.e("getting item", item.getComponentType());
        }else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.ram))&& products.isEmpty()) {
            products.addAll(MainActivity.getCatalogComponentsList().get(3));
            Log.e("getting item", item.getComponentType());
        }else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.pc_case))&& products.isEmpty()) {
            products.addAll(MainActivity.getCatalogComponentsList().get(4));
            Log.e("getting item", item.getComponentType());
        }else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.power_supply))&& products.isEmpty()) {
            products.addAll(MainActivity.getCatalogComponentsList().get(5));
            Log.e("getting item", item.getComponentType());
        }else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.cpu_cooler))&& products.isEmpty()) {
            products.addAll(MainActivity.getCatalogComponentsList().get(6));
            Log.e("getting item", item.getComponentType());
        }else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.storage_devices))&& products.isEmpty()) {
            products.addAll(MainActivity.getCatalogComponentsList().get(7));
            Log.e("getting item", item.getComponentType());
        }else {
            Log.e("getting item", item.toString());
        }//todo Переместить это в utils и заполнять через запросы к api
    }
    private void onAddButtonClickListener() {
        /*ConfigurerFragment configurerFragment = (ConfigurerFragment) getActivity().getSupportFragmentManager().findFragmentByTag("home_fragment");
        if(configurerFragment != null){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, configurerFragment, "home_fragment")
                    .addToBackStack("home_fragment_backstack")
                    .commit();
        */
        NavController navController = ((MainActivity)requireActivity()).getNavController();
        Log.e(" catalog onAddButtonClickListener","penis");
        navController.navigate(R.id.configurerFragment,null,new NavOptions.Builder()
                .setPopUpTo(R.id.bottom_navigation,false)
                .build());
    }

}