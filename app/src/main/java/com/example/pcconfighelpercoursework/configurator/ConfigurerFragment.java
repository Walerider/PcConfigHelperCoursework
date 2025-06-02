package com.example.pcconfighelpercoursework.configurator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.catalog.CatalogAdapter;
import com.example.pcconfighelpercoursework.catalog.CatalogFragment;
import com.example.pcconfighelpercoursework.items.Component;
import com.example.pcconfighelpercoursework.utils.AssemblyData;
import com.example.pcconfighelpercoursework.utils.ItemDecoration;
import com.example.pcconfighelpercoursework.utils.NavigationData;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfigurerFragment extends Fragment implements ConfigurerAdapter.OnAddButtonClickListener{

    private RecyclerView recyclerView;
    ConfigurerAdapter configurerAdapter;
    private ImageButton profileButton;
    @SuppressLint("StaticFieldLeak")
    private static TextView priceTextView;
    @SuppressLint("StaticFieldLeak")
    private static TextView assemblyNameTextView;

    static AtomicInteger a;
    public ConfigurerFragment() {
    }
    public static ConfigurerFragment newInstance() {
        return new ConfigurerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_configurer, container, false);
        int spacingInPixels = getActivity().getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerView = mainView.findViewById(R.id.compView);
        recyclerView.addItemDecoration(new ItemDecoration(spacingInPixels));
        priceTextView = mainView.findViewById(R.id.priceTextView);
        assemblyNameTextView = mainView.findViewById(R.id.assemblyNameTextView);
        MainActivity activity = (MainActivity)this.getActivity();
        activity.changeNavigationStartDestination();
        if(activity.getBottomNavigationView().getSelectedItemId() != R.id.nav_home){
            activity.getBottomNavigationView().setSelectedItemId(R.id.nav_home);
        }
        Log.e("socket",AssemblyData.getString("socket"));
        a = new AtomicInteger(0);
        if(AssemblyData.getString("name").isEmpty()){
            assemblyNameTextView.setText("Название сборки");
        }else {
            assemblyNameTextView.setText(AssemblyData.getString("name"));
        }
        Log.e("assembly name",AssemblyData.getString("name"));
        assemblyNameTextView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            final EditText input = new EditText(getContext());
            input.setHint(assemblyNameTextView.getText());
            builder.setTitle("Редактирование текста")
                    .setView(input)
                    .setPositiveButton("Сохранить", (dialog, which) -> {
                        String newText = input.getText().toString();
                        assemblyNameTextView.setText(newText);
                        AssemblyData.setString("name",newText);
                    })
                    .setNegativeButton("Отмена", null)
                    .show();
        });;
        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        configurerAdapter = new ConfigurerAdapter(MainActivity.getComponents(),getContext(),Navigation.findNavController(requireView()));
        configurerAdapter.setOnAddButtonClickListener(this::onAddClick);
        configurerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(configurerAdapter);
        if(!MainActivity.checkComponents(MainActivity.getComponents())){
            MainActivity.fillComponents(MainActivity.getComponents());
        }
        setPrice();
    }

    public static TextView getPriceTextView() {
        return priceTextView;
    }

    public static TextView getAssemblyNameTextView() {
        return assemblyNameTextView;
    }

    public static void setPrice() {
        MainActivity.getComponents().stream().filter(c -> c.getPrice() > 0).forEach(c -> a.addAndGet(c.getPrice()));
        ConfigurerFragment.priceTextView.setText("Итого: " + a + "р");

    }

    @Override
    public void onAddClick( Component item,int type) {

        /*CatalogFragment catalogFragment = CatalogFragment.newInstance(item, CatalogAdapter.ADD_CONFIG);
        Log.e("asdasdfgfg",item.getComponentType());
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .replace(R.id.fragmentContainerView, catalogFragment,"catalog_fragment")
                .addToBackStack("catalog_fragment_backstack")
                .commit();*/
        NavigationData.init(requireContext());
        NavigationData.setBoolean("add",true);
        NavController navController = ((MainActivity)requireActivity()).getNavController();
        Bundle args = new Bundle();
        args.putParcelable("component", item);
        args.putInt("type", type);
        Log.e("onItemClick",args.toString());

        navController.navigate(R.id.catalogFragment,args);

    }

}