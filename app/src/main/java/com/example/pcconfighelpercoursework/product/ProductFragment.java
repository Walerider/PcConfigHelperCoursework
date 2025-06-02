package com.example.pcconfighelpercoursework.product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.API;
import com.example.pcconfighelpercoursework.api.APIClient;
import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;
import com.example.pcconfighelpercoursework.api.items.ProductDAO;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {
    private int id;
    private Component component;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ImageButton backImageButton;
    private TextView nameTextView;
    public ProductFragment() {

    }

    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("product_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);//todo сделать карточку для продуктов
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        backImageButton = view.findViewById(R.id.backImageButton);
        nameTextView = view.findViewById(R.id.productNameTextView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(id != 0){
            getProduct();
        }
        backImageButton.setOnClickListener(v -> {
            backByBackStack();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void backByBackStack(){
        NavController navController = ((MainActivity)requireActivity()).getNavController();
        navController.navigateUp();
    }
    private void getProduct(){
        new GetProduct().getItem(id);
    }
    private class GetProduct{
        int currIndex = 0;
        public void getItem(int productId) {
            if (currIndex >= 1) {
                progressBar.setVisibility(View.GONE);
                nameTextView.setText(component.getName());
                recyclerView.setVisibility(View.VISIBLE);
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            API apiService = APIClient.getApi();
            Call<ProductDAO> call = apiService.getProductById(productId);
            Component c = new Component();
            call.enqueue(new Callback<ProductDAO>() {
                @Override
                public void onResponse(@NonNull Call<ProductDAO> call, @NonNull Response<ProductDAO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.e("API", "Success");
                        Log.e("API", response.body().toString());
                        c.setName(response.body().getName());
                        c.setId((int) response.body().getId());
                        c.setAttributes(response.body().getAttributes().stream().collect(Collectors.toMap(ProductAttributeDAO::getName,ProductAttributeDAO::getValue)));
                        c.setPrice(response.body().getPrices().get(0));
                        Log.e("API", String.valueOf(c.getPrice()));
                        //componentRepository.insertComponents(list, categoryId);
                        /*fillListFetchItems(categoryId,list,componentType);*/
                        currIndex++;
                        component = c;
                        getItem(productId);
                    } else {
                        Toast.makeText(getContext(), "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ProductDAO> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Ошибка запроса", t);
                }
            });
        }
        /*private List<Component> fillListFetchItems(int categoryId, List<ProductDAO> list, String componentType){
            Log.e("attr", String.valueOf(list.get(0).getDescription()));
            List<Component> catalogComponentsList = new ArrayList<>();
            switch (categoryId){
                case 1:
                    catalogComponentsList.addAll(list.stream().map(p ->
                            new Videocard((int) p.getId(), p.getName(), p.getDescription(), componentType, !p.getPrices().isEmpty() ? p.getPrices().get(0) : 0,p.getAttributes())
                    ).collect(Collectors.toList()));
                    return catalogComponentsList;

                case 2:
                    catalogComponentsList.addAll(list.stream().map(p ->
                            new CPU((int) p.getId(), p.getName(), p.getDescription(), componentType, !p.getPrices().isEmpty() ? p.getPrices().get(0) : 0,p.getAttributes())
                    ).collect(Collectors.toList()));
                    return catalogComponentsList;

                case 3:
                    catalogComponentsList.addAll(list.stream().map(p ->
                            new Motherboard((int) p.getId(), p.getName(), p.getDescription(), componentType, !p.getPrices().isEmpty() ? p.getPrices().get(0) : 0,p.getAttributes())
                    ).collect(Collectors.toList()));
                    return catalogComponentsList;

                case 4:
                    catalogComponentsList.addAll(list.stream().map(p ->
                            new RAM((int) p.getId(), p.getName(), p.getDescription(), componentType, !p.getPrices().isEmpty() ? p.getPrices().get(0) : 0,p.getAttributes())
                    ).collect(Collectors.toList()));
                    return catalogComponentsList;

                case 5:
                    catalogComponentsList.addAll(list.stream().map(p ->
                            new Cases((int) p.getId(), p.getName(), p.getDescription(), componentType, !p.getPrices().isEmpty() ? p.getPrices().get(0) : 0,p.getAttributes())
                    ).collect(Collectors.toList()));
                    return catalogComponentsList;

                case 6:
                    catalogComponentsList.addAll(list.stream().map(p ->
                            new PowerSupply((int) p.getId(), p.getName(), p.getDescription(), componentType, !p.getPrices().isEmpty() ? p.getPrices().get(0) : 0,p.getAttributes())
                    ).collect(Collectors.toList()));
                    return catalogComponentsList;

                case 7:
                    catalogComponentsList.addAll(list.stream().map(p ->
                            new CPUCooler((int) p.getId(), p.getName(), p.getDescription(), componentType, !p.getPrices().isEmpty() ? p.getPrices().get(0) : 0,p.getAttributes())
                    ).collect(Collectors.toList()));
                    return catalogComponentsList;

                case 8:
                    catalogComponentsList.addAll(list.stream().map(p ->
                            new StorageDevice((int) p.getId(), p.getName(), p.getDescription(), componentType, !p.getPrices().isEmpty() ? p.getPrices().get(0) : 0,p.getAttributes())
                    ).collect(Collectors.toList()));
                    return catalogComponentsList;
            }
            return catalogComponentsList;
        }*/
    }
}