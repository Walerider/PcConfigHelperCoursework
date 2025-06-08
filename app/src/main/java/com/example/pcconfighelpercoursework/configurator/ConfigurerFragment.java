package com.example.pcconfighelpercoursework.configurator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        assemblyNameTextView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            final EditText input = new EditText(getContext());
            input.setHint(assemblyNameTextView.getText());
            builder.setTitle("Редактирование текста")
                    .setView(input)
                    .setPositiveButton("Сохранить", (dialog, which) -> {
                        String newText = input.getText().toString();
                        assemblyNameTextView.setText(newText);
                        AssemblyData.setString("name", newText);
                    })
                    .setNegativeButton("Отмена", null);

            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(d -> {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                TypedValue typedValue = new TypedValue();
                getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorSecondary, typedValue, true);
                int colorSecondary = typedValue.data;
                TypedValue errorValue = new TypedValue();
                getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorError, errorValue, true);
                int colorError = errorValue.data;
                if (positiveButton != null) {
                    positiveButton.setTextColor(colorSecondary);
                }
                if (negativeButton != null) {
                    negativeButton.setTextColor(colorError);
                }
            });
            dialog.show();
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