package com.example.pcconfighelpercoursework.configurator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import com.example.pcconfighelpercoursework.utils.NavigationData;

import java.util.concurrent.atomic.AtomicInteger;

public class ConfigurerFragment extends Fragment implements ConfigurerAdapter.OnAddButtonClickListener{

    private RecyclerView recyclerView;
    ConfigurerAdapter configurerAdapter;
    private ImageButton profileButton;
    private static TextView priceTextView;
    AtomicInteger a;
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
        MainActivity activity = (MainActivity)this.getActivity();
        activity.changeNavigationStartDestination();
        if(activity.getBottomNavigationView().getSelectedItemId() != R.id.nav_home){
            activity.getBottomNavigationView().setSelectedItemId(R.id.nav_home);
        }
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
        setPrice();
    }

    public static TextView getPriceTextView() {
        return priceTextView;
    }

    public static void setPrice() {
        AtomicInteger a = new AtomicInteger();
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
        NavigationData.init(getContext());
        NavigationData.setBoolean("add",true);
        NavController navController = ((MainActivity)requireActivity()).getNavController();
        Bundle args = new Bundle();
        args.putParcelable("component", item);
        args.putInt("type", type);
        Log.e("onItemClick",args.toString());

        navController.navigate(R.id.catalogFragment,args);

    }

}