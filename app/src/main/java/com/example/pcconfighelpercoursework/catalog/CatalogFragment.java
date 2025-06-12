package com.example.pcconfighelpercoursework.catalog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.API;
import com.example.pcconfighelpercoursework.api.APIClient;
import com.example.pcconfighelpercoursework.api.items.FilterDAO;
import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;
import com.example.pcconfighelpercoursework.api.items.ProductDAO;
import com.example.pcconfighelpercoursework.items.*;
import com.example.pcconfighelpercoursework.utils.FiltersGetCompat;
import com.example.pcconfighelpercoursework.utils.ItemDecoration;
import com.example.pcconfighelpercoursework.utils.NavigationData;
import com.example.pcconfighelpercoursework.utils.SharedViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogFragment extends Fragment {

    private Component mComponent;
    private int mChoice;
    private CatalogAdapter catalogAdapter;
    private RecyclerView catalogRecyclerView;
    TextView toolbarTitleTextView;
    TextView showTextView;
    Toolbar toolbar;
    ProgressBar progressBar;
    List<Component> products;
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
        progressBar = view.findViewById(R.id.progressBar);
        showTextView = view.findViewById(R.id.showTextView);
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
        /*fillproducts(item);*/
        toolbarTitleTextView.setText(item.getComponentType());

        Log.e("mChoice", String.valueOf(mChoice));
        Log.e("products length", String.valueOf(products.size()));
        //todo сделать фильтрацию
        fillproducts(item);
        MainActivity activity = (MainActivity)this.getActivity();

        if(activity.getBottomNavigationView().getSelectedItemId() != R.id.nav_catalog){
            activity.getBottomNavigationView().setSelectedItemId(R.id.nav_catalog);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        NavigationData.setBoolean("add",false);
    }

    private void fillproducts(Component item){
        if (item.getComponentType().equals(getActivity().getResources().getString(R.string.videocard)) && products.isEmpty()) {
            executeLists(1);
            Log.e("getting item", item.getComponentType());

        } else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.cpu))&& products.isEmpty()) {
            executeLists(2);
            Log.e("getting item", item.getComponentType());

        } else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.motherboard))&& products.isEmpty()) {
            executeLists(3);
            Log.e("getting item", item.getComponentType());
        }else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.ram))&& products.isEmpty()) {
            executeLists(4);
            Log.e("getting item", item.getComponentType());
        }else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.pc_case))&& products.isEmpty()) {
            executeLists(5);
            Log.e("getting item", item.getComponentType());
        }else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.power_supply))&& products.isEmpty()) {
            executeLists(6);
            Log.e("getting item", item.getComponentType());
        }else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.cpu_cooler))&& products.isEmpty()) {
            executeLists(7);
            Log.e("getting item", item.getComponentType());
        }else if (item.getComponentType().equals(getActivity().getResources().getString(R.string.storage_devices))&& products.isEmpty()) {
            executeLists(8);

            Log.e("getting item", item.getComponentType());
        }else {
            Log.e("getting item", item.toString());
        }//todo Переделать, чтобы статичных данных не было
    }

    private void executeLists(int categoryId){
        new FillComponentsCatalogLists().fetchItems(categoryId, mComponent.getComponentType());
    }
    private class FillComponentsCatalogLists{
        int currIndex = 0;
        public void fetchItems(int categoryId,String componentType) {
            List<ProductAttributeDAO> filtersList = new ArrayList<>();
            if(mChoice != 0){
                filtersList.addAll(FiltersGetCompat.getFiltersFromShared(mComponent,getResources()));
            }
            Log.e("filters list", Arrays.toString(filtersList.toArray()));
            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            if (currIndex >= 1) {
                progressBar.setVisibility(View.GONE);
                viewModel.getFiltersList(mComponent.getComponentType()).observe(getViewLifecycleOwner(),list ->{
                    filtersList.addAll(list);
                });
                Log.e("filters list adapter", Arrays.toString(filtersList.toArray()));
                setupAdapter(filtersList,viewModel);
                List<Map<String,String>> list = new ArrayList<>();
                products.stream().forEach(p -> list.add(p.getAttributes()));
                viewModel.setMapList(list);
                catalogRecyclerView.setAdapter(catalogAdapter);
                if(list.isEmpty()){
                    showTextView.setVisibility(View.VISIBLE);
                }
                catalogRecyclerView.setVisibility(View.VISIBLE);
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            catalogRecyclerView.setVisibility(View.GONE);
            API apiService = APIClient.getApi();
            viewModel.getFiltersList(mComponent.getComponentType()).observe(getViewLifecycleOwner(),list ->{
                filtersList.addAll(list);
            });
            Call<List<ProductDAO>> call = apiService.getProductsByCategoryFilter(categoryId,new FilterDAO(filtersList));
            List<ProductDAO> list = new ArrayList<>();
            call.enqueue(new Callback<List<ProductDAO>>() {
                @Override
                public void onResponse(@NonNull Call<List<ProductDAO>> call, @NonNull Response<List<ProductDAO>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.e("API", "Success");
                        list.clear();
                        Log.e("API", Arrays.toString(response.body().toArray()));
                        list.addAll(response.body());
                        Log.e("API", String.valueOf(list.get(0).getPrices().get(0)));
                        //componentRepository.insertComponents(list, categoryId);
                        /*fillListFetchItems(categoryId,list,componentType);*/
                        currIndex++;
                        products = fillListFetchItems(categoryId,list,componentType);
                        Log.e("list",Arrays.toString(products.toArray()));
                        fetchItems(categoryId,componentType);
                    } else {
                        currIndex++;
                        fetchItems(categoryId,componentType);
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<ProductDAO>> call, @NonNull Throwable t) {
                    currIndex++;
                    fetchItems(categoryId,componentType);
                    Toast.makeText(getContext(), "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Ошибка запроса", t);
                }
            });
        }
        private List<Component> fillListFetchItems(int categoryId,List<ProductDAO> list,String componentType){
            Log.e("attr", String.valueOf(list.get(0).getDescription()));
            List<Component> catalogComponentsList = new ArrayList<>();
            switch (categoryId) {
                case 1:
                    catalogComponentsList = list.stream()
                            .filter(p -> !p.getPrices().isEmpty())
                            .map(p ->{
                                return new Videocard(
                                        (int) p.getId(),
                                        p.getName(),
                                        p.getProductImages().isEmpty() ? null :p.getProductImages().get(0).getSource(),
                                        p.getDescription(),
                                        componentType,
                                        p.getPrices().get(0),
                                        p.getAttributes().stream()
                                                .collect(Collectors.toMap(
                                                        ProductAttributeDAO::getName,
                                                        ProductAttributeDAO::getValue
                                                ))
                                );
                            })
                                    .collect(Collectors.toList());

                    break;

                case 2:
                    catalogComponentsList = list.stream()
                            .filter(p -> !p.getPrices().isEmpty())
                            .map(p -> new CPU(
                                    (int) p.getId(),
                                    p.getName(),
                                    p.getProductImages().isEmpty() ? null :p.getProductImages().get(0).getSource(),
                                    p.getDescription(),
                                    componentType,
                                    p.getPrices().get(0),
                                    p.getAttributes().stream()
                                            .collect(Collectors.toMap(
                                                    ProductAttributeDAO::getName,
                                                    ProductAttributeDAO::getValue
                                            ))
                            ))
                            .collect(Collectors.toList());
                    break;

                case 3:
                    catalogComponentsList = list.stream()
                            .filter(p -> !p.getPrices().isEmpty())
                            .map(p -> new Motherboard(
                                    (int) p.getId(),
                                    p.getName(),
                                    p.getProductImages().isEmpty() ? null :p.getProductImages().get(0).getSource(),
                                    p.getDescription(),
                                    componentType,
                                    p.getPrices().get(0),
                                    p.getAttributes().stream()
                                            .collect(Collectors.toMap(
                                                    ProductAttributeDAO::getName,
                                                    ProductAttributeDAO::getValue
                                            ))
                            ))
                            .collect(Collectors.toList());
                    break;

                case 4:
                    catalogComponentsList = list.stream()
                            .filter(p -> !p.getPrices().isEmpty())
                            .map(p -> new RAM(
                                    (int) p.getId(),
                                    p.getName(),
                                    p.getProductImages().isEmpty() ? null :p.getProductImages().get(0).getSource(),
                                    p.getDescription(),
                                    componentType,
                                    p.getPrices().get(0),
                                    p.getAttributes().stream()
                                            .collect(Collectors.toMap(
                                                    ProductAttributeDAO::getName,
                                                    ProductAttributeDAO::getValue
                                            ))
                            ))
                            .collect(Collectors.toList());
                    break;

                case 5:
                    catalogComponentsList = list.stream()
                            .filter(p -> !p.getPrices().isEmpty())
                            .map(p -> new Cases(
                                    (int) p.getId(),
                                    p.getName(),
                                    p.getProductImages().isEmpty() ? null :p.getProductImages().get(0).getSource(),
                                    p.getDescription(),
                                    componentType,
                                    p.getPrices().get(0),
                                    p.getAttributes().stream()
                                            .collect(Collectors.toMap(
                                                    ProductAttributeDAO::getName,
                                                    ProductAttributeDAO::getValue
                                            ))
                            ))
                            .collect(Collectors.toList());
                    break;

                case 6:
                    catalogComponentsList = list.stream()
                            .filter(p -> !p.getPrices().isEmpty())
                            .map(p -> new PowerSupply(
                                    (int) p.getId(),
                                    p.getName(),
                                    p.getProductImages().isEmpty() ? null :p.getProductImages().get(0).getSource(),
                                    p.getDescription(),
                                    componentType,
                                    p.getPrices().get(0),
                                    p.getAttributes().stream()
                                            .collect(Collectors.toMap(
                                                    ProductAttributeDAO::getName,
                                                    ProductAttributeDAO::getValue
                                            ))
                            ))
                            .collect(Collectors.toList());
                    break;

                case 7:
                    catalogComponentsList = list.stream()
                            .filter(p -> !p.getPrices().isEmpty())
                            .map(p -> new CPUCooler(
                                    (int) p.getId(),
                                    p.getName(),
                                    p.getProductImages().isEmpty() ? null :p.getProductImages().get(0).getSource(),
                                    p.getDescription(),
                                    componentType,
                                    p.getPrices().get(0),
                                    p.getAttributes().stream()
                                            .collect(Collectors.toMap(
                                                    ProductAttributeDAO::getName,
                                                    ProductAttributeDAO::getValue
                                            ))
                            ))
                            .collect(Collectors.toList());
                    break;

                case 8:
                    catalogComponentsList = list.stream()
                            .filter(p -> !p.getPrices().isEmpty())
                            .map(p -> new StorageDevice(
                                    (int) p.getId(),
                                    p.getName(),
                                    p.getProductImages().isEmpty() ? null :p.getProductImages().get(0).getSource(),
                                    p.getDescription(),
                                    componentType,
                                    p.getPrices().get(0),
                                    p.getAttributes().stream()
                                            .collect(Collectors.toMap(
                                                    ProductAttributeDAO::getName,
                                                    ProductAttributeDAO::getValue
                                            ))
                            ))
                            .collect(Collectors.toList());
                    break;
            }
            return catalogComponentsList;
        }
        private void setupAdapter(List<ProductAttributeDAO> list,SharedViewModel viewModel){
            switch (mChoice){
                case 0:
                    catalogAdapter = new CatalogAdapter(mComponent,getContext(), products ,mChoice,viewModel,list,((MainActivity)requireActivity()).getNavController(), this::onItemClickListener);
                    break;
                case 1:
                    Log.e("choice", String.valueOf(mChoice));
                    if(!products.isEmpty()){
                        catalogAdapter = new CatalogAdapter(mComponent,getContext(), products, mChoice,viewModel,list,this::onAddButtonClickListener, mComponent,((MainActivity)requireActivity()).getNavController(), this::onItemClickListener);

                    }else{
                        Toast.makeText(getContext(),"Убедитесь в подключении к интернету", Toast.LENGTH_LONG);
                    }
                    break;
                case 2:
                    if(!products.isEmpty()){
                        catalogAdapter = new CatalogAdapter(mComponent,getContext(), products, mChoice,viewModel,list,this::onAddButtonClickListener, mComponent,((MainActivity)requireActivity()).getNavController(), this::onItemClickListener );
                    }else{
                        Toast.makeText(getContext(),"Убедитесь в подключении к интернету", Toast.LENGTH_LONG);
                    }
                    break;


            }
        }
        private void onAddButtonClickListener() {
            NavController navController = ((MainActivity)requireActivity()).getNavController();
            Log.e(" catalog onAddButtonClickListener","penis");
            navController.navigate(R.id.configurerFragment,null,new NavOptions.Builder()
                    .setPopUpTo(R.id.bottom_navigation,false)
                    .build());
        }
        private void onItemClickListener(@NotNull int productId, @NotNull String componentType) {
            NavController navController = ((MainActivity)requireActivity()).getNavController();
            Bundle args = new Bundle();
            args.putInt("product_id", productId);
            args.putString("product_type", componentType);
            Log.e("onItemClick",args.toString());
            navController.navigate(R.id.productFragment,args);
        }
    }
}