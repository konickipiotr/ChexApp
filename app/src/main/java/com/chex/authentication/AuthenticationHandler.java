package com.chex.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.loader.ResourcesLoader;
import android.os.AsyncTask;
import android.view.View;

import com.chex.Home;
import com.chex.MainActivity;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.user.User;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.lang.ref.WeakReference;
import java.util.Collections;

public class AuthenticationHandler extends AsyncTask<String, Void, String> {

    private final WeakReference<MainActivity> activityReference;

    public AuthenticationHandler(MainActivity context) {
        this.activityReference = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(String... strings) {

        MainActivity context = this.activityReference.get();
        String path = Settings.ROOT_PATH + "/login";
        String username = strings[0];
        String password = strings[1];

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
        requestHeaders.setAuthorization(authHeader);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        RestTemplate restTemplate = new HttpRequestUtils().getRestTemplate();
        String ret = null;
        try {
            ResponseEntity<Auth> response = restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<>(requestHeaders), Auth.class);
            Auth auth = response.getBody();
            if(auth.getAccountStatus().equals(AccountStatus.INACTIVE)){
                return context.getResources().getString(R.string.account_is_inactive);
            }

            Settings.username = username;
            Settings.password = password;

            path = Settings.ROOT_PATH + "/user";
            ResponseEntity<User> response2 = restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<>(requestHeaders), User.class);
            Settings.user = response2.getBody();

        }catch (HttpClientErrorException e){
            ret = context.getResources().getString(R.string.wrong_credentials);
        }catch (ResourceAccessException e){
            ret = context.getResources().getString(R.string.connection_timeout);
        }
        return ret;
    }

    @Override
    protected void onPostExecute(String ret) {
        super.onPostExecute(ret);

        MainActivity context = this.activityReference.get();
        if(ret != null){
            context.error_message.setVisibility(View.VISIBLE);
            context.error_message.setText(ret);
            context.username.setText("");
            context.password.setText("");
            Settings.clearData();
            return;
        }
        saveCredentials(context);
        context.finish();
        context.startActivity(new Intent(context, Home.class));
    }

    private void saveCredentials(MainActivity context){
        SharedPreferences sp = context.getSharedPreferences("chexCredential", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", Settings.username);
        editor.putString("password", Settings.password);
        editor.commit();
    }
}
