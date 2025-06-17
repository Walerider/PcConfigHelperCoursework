package com.example.pcconfighelpercoursework.assemblies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.API;
import com.example.pcconfighelpercoursework.api.APIClient;
import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;
import com.example.pcconfighelpercoursework.api.items.ProductDAO;
import com.example.pcconfighelpercoursework.api.items.UserAssemblyDAO;
import com.example.pcconfighelpercoursework.items.Component;
import com.example.pcconfighelpercoursework.utils.ComponentsSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAssemblyLookFragment extends Fragment {
    TextView assembliesTextView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    int assemblyId;
    public UserAssemblyLookFragment() {
        // Required empty public constructor
    }


    public static UserAssemblyLookFragment newInstance(String param1, String param2) {
        UserAssemblyLookFragment fragment = new UserAssemblyLookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            assemblyId = getArguments().getInt("assembly_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_assembly_look, container, false);
        assembliesTextView = view.findViewById(R.id.assembliesTextView);
        recyclerView = view.findViewById(R.id.assemblyRecyclerView);
        progressBar = view.findViewById(R.id.progressBar2);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getAssembly();
    }

    void getAssembly(){
        new GetAssembly().getAssembly();
    }
    private class GetAssembly {
        private int currIndex = 0;
        private List<Integer> productIds = new ArrayList<>();
        private List<Component> components = new ArrayList<>();
        private API apiService = APIClient.getApi();

        public void getAssembly() {
            if (currIndex == 1) {
                // После получения ID продуктов загружаем их по одному
                if (!productIds.isEmpty()) {
                    getAllProducts(productIds);
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            Call<UserAssemblyDAO> call = apiService.getAssemblyById(assemblyId);
            call.enqueue(new Callback<UserAssemblyDAO>() {
                @Override
                public void onResponse(@NonNull Call<UserAssemblyDAO> call, @NonNull Response<UserAssemblyDAO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        productIds.addAll(
                                response.body().getUserAssemblyComponents()
                                        .stream()
                                        .map(component -> Integer.parseInt(String.valueOf(component.getId())))
                                        .collect(Collectors.toList())
                        );
                        assembliesTextView.setText(response.body().getName());
                        currIndex++;
                        getAssembly();
                    } else {
                        Toast.makeText(getContext(), "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserAssemblyDAO> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Ошибка запроса", t);
                }
            });
        }

        public void getAllProducts(List<Integer> productIds) {
            if (components.size() == productIds.size()) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(new UserAssemblyLookAdapter(ComponentsSorter.sortComponents(components),getContext()));
                recyclerView.setVisibility(View.VISIBLE);
                Log.e("components", Arrays.toString(components.toArray()));
                return;
            }

            // Берём следующий ID продукта
            int productId = productIds.get(components.size());
            Call<ProductDAO> call = apiService.getProductById(productId);
            call.enqueue(new Callback<ProductDAO>() {
                @Override
                public void onResponse(@NonNull Call<ProductDAO> call, @NonNull Response<ProductDAO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ProductDAO product = response.body();
                        Component component = new Component();
                        component.setName(product.getName());
                        component.setId((int) product.getId());
                        component.setDescription(product.getDescription());
                        component.setCategory(product.getCategoryName());
                        component.setSelected(true);
                        if (!product.getPrices().isEmpty()) {
                            component.setPrice(product.getPrices().get(0));
                        }
                        if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
                            component.setImage(product.getProductImages().get(0).getSource());
                        }
                        components.add(component);

                        // Рекурсивно загружаем следующий продукт
                        getAllProducts(productIds);
                    } else {
                        Toast.makeText(getContext(), "Ошибка загрузки товара: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductDAO> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Ошибка запроса товара", t);
                }
            });
        }
    }
}