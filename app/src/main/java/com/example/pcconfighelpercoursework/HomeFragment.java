package com.example.pcconfighelpercoursework;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HomeFragment extends Fragment {

    private List<ConfigurerItem> components;
    MainActivity activity;
    RecyclerView recyclerView;
    ConfigurerAdapter configurerAdapter;
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
        activity = (MainActivity) getActivity();
        components = activity != null ? activity.getComponents() : null;
        configurerAdapter = new ConfigurerAdapter(components,getContext());
        recyclerView = mainView.findViewById(R.id.compView);
        recyclerView.setAdapter(configurerAdapter);
        return mainView;
    }
}