package com.example.pcconfighelpercoursework.configurator;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.API;
import com.example.pcconfighelpercoursework.api.APIClient;
import com.example.pcconfighelpercoursework.api.items.AssemblyPOJO;
import com.example.pcconfighelpercoursework.api.items.UserPOJO;
import com.example.pcconfighelpercoursework.catalog.CatalogAdapter;
import com.example.pcconfighelpercoursework.items.CPU;
import com.example.pcconfighelpercoursework.items.CPUCooler;
import com.example.pcconfighelpercoursework.items.Cases;
import com.example.pcconfighelpercoursework.items.Component;
import com.example.pcconfighelpercoursework.items.Motherboard;
import com.example.pcconfighelpercoursework.items.PowerSupply;
import com.example.pcconfighelpercoursework.items.RAM;
import com.example.pcconfighelpercoursework.items.StorageDevice;
import com.example.pcconfighelpercoursework.items.Videocard;
import com.example.pcconfighelpercoursework.utils.AssemblyData;
import com.example.pcconfighelpercoursework.utils.AssemblyDeleteCompat;
import com.example.pcconfighelpercoursework.utils.NavigationData;
import com.example.pcconfighelpercoursework.utils.UserData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigurerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int SL_TYPE_NOT_CHOICE = 0;
    public static final int SL_TYPE_CHOICE = 1;
    public static final int SL_TYPE_STORAGE = 2;
    public static final int SAVE_CLEAR = 3;
    private List<Component> components;
    private Map<String, Component> emptyComponents;
    private final LayoutInflater inflater;
    private OnAddButtonClickListener onAddButtonClickListener;
    public final NavController navController;

    public ConfigurerAdapter(List<Component> components, Context context,NavController navController) {
        this.components = components;
        //Collections.reverse(this.components);
        Log.e("asdasd", Arrays.toString(components.toArray()));
        this.inflater = LayoutInflater.from(context);
        emptyComponents = new HashMap<>();
        emptyComponents.put(MainActivity.resources.getString(R.string.cpu),new CPU(MainActivity.resources.getString(R.string.cpu)));
        emptyComponents.put(MainActivity.resources.getString(R.string.videocard),new Videocard(MainActivity.resources.getString(R.string.videocard)));
        emptyComponents.put(MainActivity.resources.getString(R.string.motherboard),new Motherboard(MainActivity.resources.getString(R.string.motherboard)));
        emptyComponents.put(MainActivity.resources.getString(R.string.ram),new RAM(MainActivity.resources.getString(R.string.ram)));
        emptyComponents.put(MainActivity.resources.getString(R.string.power_supply),new PowerSupply(MainActivity.resources.getString(R.string.power_supply)));
        emptyComponents.put(MainActivity.resources.getString(R.string.storage_devices),new StorageDevice(MainActivity.resources.getString(R.string.storage_devices)));
        emptyComponents.put(MainActivity.resources.getString(R.string.cpu_cooler),new CPUCooler(MainActivity.resources.getString(R.string.cpu_cooler)));
        emptyComponents.put(MainActivity.resources.getString(R.string.pc_case),new Cases(MainActivity.resources.getString(R.string.pc_case)));
        this.navController = navController;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SAVE_CLEAR){
            View view = inflater.inflate(R.layout.buttons_assembly_item,parent,false);
            ButtonsViewHolder viewHolder = new ButtonsViewHolder(view);
            return viewHolder;
        }else if (viewType == SL_TYPE_NOT_CHOICE) {

            View view = inflater.inflate(R.layout.not_selected_pc_config_item, parent, false);
            NotSelectedViewHolder viewHolder = new NotSelectedViewHolder(view);

            return viewHolder;
        } else {
            View view = inflater.inflate(R.layout.selected_pc_config_item, parent, false);
            SelectedViewHolder viewHolder = new SelectedViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case SL_TYPE_CHOICE:
                SelectedViewHolder selectedViewHolder = (SelectedViewHolder) holder;
                selectedViewHolder.productNameTextView.setText(components.get(position).getName());
                selectedViewHolder.productDescriptionTextView.setText(components.get(position).getDescription());
                selectedViewHolder.priceCatalogTextView.setText(String.valueOf(components.get(position).getPrice()) + "р");
                selectedViewHolder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
                selectedViewHolder.clearButton.setOnClickListener(v ->{
                    components.set(position,emptyComponents.get(MainActivity.getComponents().get(position).getComponentType()));
                    //Log.e("components in delete button adapter",Arrays.toString(components.toArray()));
                    if(!MainActivity.checkComponents(components)){
                        MainActivity.fillComponents(components);
                    }
                    AssemblyDeleteCompat.deleteCompat(MainActivity.getComponents().get(position),inflater.getContext().getResources());
                    ConfigurerFragment.setPrice();
                    notifyDataSetChanged();
                });
                selectedViewHolder.changeButton.setOnClickListener(v ->{
                    onAddButtonClickListener.onAddClick(emptyComponents.get(components.get(position).getComponentType()), CatalogAdapter.CHANGE_CONFIG);
                });
                break;
            case SL_TYPE_NOT_CHOICE:
                NotSelectedViewHolder notSelectedViewHolder = (NotSelectedViewHolder) holder;
                notSelectedViewHolder.componentType.setText(components.get(position).getComponentType());
                notSelectedViewHolder.addButton.setOnClickListener(v -> {
                    onAddButtonClickListener.onAddClick(components.get(position), CatalogAdapter.ADD_CONFIG);
                });
                break;
            case SL_TYPE_STORAGE:
                SelectedViewHolderStorage selectedViewHolderStorage = (SelectedViewHolderStorage) holder;
                selectedViewHolderStorage.productNameTextView.setText(components.get(position).getName());
                selectedViewHolderStorage.productDescriptionTextView.setText(components.get(position).getDescription());
                selectedViewHolderStorage.priceCatalogTextView.setText(String.valueOf(components.get(position).getPrice()) + "р");
                selectedViewHolderStorage.clearButton.setOnClickListener(v ->{
                    components.set(position,emptyComponents.get(MainActivity.getComponents().get(position).getComponentType()));
                    //Log.e("components in delete button adapter",Arrays.toString(components.toArray()));
                    if(!MainActivity.checkComponents(components)){
                        MainActivity.fillComponents(components);
                    }
                    MainActivity.setComponents(components);
                    notifyDataSetChanged();
                });
                selectedViewHolderStorage.changeButton.setOnClickListener(v ->{
                    onAddButtonClickListener.onAddClick(emptyComponents.get(components.get(position).getComponentType()), CatalogAdapter.CHANGE_CONFIG);
                });
                break;
            case SAVE_CLEAR:
                ButtonsViewHolder buttonsViewHolder = (ButtonsViewHolder) holder;
                buttonsViewHolder.clearButton.setOnClickListener(v ->{
                    List<Component> l = MainActivity.getComponents();
                    l.clear();
                    MainActivity.setComponents(l);
                    MainActivity.fillComponents(l);
                    AssemblyData.clear(inflater.getContext());
                    notifyDataSetChanged();
                });
                buttonsViewHolder.saveButton.setOnClickListener(v -> {
                    if(UserData.getInteger("id") == -1){
                        Toast.makeText(inflater.getContext(),"Для сохранения сборки войдите в аккаунт",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(checkAssemblyComponents()){
                        API apiService = APIClient.getApi();
                        AssemblyPOJO assembly = new AssemblyPOJO();
                        assembly.setName(String.valueOf(ConfigurerFragment.getAssemblyNameTextView().getText()));
                        assembly.setUserId(Long.valueOf(UserData.getInteger("id")));
                        List<Long> ids = new ArrayList<>();
                        MainActivity.getComponents().stream().forEach(c -> {
                            ids.add((long) c.getId());
                        });
                        assembly.setProductIds(ids);
                        assembly.setPrice(ConfigurerFragment.a.get());

                        Log.e("assembly save", String.valueOf(assembly.getProductIds()));
                        Call<AssemblyPOJO> call = apiService.createAssembly(assembly);
                        call.enqueue(new Callback<AssemblyPOJO>() {
                            @Override
                            public void onResponse(Call<AssemblyPOJO> call, Response<AssemblyPOJO> response) {
                                if (response.code() == 200) {
                                    String result = response.toString();
                                    Log.e("id",result);
                                    NavigationData.setBoolean("isCreating",false);
                                    Toast.makeText(inflater.getContext(), "Сборка успешно создана!", Toast.LENGTH_SHORT).show();
                                    AssemblyData.setString("name",null);

                                    NavOptions navOptions = new NavOptions.Builder()
                                            .setPopUpTo(R.id.assemblyChoiceFragment, true) // Очищаем стек до registerFragment
                                            .build();

                                    navController.navigate(
                                            R.id.configurerFragment, // ID вашего фрагмента авторизации
                                            null,
                                            navOptions
                                    );

                                } else {
                                    Log.e("Retrofit", "Ошибка: " + response.code());
                                }
                            }
                            @Override
                            public void onFailure(Call<AssemblyPOJO> call, Throwable t) {
                                if(!call.isCanceled()){
                                    Toast.makeText(inflater.getContext(), "Сборка создана успешно!", Toast.LENGTH_SHORT).show();
                                    NavigationData.setBoolean("isCreating",false);
                                    AssemblyData.setString("name",null);
                                    List<Component> l = MainActivity.getComponents();
                                    l.clear();
                                    MainActivity.setComponents(l);
                                    MainActivity.fillComponents(l);
                                    notifyDataSetChanged();
                                    NavOptions navOptions = new NavOptions.Builder()
                                            .setPopUpTo(R.id.configurerFragment, true) // Очищаем стек до registerFragment
                                            .build();
                                    navController.navigate(
                                            R.id.assemblyChoiceFragment, // ID вашего фрагмента авторизации
                                            null,
                                            navOptions
                                    );
                                }
                                Log.e("Retrofit", "Network bug: " + t.getMessage());
                                Log.e("Retrofit", "Network bug: " + call.isCanceled());
                            }
                        });
                    }
                    else{
                        Toast.makeText(inflater.getContext(),"Выбраны не все комплектующие",Toast.LENGTH_LONG).show();
                    }
                });
        }


    }
    private boolean checkAssemblyComponents(){
        AtomicInteger allFill = new AtomicInteger(1);
        MainActivity.getComponents().stream().forEach(c -> {
            if(c.getId() <= 0)allFill.set(0);
        });
        if(allFill.get() == 1){
            return true;
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return components.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if((position == getItemCount() - 1)){
            return SAVE_CLEAR;
        }
        if (components.get(position).isSelected()) {
            if(components.get(position).getComponentType().equals(R.string.storage_devices)){
                return SL_TYPE_STORAGE;
            }
            return SL_TYPE_CHOICE;
        } else {
            return SL_TYPE_NOT_CHOICE;
        }
    }

    public void setOnAddButtonClickListener(OnAddButtonClickListener AddButtonListener) {
        this.onAddButtonClickListener = AddButtonListener;
    }

    public OnAddButtonClickListener getOnAddButtonClickListener() {
        return onAddButtonClickListener;
    }

    public static class NotSelectedViewHolder extends RecyclerView.ViewHolder {
        final TextView componentType;
        final ImageButton addButton;

        NotSelectedViewHolder(View view) {
            super(view);
            componentType = view.findViewById(R.id.productNameTextView);
            addButton = view.findViewById(R.id.addImageButton);
        }
    }

    public static class SelectedViewHolder extends RecyclerView.ViewHolder {
        final TextView productNameTextView;
        final TextView productDescriptionTextView;
        final TextView priceCatalogTextView;
        final ImageButton clearButton;
        final ImageButton changeButton;
        final ImageView imageView;

        SelectedViewHolder(View view) {
            super(view);
            productNameTextView = view.findViewById(R.id.productNameTextView);
            productDescriptionTextView = view.findViewById(R.id.productDescriptionTextView);
            priceCatalogTextView = view.findViewById(R.id.priceCatalogTextView);
            clearButton = view.findViewById(R.id.clearButton);
            changeButton = view.findViewById(R.id.changeButton);
            imageView = view.findViewById(R.id.imageView);
        }

    }
    public static class SelectedViewHolderStorage extends RecyclerView.ViewHolder {
        final TextView productNameTextView;
        final TextView productDescriptionTextView;
        final TextView priceCatalogTextView;
        final ImageButton clearButton;
        final ImageButton changeButton;
        final ImageView imageView;

        SelectedViewHolderStorage(View view) {
            super(view);
            productNameTextView = view.findViewById(R.id.productNameTextView);
            productDescriptionTextView = view.findViewById(R.id.productDescriptionTextView);
            priceCatalogTextView = view.findViewById(R.id.priceCatalogTextView);
            clearButton = view.findViewById(R.id.clearButton);
            changeButton = view.findViewById(R.id.changeButton);
            imageView = view.findViewById(R.id.imageView);
        }

    }

    public interface OnAddButtonClickListener {
        void onAddClick(Component item,int type);
    }
    public static class ButtonsViewHolder extends RecyclerView.ViewHolder{
        private Button clearButton;
        private final Button saveButton;

        public ButtonsViewHolder(@NonNull View itemView) {
            super(itemView);
            clearButton = itemView.findViewById(R.id.clearButton);
            saveButton = itemView.findViewById(R.id.saveButton);
        }
    }
}