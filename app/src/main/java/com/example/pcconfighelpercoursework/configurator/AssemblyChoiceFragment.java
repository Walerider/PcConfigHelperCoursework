package com.example.pcconfighelpercoursework.configurator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pcconfighelpercoursework.MainActivity;
import com.example.pcconfighelpercoursework.R;

public class AssemblyChoiceFragment extends Fragment {
    TextView toolbarTitleTextView;
    Button createAssemblyButton;
    NavController navController;
    public AssemblyChoiceFragment() {

    }
    public static AssemblyChoiceFragment newInstance(String param1, String param2) {
        AssemblyChoiceFragment fragment = new AssemblyChoiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assembly_choice, container, false);
        navController = ((MainActivity)requireActivity()).getNavController();;
        toolbarTitleTextView = view.findViewById(R.id.toolbarTitleTextView);
        createAssemblyButton = view.findViewById(R.id.createAssemblyButton);
        toolbarTitleTextView.setText("Выбор");
        createAssemblyButton.setOnClickListener(v ->{
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.assemblyChoiceFragment, true)
                    .build();
            navController.navigate(R.id.configurerFragment,null,navOptions);

        });
        MainActivity activity = (MainActivity)this.getActivity();
        if(activity.getBottomNavigationView().getSelectedItemId() != R.id.nav_home){
            activity.getBottomNavigationView().setSelectedItemId(R.id.nav_home);
        }
        return view;
    }
}