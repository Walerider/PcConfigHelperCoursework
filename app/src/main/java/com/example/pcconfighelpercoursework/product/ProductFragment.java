package com.example.pcconfighelpercoursework.product;

import static com.example.pcconfighelpercoursework.R.*;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.API;
import com.example.pcconfighelpercoursework.api.APIClient;
import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;
import com.example.pcconfighelpercoursework.api.items.ProductDAO;
import com.example.pcconfighelpercoursework.catalog.filters.ExpandableItem;
import com.example.pcconfighelpercoursework.items.CPU;
import com.example.pcconfighelpercoursework.items.CPUCooler;
import com.example.pcconfighelpercoursework.items.Cases;
import com.example.pcconfighelpercoursework.items.Component;
import com.example.pcconfighelpercoursework.items.Motherboard;
import com.example.pcconfighelpercoursework.items.PowerSupply;
import com.example.pcconfighelpercoursework.items.RAM;
import com.example.pcconfighelpercoursework.items.StorageDevice;
import com.example.pcconfighelpercoursework.items.Videocard;
import com.example.pcconfighelpercoursework.utils.filters.CaseFilters;
import com.example.pcconfighelpercoursework.utils.filters.CoolersFilters;
import com.example.pcconfighelpercoursework.utils.filters.CpuFilters;
import com.example.pcconfighelpercoursework.utils.filters.MotherboardFilters;
import com.example.pcconfighelpercoursework.utils.filters.PsuFilters;
import com.example.pcconfighelpercoursework.utils.filters.RamFilters;
import com.example.pcconfighelpercoursework.utils.filters.StorageDevicesFilters;
import com.example.pcconfighelpercoursework.utils.filters.VideocardFilters;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private int id;
    String componentType;
    private Component component;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView priceRecyclerView;
    private ImageButton backImageButton;
    private TextView nameTextView;
    private TextView productNameTextView;
    ImageView imageView;
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
            componentType = getArguments().getString("product_type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);//todo сделать карточку для продуктов
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        priceRecyclerView = view.findViewById(R.id.priceRecyclerView);
        backImageButton = view.findViewById(R.id.backImageButton);
        nameTextView = view.findViewById(R.id.productNameTextView);
        productNameTextView = view.findViewById(R.id.productNameTextView2);
        imageView = view.findViewById(R.id.imageView);
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
        Component c = new Component();
        public void getItem(int productId) {
            if(currIndex == 1){
                c.setAttributes(new AttributesProcessor().processAttributes(c.getAttributes()));
                Log.e("API", String.valueOf(c.getAttributes()));
                currIndex++;
                getItem(productId);
            }
            if (currIndex >= 2) {
                progressBar.setVisibility(View.GONE);
                nameTextView.setText(c.getName());
                productNameTextView.setText(c.getName());
                if(component.getImage() != null){
                    Glide.with(ProductFragment.this)
                            .asBitmap()
                            .load(component.getImage())
                            .apply(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap bitmap, Transition<? super Bitmap> transition) {

                                    try (FileOutputStream out = new FileOutputStream("output.png")) {
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 1000, out);
                                    } catch (IOException e) {
                                    }
                                    Log.e("Transparency", "Transparency");
                                    if (bitmap.hasAlpha()) {
                                        Log.e("Transparency", "Transparency here");
                                    }
                                    imageView.setImageBitmap(bitmap);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });
                }else{
                    imageView.setImageResource(R.drawable.ic_launcher_foreground);
                }
                ProductAdapter adapter = new ProductAdapter(c);
                recyclerView.setAdapter(adapter);
                priceRecyclerView.setAdapter(new ProductPriceAdapter(component));
                recyclerView.setVisibility(View.VISIBLE);
                priceRecyclerView.setVisibility(View.VISIBLE);
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            priceRecyclerView.setVisibility(View.GONE);
            API apiService = APIClient.getApi();
            Call<ProductDAO> call = apiService.getProductById(productId);
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
                        c.setImage(response.body().getProductImages() != null ? response.body().getProductImages().get(0).getSource() : null);
                        Log.e("API", String.valueOf(c.getAttributes()));
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

    }
    private class AttributesProcessor {
        private final Map<String, Class<?>> filterClasses = new HashMap<String, Class<?>>() {{
            put(getString(R.string.cpu), CpuFilters.class);
            put(getString(R.string.videocard), VideocardFilters.class);
            put(getString(R.string.motherboard), MotherboardFilters.class);
            put(getString(R.string.ram), RamFilters.class);
            put(getString(R.string.storage_devices), StorageDevicesFilters.class);
            put(getString(R.string.power_supply), PsuFilters.class);
            put(getString(R.string.cpu_cooler), CoolersFilters.class);
            put(getString(R.string.pc_case), CaseFilters.class);
        }};

        public Map<String, String> processAttributes(Map<String, String> inputAttributes) {
            Class<?> filtersClass = filterClasses.get(componentType);
            Log.e("filtersClass",component.getComponentType() + " ");
            if (filtersClass == null) {
                return Collections.emptyMap();
            }
            Set<String> availableFilterNames = getAvailableAttributeNames(filtersClass);

            return inputAttributes.entrySet().stream()
                    .filter(entry -> availableFilterNames.contains(entry.getKey()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));
        }

        private Set<String> getAvailableAttributeNames(Class<?> filtersClass) {
            try {
                return Arrays.stream(filtersClass.getFields())
                        .filter(f -> Modifier.isStatic(f.getModifiers()))
                        .filter(f -> ProductAttributeDAO.class.isAssignableFrom(f.getType()))
                        .map(f -> {
                            try {
                                return ((ProductAttributeDAO) f.get(null)).getName();
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException("Error accessing filter field", e);
                            }
                        })
                        .collect(Collectors.toSet());
            } catch (Exception e) {
                Log.e("FilterProcessor", "Error getting filter names", e);
                return Collections.emptySet();
            }
        }
    }
}