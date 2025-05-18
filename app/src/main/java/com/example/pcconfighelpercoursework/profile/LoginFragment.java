package com.example.pcconfighelpercoursework.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.pcconfighelpercoursework.R;

public class LoginFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private Button regButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.signupButton);
        loginButton.setText("Вход"); // Меняем текст кнопки

        regButton = view.findViewById(R.id.regButton);

        regButton.setOnClickListener(v -> {
            // Получаем NavController
            NavController navController = Navigation.findNavController(view);

        });

        loginButton.setOnClickListener(v -> {
            if (validateInput()) {
                navigateToProfile();
            }
        });

        return view;
    }

    private boolean validateInput() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty()) {
            usernameEditText.setError("Введите логин");
            return false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Введите пароль");
            return false;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Пароль должен содержать минимум 6 символов");
            return false;
        }

        return true;
    }

    private void navigateToProfile() {
        NavController navController = Navigation.findNavController(requireView());

        /*navController.navigate(
                        R.id.action_loginFragment_to_profileFragment,
                null,
                new NavOptions.Builder()
                                .setPopUpTo(R.id.loginFragment, true)
                                .build(),
                null
                );*/
    }
}