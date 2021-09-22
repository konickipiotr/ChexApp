package com.chex.module.registration;

import android.os.AsyncTask;

import com.chex.config.Settings;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class RegistrationAsync extends AsyncTask<RegistrationForm, Void, Boolean> {
    @Override
    protected Boolean doInBackground(RegistrationForm... registrationForms) {

        String path = Settings.ROOT_PATH + "/registration";
        RestTemplate restTemplate = new HttpRequestUtils().getRestTemplate();

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(path, registrationForms[0], Void.class);
        if(responseEntity.getStatusCode().equals(HttpStatus.FOUND))
            return true;

        return false;
    }
}
