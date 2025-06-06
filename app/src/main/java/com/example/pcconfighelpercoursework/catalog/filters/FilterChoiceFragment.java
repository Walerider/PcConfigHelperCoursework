package com.example.pcconfighelpercoursework.catalog.filters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;
import com.example.pcconfighelpercoursework.items.Component;
import com.example.pcconfighelpercoursework.utils.FiltersGetCompat;
import com.example.pcconfighelpercoursework.utils.SharedViewModel;
import com.example.pcconfighelpercoursework.utils.filters.CaseFilters;
import com.example.pcconfighelpercoursework.utils.filters.CoolersFilters;
import com.example.pcconfighelpercoursework.utils.filters.CpuFilters;
import com.example.pcconfighelpercoursework.utils.filters.MotherboardFilters;
import com.example.pcconfighelpercoursework.utils.filters.PsuFilters;
import com.example.pcconfighelpercoursework.utils.filters.RamFilters;
import com.example.pcconfighelpercoursework.utils.filters.StorageDevicesFilters;
import com.example.pcconfighelpercoursework.utils.filters.VideocardFilters;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterChoiceFragment extends Fragment {

    ImageButton imageButton;
    Button useButton;
    private Component mComponent;
    private int mChoice;
    static public List<ProductAttributeDAO> filtersSend = new ArrayList<>();
    Map<String,Map<String,Integer>> filters;
    List<Map<String, String>> attrList;
    List<ExpandableItem> filtersList;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    public FilterChoiceFragment() {
    }
    public static FilterChoiceFragment newInstance(String param1, String param2) {
        FilterChoiceFragment fragment = new FilterChoiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mComponent = getArguments().getParcelable("component");
            mChoice = getArguments().getInt("choice");
            Log.e("asdasfgghh",getArguments().toString());
            Log.e("mChoice", String.valueOf(mChoice));
            Log.e("mChoice", mComponent.toString());
            filters = new HashMap<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_choice, container, false);
        attrList = new ArrayList<>();
        filtersList = new ArrayList<>();
        imageButton = view.findViewById(R.id.backImageButton);
        imageButton.setOnClickListener(v ->{
            backByBackStack();
        });
        //Log.e("filters test", new FilterAdd().getFilters(l.get(0)).toString());
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        useButton = view.findViewById(R.id.useButton);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new AddAttributes().addItems();
        useButton.setOnClickListener(v -> {
            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.clearFilters(mComponent.getComponentType());
            viewModel.addFilters(mComponent.getComponentType(),filtersSend);
            Bundle args = new Bundle();
            args.putParcelable("component",mComponent);
            args.putInt("type",mChoice);
            NavController navController = ((MainActivity)requireActivity()).getNavController();
            navController.navigate(R.id.catalogFragment,args,new NavOptions.Builder()
                    .build());
        });
    }

    private void backByBackStack(){
        NavController navController = ((MainActivity)requireActivity()).getNavController();
        navController.navigateUp();
    }
    private class AddFilters{
        public Map<String,Map<String,Integer>> subFilters;
        {
            subFilters = new HashMap<>();
        }
        public List<ExpandableItem> getFilters(List<Map<String, String>> attributesList) {
            Map<String, Map<String, Integer>> result = new HashMap<>();
            List<ProductAttributeDAO> componentFilters = getComponentFilters();
            List<ExpandableItem> adapterList = new ArrayList<>();
            if (componentFilters == null || componentFilters.isEmpty()) {
                return adapterList;
            }
            Set<String> filterNames = componentFilters.stream()
                    .map(ProductAttributeDAO::getName)
                    .collect(Collectors.toSet());

            attributesList.forEach(attributes -> {
                filterNames.stream()
                        .filter(attributes::containsKey)
                        .forEach(filterName -> {
                            String attributeValue = attributes.get(filterName);
                            result.computeIfAbsent(filterName, k -> new HashMap<>())
                                    .merge(attributeValue, 1, Integer::sum);
                        });
            });
            filters = result;
            for (String key: result.keySet()) {
                adapterList.add(new ExpandableItem(key,result.get(key),false));
            }
            return adapterList;
        }
        private List<ProductAttributeDAO> getComponentFilters() {
            try {
                Class<?> filtersClass = getaClass();
                Log.e("filter class",filtersClass.getName());
                if(filtersClass == null){
                    return null;
                }
                Log.e("filter class","assdasdasdas");
                return Arrays.stream(filtersClass.getFields())
                        .filter(f -> Modifier.isStatic(f.getModifiers()))
                        .filter(f -> ProductAttributeDAO.class.isAssignableFrom(f.getType()))
                        .map(f -> {
                            try {
                                return (ProductAttributeDAO) f.get(null);
                            } catch (IllegalAccessException e) {
                                Log.e("exception",e.getMessage());
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toList());
            } catch (Exception e) {
                Log.e("filter class", e.getMessage());
                return Collections.emptyList();
            }
        }

        private @Nullable Class<?> getaClass() {
            Class<?> filtersClass = null;
            if(mComponent.getComponentType().equals(getResources().getString(R.string.cpu))){
                filtersClass = CpuFilters.class;
            }else if(mComponent.getComponentType().equals(getResources().getString(R.string.videocard))){
                filtersClass = VideocardFilters.class;
            }else if(mComponent.getComponentType().equals(getResources().getString(R.string.motherboard))){
                filtersClass = MotherboardFilters.class;
            }else if(mComponent.getComponentType().equals(getResources().getString(R.string.ram))){
                filtersClass = RamFilters.class;
            }else if(mComponent.getComponentType().equals(getResources().getString(R.string.storage_devices))){
                filtersClass = StorageDevicesFilters.class;
            }else if(mComponent.getComponentType().equals(getResources().getString(R.string.power_supply))){
                filtersClass = PsuFilters.class;
            }else if(mComponent.getComponentType().equals(getResources().getString(R.string.cpu_cooler))){
                filtersClass = CoolersFilters.class;
            }else if(mComponent.getComponentType().equals(getResources().getString(R.string.pc_case))){
                filtersClass = CaseFilters.class;
            }
            Log.e("equals", mComponent.getComponentType());
            return filtersClass;
        }
    }
    private class AddAttributes{
        int currIndex = 0;
        public void addItems() {
            if (currIndex >= 2) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Log.e("filters List", filters.toString());
                recyclerView.setAdapter(new FilterAdapter(filtersList));
                return;
            }
            if(currIndex == 1){
                Log.e("attrList", Arrays.toString(attrList.toArray()));
                filtersList = new AddFilters().getFilters(attrList);
                currIndex++;
                addItems();
            }
            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            viewModel.getMapList().observe(getViewLifecycleOwner(), maps -> {
                attrList.clear();
                attrList.addAll(maps);
                currIndex++;
                addItems();
            });
        }
    }
}