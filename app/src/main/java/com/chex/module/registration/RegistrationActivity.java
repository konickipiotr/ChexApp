package com.chex.module.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chex.MainActivity;
import com.chex.R;

import java.util.concurrent.ExecutionException;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email, password, password2, firstname, lastname;
    private TextView errorMsg;
    private RadioButton male_btn;
    private AutoCompleteTextView country;
    private Button sendFormBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    private void init(){
        email = findViewById(R.id.username);
        password = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        male_btn = findViewById(R.id.male_btn);
        country = findViewById(R.id.countryList);
        sendFormBtn = findViewById(R.id.send_form_btn);
        errorMsg = findViewById(R.id.error_msg);

        sendFormBtn.setOnClickListener(v -> {
            errorMsg.setVisibility(View.INVISIBLE);

            if(wrongEmail()) return;
            if(wrongPassword()) return;
            if(wrongRequiredFields()) return;

            RegistrationForm registrationForm = collectDate();

            try {
                Boolean userExists = new RegistrationAsync().execute(registrationForm).get();
                if(userExists){
                    setErrorMsg(R.string.email_exists);
                }else {
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    String info_msg = String.format(getResources().getString(R.string.activation_code_sent), this.email.getText().toString());
                    intent.putExtra("info_msg", info_msg);
                    startActivity(intent);
                    finish();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private RegistrationForm collectDate(){
        RegistrationForm rf = new RegistrationForm();
        rf.setEmail(this.email.getText().toString().trim().toLowerCase());
        rf.setPassword(this.password.getText().toString().trim());
        rf.setFirstname(this.firstname.getText().toString().trim());
        rf.setLastname(this.lastname.getText().toString().trim());
        rf.setGender(this.male_btn.isChecked() ? "M" : "F");
        rf.setCountry(this.country.getText().toString().trim());
        return rf;
    }

    private boolean wrongRequiredFields(){

        if(this.firstname.getText().toString().trim().isEmpty()){
            setErrorMsg(R.string.first_name_cannot_be_empty);
            return true;
        }

        if(this.lastname.getText().toString().trim().isEmpty()){
            setErrorMsg(R.string.last_name_cannot_be_empty);
            return true;
        }
        return false;
    }

    private boolean wrongPassword(){
        String password = this.password.getText().toString().trim();
        String password2 = this.password2.getText().toString().trim();


        if(password.isEmpty() || password2.isEmpty()){
            setErrorMsg(R.string.password_cannot_be_empty);
            cleanPasswordField();
            return true;
        }

        if(!password.equals(password2)){
            setErrorMsg(R.string.passwords_are_not_the_same);
            cleanPasswordField();
            return true;
        }

        if(password.length() < 6){
            setErrorMsg(R.string.password_is_too_short);
            cleanPasswordField();
            return true;
        }
        return false;
    }

    private void cleanPasswordField(){
        this.password.setText("");
        this.password2.setText("");
    }

    private boolean wrongEmail(){
        String emailContent = email.getText()
                .toString()
                .trim();

        if(emailContent.isEmpty()){
            setErrorMsg(R.string.email_cannot_be_empty);
            return true;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailContent).matches()){
            setErrorMsg(R.string.email_is_not_email);
            return true;
        }
        return false;
    }


    private void setErrorMsg(int code){
        String message = getResources().getString(code);
        errorMsg.setVisibility(View.VISIBLE);
        errorMsg.setText(message);
    }
}