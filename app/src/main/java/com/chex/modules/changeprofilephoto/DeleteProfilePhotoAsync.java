package com.chex.modules.changeprofilephoto;

import android.os.AsyncTask;

import com.chex.config.Settings;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class DeleteProfilePhotoAsync extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        HttpHeaders requestHeaders = new HttpRequestUtils().getRequestHeaders();
        RestTemplate restTemplate = new HttpRequestUtils().getRestTemplate();
        String path = Settings.ROOT_PATH + "/user/profilephoto";
        restTemplate.exchange(path, HttpMethod.DELETE, new HttpEntity<>(requestHeaders), Void.class);
        return null;
    }
}
