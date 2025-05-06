package com.example.pcconfighelpercoursework.configurator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.catalog.CatalogAdapter;
import com.example.pcconfighelpercoursework.catalog.CatalogFragment;
import com.example.pcconfighelpercoursework.items.Component;
import com.example.pcconfighelpercoursework.utils.ItemDecoration;

import java.util.concurrent.atomic.AtomicInteger;

public class HomeFragment extends Fragment implements ConfigurerAdapter.OnAddButtonClickListener{

    RecyclerView recyclerView;
    ConfigurerAdapter configurerAdapter;
    ImageButton profileButton;
    TextView priceTextView;
    public HomeFragment() {
    }
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_home, container, false);
        int spacingInPixels = getActivity().getResources().getDimensionPixelSize(R.dimen.item_spacing);
        recyclerView = mainView.findViewById(R.id.compView);
        recyclerView.addItemDecoration(new ItemDecoration(spacingInPixels));
        priceTextView = mainView.findViewById(R.id.priceTextView);

        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        configurerAdapter = new ConfigurerAdapter(MainActivity.getComponents(),getContext());
        configurerAdapter.setOnAddButtonClickListener(this::onAddClick);
        configurerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(configurerAdapter);
        if(!MainActivity.checkComponents(MainActivity.getComponents())){
            MainActivity.fillComponents(MainActivity.getComponents());
        }
        AtomicInteger a = new AtomicInteger();
        MainActivity.getComponents().stream().filter(c -> c.getPrice() > 0).forEach(c -> a.addAndGet(c.getPrice()));
        priceTextView.setText("Итого: " + a + "р");
    }


    @Override
    public void onAddClick( Component item) {

        CatalogFragment catalogFragment = CatalogFragment.newInstance(item, CatalogAdapter.ADD_CONFIG);
        Log.e("asdasdfgfg",item.getComponentType());
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
                .replace(R.id.fragmentContainerView, catalogFragment,"catalog_fragment")
                .addToBackStack("catalog_fragment_backstack")
                .commit();
    }

}