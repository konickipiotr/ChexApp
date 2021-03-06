package com.chex.modules.checkplace;

import android.content.Intent;
import android.os.AsyncTask;

import com.chex.Home;
import com.chex.config.Settings;
import com.chex.modules.checkplace.addphoto.AddPlacePhotoActivity;
import com.chex.utils.HttpRequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.lang.ref.WeakReference;

public class UploadPlaceAsync extends AsyncTask<AchievedPlaceDTO, Void, Void> {

    private final WeakReference<AddPlacePhotoActivity> context;

    public UploadPlaceAsync(AddPlacePhotoActivity addPlacePhotoActivity) {
        context = new WeakReference<>(addPlacePhotoActivity);
    }

    @Override
    protected Void doInBackground(AchievedPlaceDTO... dtos) {

        String path = Settings.ROOT_PATH + "/checkplace/finalize";

        HttpRequestUtils requestUtils = new HttpRequestUtils();
        HttpHeaders requestHeaders = requestUtils.getRequestHeaders();
        RestTemplate restTemplate = new HttpRequestUtils().getRestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        AchievedPlaceDTO dto = dtos[0];

        HttpEntity<AchievedPlaceDTO> request = new HttpEntity<>(dto, requestHeaders);
        restTemplate.postForEntity(path, request, Void.class);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        AddPlacePhotoActivity activity = context.get();
        activity.finish();
        activity.startActivity(new Intent(activity, Home.class));
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
