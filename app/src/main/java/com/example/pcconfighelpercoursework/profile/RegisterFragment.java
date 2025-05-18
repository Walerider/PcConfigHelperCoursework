package com.example.pcconfighelpercoursework.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.NavOptions;

import com.example.pcconfighelpercoursework.R;

public class RegisterFragment extends Fragment {

    private EditText usernameEditText, emailEditText, passwordEditText;
    private Button signupButton, loginButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        usernameEditText = view.findViewById(R.id.usernameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        signupButton = view.findViewById(R.id.signupButton);
        loginButton = view.findViewById(R.id.loginButton);

        signupButton.setOnClickListener(v -> attemptRegistration());

        // Обработчик для кнопки авторизации
        loginButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);

            // Создаем NavOptions для очистки стека
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.registerFragment, true) // Удаляем текущий фрагмент из стека
                    .build();

            navController.navigate(
                    R.id.loginFragment,
                    null,
                    navOptions
            );
        });

        return view;
    }

    private void attemptRegistration() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty()) {
            usernameEditText.setError("Введите логин");
            return;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Введите корректный email");
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordEditText.setError("Пароль должен содержать минимум 6 символов");
            return;
        }

        registerUser(username, email, password);
    }

    private void registerUser(String username, String email, String password) {
        // Здесь должна быть реальная логика регистрации
        // Например, сохранение в SharedPreferences или запрос к API

        // После успешной регистрации:
        Toast.makeText(getContext(), "Регистрация успешна!", Toast.LENGTH_SHORT).show();

        // Переход на экран авторизации с очисткой стека навигации
        NavController navController = Navigation.findNavController(requireView());

        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.registerFragment, true) // Очищаем стек до registerFragment
                .build();

        navController.navigate(
                R.id.loginFragment, // ID вашего фрагмента авторизации
                null,
                navOptions
        );
    }
}