package com.example.pcconfighelpercoursework.catalog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.API;
import com.example.pcconfighelpercoursework.api.APIClient;
import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;
import com.example.pcconfighelpercoursework.api.items.ProductDAO;
import com.example.pcconfighelpercoursework.items.Component;
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterChoiceFragment extends Fragment {

    ImageButton imageButton;
    private String mComponentType;
    private int mChoice;
    Map<String,Map<String,Integer>> filters;
    List<Map<String, String>> attrList;
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
            mComponentType = getArguments().getString("component_type");
            mChoice = getArguments().getInt("choice");
            Log.e("asdasfgghh",getArguments().toString());
            Log.e("mChoice", String.valueOf(mChoice));
            Log.e("mChoice", mComponentType);
            filters = new HashMap<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_choice, container, false);
        attrList = new ArrayList<>();
        imageButton = view.findViewById(R.id.backImageButton);
        imageButton.setOnClickListener(v ->{
            backByBackStack();
        });
        //Log.e("filters test", new FilterAdd().getFilters(l.get(0)).toString());
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new AddAttributes().addItems();
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
        public Map<String, Map<String, Integer>> getFilters(List<Map<String, String>> attributesList) {
            Map<String, Map<String, Integer>> result = new HashMap<>();
            List<ProductAttributeDAO> componentFilters = getComponentFilters();

            if (componentFilters == null || componentFilters.isEmpty()) {
                return result;
            }

            // Получаем все имена фильтров для быстрого доступа
            Set<String> filterNames = componentFilters.stream()
                    .map(ProductAttributeDAO::getName)
                    .collect(Collectors.toSet());

            // Обрабатываем все переданные атрибуты
            attributesList.forEach(attributes -> {
                filterNames.stream()
                        .filter(attributes::containsKey)
                        .forEach(filterName -> {
                            String attributeValue = attributes.get(filterName);
                            result.computeIfAbsent(filterName, k -> new HashMap<>())
                                    .merge(attributeValue, 1, Integer::sum);
                        });
            });

            return result;
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
            if(mComponentType.equals(getResources().getString(R.string.cpu))){
                filtersClass = CpuFilters.class;
            }else if(mComponentType.equals(getResources().getString(R.string.videocard))){
                filtersClass = VideocardFilters.class;
            }else if(mComponentType.equals(getResources().getString(R.string.motherboard))){
                filtersClass = MotherboardFilters.class;
            }else if(mComponentType.equals(getResources().getString(R.string.ram))){
                filtersClass = RamFilters.class;
            }else if(mComponentType.equals(getResources().getString(R.string.storage_devices))){
                filtersClass = StorageDevicesFilters.class;
            }else if(mComponentType.equals(getResources().getString(R.string.power_supply))){
                filtersClass = PsuFilters.class;
            }else if(mComponentType.equals(getResources().getString(R.string.cpu_cooler))){
                filtersClass = CoolersFilters.class;
            }else if(mComponentType.equals(getResources().getString(R.string.pc_case))){
                filtersClass = CaseFilters.class;
            }
            Log.e("equals", mComponentType);
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
                return;
            }
            if(currIndex == 1){
                Log.e("attrList", Arrays.toString(attrList.toArray()));
                filters = new AddFilters().getFilters(attrList);
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