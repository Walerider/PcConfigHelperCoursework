package com.example.pcconfighelpercoursework.assemblies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pcconfighelpercoursework.R;
import com.example.pcconfighelpercoursework.api.API;
import com.example.pcconfighelpercoursework.api.APIClient;
import com.example.pcconfighelpercoursework.api.items.UserAssemblyDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAssemblyLookFragment extends Fragment {
    TextView assembliesTextView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    public UserAssemblyLookFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static UserAssemblyLookFragment newInstance(String param1, String param2) {
        UserAssemblyLookFragment fragment = new UserAssemblyLookFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_assembly_look, container, false);
        assembliesTextView = view.findViewById(R.id.assembliesTextView);
        return view;
    }
    private class GetAssemblies{
        int currIndex = 0;
        List<UserAssemblyDAO> list = new ArrayList<>();
        public void getAssemblies() {
            if (currIndex >= 1) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(new AssemblyAdapter(getContext(),list));
                recyclerView.setVisibility(View.VISIBLE);
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            API apiService = APIClient.getApi();
            /*Call<List<UserAssemblyDAO>> call = apiService.getAllAssembliesByUserId();
            call.enqueue(new Callback<List<UserAssemblyDAO>>() {
                @Override
                public void onResponse(@NonNull Call<List<UserAssemblyDAO>> call, @NonNull Response<List<UserAssemblyDAO>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        currIndex++;
                        list.addAll(response.body());
                        getAssemblies();
                    } else {
                        Toast.makeText(getContext(), "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<UserAssemblyDAO>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Ошибка запроса", t);
                }
            });*/
        }
    }
}