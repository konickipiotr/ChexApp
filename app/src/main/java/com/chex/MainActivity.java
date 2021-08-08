package com.chex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chex.authentication.AuthenticationHandler;

public class MainActivity extends AppCompatActivity {

    public EditText username;
    public EditText password;
    public Button signinBtn;
    public TextView error_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        username = findViewById(R.id.login_username_input);
        password = findViewById(R.id.login_password_input);
        signinBtn = findViewById(R.id.login_btn);
        error_message = findViewById(R.id.login_error_message);

        signinBtn.setOnClickListener(v -> {

            String login = username.getText().toString();
            String pass = password.getText().toString();
            new AuthenticationHandler(this).execute(login, pass);
        });
    }
}