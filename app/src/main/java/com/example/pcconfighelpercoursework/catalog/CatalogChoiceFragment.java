package com.example.pcconfighelpercoursework.catalog;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.items.CPU;
import com.example.pcconfighelpercoursework.items.CPUCooler;
import com.example.pcconfighelpercoursework.items.Cases;
import com.example.pcconfighelpercoursework.items.Component;
import com.example.pcconfighelpercoursework.items.Motherboard;
import com.example.pcconfighelpercoursework.items.PowerSupply;
import com.example.pcconfighelpercoursework.items.RAM;
import com.example.pcconfighelpercoursework.items.StorageDevice;
import com.example.pcconfighelpercoursework.items.Videocard;

import java.util.ArrayList;
import java.util.List;


public class CatalogChoiceFragment extends Fragment implements CatalogChoiceAdapter.OnItemClickListener{

    RecyclerView recyclerView;
    List<String> componentList;
    List<Component> emptyComponents;
    CatalogChoiceAdapter adapter;

    public CatalogChoiceFragment() {
    }

    public static CatalogChoiceFragment newInstance() {
        CatalogChoiceFragment fragment = new CatalogChoiceFragment();
        Bundle args = new Bundle();
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
        componentList = new ArrayList<>();emptyComponents = new ArrayList<>();
        setComponentList(getResources());
        setEmptyComponents();;
        View view = inflater.inflate(R.layout.fragment_catalog_choice, container, false);
        recyclerView = view.findViewById(R.id.catalogChoiceRecyclerView);
        adapter = new CatalogChoiceAdapter(componentList, getContext(), this::onItemClick);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(int position) {
        /*NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        Bundle bundle = new Bundle();
        bundle.putParcelable("component",emptyComponents.get(position));
        bundle.putInt("choice",0);
        navController.navigate(R.id.catalogFragment,bundle);*/

        CatalogFragment catalogFragment = CatalogFragment.newInstance(emptyComponents.get(position), CatalogAdapter.CATALOG);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .replace(R.id.fragmentContainerView, catalogFragment,"catalog_fragment")
                .addToBackStack("catalog_fragment_backstack")
                .commit();
    }
    void setComponentList(Resources resources){
        Log.e("asd",resources.getString(R.string.cpu));
        this.componentList.add(resources.getString(R.string.cpu));
        this.componentList.add(resources.getString(R.string.videocard));
        this.componentList.add(resources.getString(R.string.ram));
        this.componentList.add(resources.getString(R.string.motherboard));
        this.componentList.add(resources.getString(R.string.power_supply));
        this.componentList.add(resources.getString(R.string.pc_case));
        this.componentList.add(resources.getString(R.string.cpu_cooler));
        this.componentList.add(resources.getString(R.string.pc_case));
    }

    public void setEmptyComponents() {
        emptyComponents.add(new CPU(getString(R.string.cpu)));
        emptyComponents.add(new Videocard(getString(R.string.videocard)));
        emptyComponents.add(new Motherboard(getString(R.string.motherboard)));
        emptyComponents.add(new RAM(getString(R.string.ram)));
        emptyComponents.add(new PowerSupply(getString(R.string.power_supply)));
        emptyComponents.add(new CPUCooler(getString(R.string.cpu_cooler)));
        emptyComponents.add(new Cases(getString(R.string.pc_case)));
        emptyComponents.add(new StorageDevice(getString(R.string.storage_devices)));
    }
}