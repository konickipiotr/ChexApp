package com.chex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chex.authentication.AuthenticationHandler;
import com.chex.module.registration.RegistrationActivity;

public class MainActivity extends AppCompatActivity {

    public EditText username, password;
    public Button signinBtn, registerBtn, forgotPassBtn;
    public TextView error_message, info_message;
    private static final int PERMISSION_FINE_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        init();

        Intent intent = getIntent();
        String info_msg = intent.getStringExtra("info_msg");
        if(info_msg != null && !info_msg.isEmpty()){
            info_message.setVisibility(View.VISIBLE);
            info_message.setText(info_msg);
        }





        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.i("eeeee", "errrrprrr");
        }else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }
        }

//        String login = "user1";
//        String pass = "11";
//        new AuthenticationHandler(this).execute(login, pass);

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode){
//            case PERMISSION_FINE_LOCATION:
//                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//
//                }else {
//                    Toast.makeText(this, "This app requires permission to be granted in order to work properly", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }

    private void init() {
        username = findViewById(R.id.login_username_input);
        password = findViewById(R.id.login_password_input);
        signinBtn = findViewById(R.id.signin_btn);
        registerBtn = findViewById(R.id.register_btn);
        error_message = findViewById(R.id.login_error_message);
        info_message = findViewById(R.id.info_message);

        registerBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
        });

        signinBtn.setOnClickListener(v -> {
            error_message.setVisibility(View.GONE);
            info_message.setVisibility(View.GONE);
            String login = username.getText().toString();
            String pass = password.getText().toString();
            new AuthenticationHandler(this).execute(login, pass);
        });
    }
}