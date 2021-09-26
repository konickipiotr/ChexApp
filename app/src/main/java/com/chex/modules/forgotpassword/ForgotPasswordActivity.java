package com.chex.modules.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chex.R;
import com.chex.config.Settings;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button send_btn;
    private EditText email;
    private TextView error_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
    }

    private void init(){
        send_btn = findViewById(R.id.send_form_btn);
        email = findViewById(R.id.fg_email);
        error_msg = findViewById(R.id.fg_msg_error);
    }

    class ResetPassword extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            String path = Settings.ROOT_PATH + "/forgotpassword";
            RestTemplate restTemplate = new HttpRequestUtils().getRestTemplate();
            String email = strings[0];

            ResponseEntity<Void> responseEntity = restTemplate.postForEntity(path, email, Void.class);
            if(responseEntity.getStatusCode().equals(HttpStatus.FOUND))
                return true;

            return false;
        }
    }
}