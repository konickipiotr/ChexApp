package com.chex.modules.changeprofilephoto;

import android.os.AsyncTask;

import com.chex.config.Settings;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class SendNewProfilePhotoAsync extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils();
        HttpHeaders requestHeaders = httpRequestUtils.getRequestHeaders();
        RestTemplate restTemplate = httpRequestUtils.getRestTemplate();
        String stringFile = strings[0];
        String path = Settings.ROOT_PATH + "/user/profilephoto";
        try {
            restTemplate.postForEntity(path, new HttpEntity<>(stringFile, requestHeaders), Void.class);
        }catch (HttpClientErrorException e){

        }
        return null;
    }
}
