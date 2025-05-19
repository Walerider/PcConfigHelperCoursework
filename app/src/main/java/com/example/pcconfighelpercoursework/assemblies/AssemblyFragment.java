package com.example.pcconfighelpercoursework.assemblies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.utils.UserData;

public class AssemblyFragment extends Fragment {

    private int userId;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assembly, container, false);
        return view;
    }
}