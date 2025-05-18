package com.example.pcconfighelpercoursework.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.NavOptions;

import com.example.pcconfighelpercoursework.R;

public class ProfileFragment extends Fragment {

    private TextView userNameTextView;
    private Button logoutButton;
    private Button viewBuildsButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userNameTextView = view.findViewById(R.id.userNameTextView);
        logoutButton = view.findViewById(R.id.logoutButton);
        viewBuildsButton = view.findViewById(R.id.viewBuildsButton);

        logoutButton.setOnClickListener(v -> {
            // Очищаем историю навигации и переходим на экран логина
            NavController navController = Navigation.findNavController(view);

            // Создаем NavOptions для очистки back stack
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.bottom_navigation, true) // Очищаем весь граф навигации
                    .build();

            navController.navigate(
                    R.id.loginFragment, // ID вашего фрагмента авторизации
                    null,
                    navOptions
            );

            // Здесь можно добавить очистку данных авторизации (SharedPreferences и т.д.)
            // clearAuthData();
        });

        viewBuildsButton.setOnClickListener(v -> {
           /* NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_profileFragment_to_buildsFragment);*/
        });

        return view;
    }

    // Метод для очистки данных авторизации (пример)
    private void clearAuthData() {
        // SharedPreferences preferences = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
        // preferences.edit().clear().apply();
    }
}