package com.chex.modules.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chex.MainActivity;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email;
    private TextView error_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
    }

    private void init(){
        Button send_btn = findViewById(R.id.fg_send_btn);
        email = findViewById(R.id.fg_email);
        error_msg = findViewById(R.id.fg_msg_error);

        send_btn.setOnClickListener(v -> {
            error_msg.setText("");
            String sEmail = email.getText().toString().trim();
            if(sEmail.isEmpty()){
                error_msg.setText(getResources().getText(R.string.email_cannot_be_empty));
                return;
            }

            try {
                Boolean found = new ResetPassword().execute(sEmail).get();
                if(found){
                    Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                    String info_msg = String.format(getResources().getString(R.string.reset_password_info_sent), this.email.getText().toString().trim());
                    intent.putExtra("info_msg", info_msg);
                    startActivity(intent);
                    finish();
                }else {
                    error_msg.setText(getResources().getText(R.string.email_not_found));
                }

            } catch (ExecutionException | InterruptedException e) {
                error_msg.setText(getResources().getText(R.string.something_went_wrong));
            }
        });
    }

    static class ResetPassword extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            String path = Settings.ROOT_PATH + "/forgotpassword";
            RestTemplate restTemplate = new HttpRequestUtils().getRestTemplate();
            String email = strings[0];

            try {
                ResponseEntity<Void> responseEntity = restTemplate.postForEntity(path, email, Void.class);
                if(responseEntity.getStatusCode().equals(HttpStatus.FOUND))
                    return true;
            }catch (HttpClientErrorException e){
                e.printStackTrace();
            }
            return false;
        }
    }
}