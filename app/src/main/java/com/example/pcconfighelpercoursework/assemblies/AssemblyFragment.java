package com.example.pcconfighelpercoursework.assemblies;

import android.graphics.Bitmap;
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
import com.example.pcconfighelpercoursework.api.items.UserAssemblyDAO;
import com.example.pcconfighelpercoursework.items.Component;
import com.example.pcconfighelpercoursework.product.ProductAdapter;
import com.example.pcconfighelpercoursework.product.ProductFragment;
import com.example.pcconfighelpercoursework.product.ProductPriceAdapter;
import com.example.pcconfighelpercoursework.utils.UserData;
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

public class AssemblyFragment extends Fragment {

    private int userId;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView assembliesTextView;
    private ImageButton backImageButton;
    TextView showTextView;
    Component cases = new Component();
    List<UserAssemblyDAO> list;
    public AssemblyFragment() {

    }

    public static AssemblyFragment newInstance() {
        AssemblyFragment fragment = new AssemblyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = UserData.getInteger("id");
        list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assembly, container, false);
        recyclerView = view.findViewById(R.id.assemblyRecyclerView);
        assembliesTextView = view.findViewById(R.id.assembliesTextView);
        backImageButton = view.findViewById(R.id.backImageButton);
        progressBar = view.findViewById(R.id.progressBar2);
        showTextView = view.findViewById(R.id.showTextView);
        cases.setComponentType(getContext().getResources().getString(R.string.pc_case));
        backImageButton.setOnClickListener(v ->{
            backByBackStack();
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setAssemblies();
    }

    private void backByBackStack(){
        NavController navController = ((MainActivity)requireActivity()).getNavController();
        navController.navigateUp();
    }
    private void setAssemblies(){
        new GetAssemblies().getAssemblies();
    }
    private class GetAssemblies{
        int currIndex = 0;

        public void getAssemblies() {
            if (currIndex >= 1) {
                progressBar.setVisibility(View.GONE);
                Log.e("asasaa","asdasdasda");
                if(list.isEmpty()){
                    showTextView.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setAdapter(new AssemblyAdapter(getContext(),list));
                    recyclerView.setVisibility(View.VISIBLE);
                }
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            API apiService = APIClient.getApi();
            Call<List<UserAssemblyDAO>> call = apiService.getAllAssembliesByUserId(userId);
            call.enqueue(new Callback<List<UserAssemblyDAO>>() {
                @Override
                public void onResponse(@NonNull Call<List<UserAssemblyDAO>> call, @NonNull Response<List<UserAssemblyDAO>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        currIndex++;
                        list.addAll(response.body());
                        getAssemblies();
                    } else {
                        currIndex++;
                        getAssemblies();
                        //Toast.makeText(getContext(), "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<UserAssemblyDAO>> call, @NonNull Throwable t) {
                    //Toast.makeText(getContext(), "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    currIndex++;
                    getAssemblies();
                }
            });
        }
    }
    private class GetProduct{
        int currIndex = 0;
        public Component getProduct(int productId) {
            Component c = new Component();
            if(currIndex == 1){
                currIndex++;
                return c;
            }
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
                        //componentRepository.insertComponents(list, categoryId);
                        /*fillListFetchItems(categoryId,list,componentType);*/
                        currIndex++;
                        getProduct(productId);
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
            return c;
        }
        public Component getCase(int productId) {
            Component componentCase = new Component();
            if(currIndex == 1){
                currIndex++;
                return componentCase;
            }
            API apiService = APIClient.getApi();
            Call<ProductDAO> call = apiService.getProductById(productId);
            call.enqueue(new Callback<ProductDAO>() {
                @Override
                public void onResponse(@NonNull Call<ProductDAO> call, @NonNull Response<ProductDAO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.e("API", "Success");
                        Log.e("API", response.body().toString());
                        componentCase.setName(response.body().getName());
                        componentCase.setId((int) response.body().getId());
                        componentCase.setImage(response.body().getProductImages() != null ? response.body().getProductImages().get(0).getSource() : null);
                        //componentRepository.insertComponents(list, categoryId);
                        /*fillListFetchItems(categoryId,list,componentType);*/
                        currIndex++;
                        getCase(productId);
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
            return componentCase;
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
            Class<?> filtersClass = filterClasses.get(cases.getComponentType());
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