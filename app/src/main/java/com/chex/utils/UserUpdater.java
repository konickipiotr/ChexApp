package com.chex.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chex.ProfilePhotoUpdater;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.modules.changeprofilephoto.ProfilePhotoActivity;
import com.chex.user.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class UserUpdater {

    public void setPhoto(Activity activity, ImageView profilePhoto){
        if(Settings.user.getImgurl() == null
                || Settings.user.getImgurl().isEmpty()
                || Settings.profilePhotoBitmap == null){

            Glide.with(activity)
                    .load(ContextCompat.getDrawable(activity, R.drawable.user))
                    .circleCrop()
                    .into(profilePhoto);
        }else {
            Glide.with(activity)
                    .load(Settings.profilePhotoBitmap)
                    .circleCrop()
                    .into(profilePhoto);
        }
    }

    public void setUserInfoBar(Activity activity){
        ImageView profilePhoto = activity.findViewById(R.id.profilePhoto);
        TextView usename = activity.findViewById(R.id.chex_username);
        TextView level = activity.findViewById(R.id.user_current_level);
        TextView nextLevel = activity.findViewById(R.id.user_next_level);
        TextView exp = activity.findViewById(R.id.user_exp);
        TextView userTitle = activity.findViewById(R.id.user_title);
        ProgressBar progressBar = activity.findViewById(R.id.user_exp_progress_bar);

        setPhoto(activity, profilePhoto);

        usename.setText(Settings.user.getName());
        level.setText(String.valueOf(Settings.user.getLevel()));
        nextLevel.setText(String.valueOf(Settings.user.getLevel() + 1));
        exp.setText(String.valueOf(Settings.user.getExp()));

        userTitle.setText(Settings.user.getTitle());

        progressBar.setMax(Settings.user.getNextlevel());
        progressBar.setMin(0);
        progressBar.setProgress(Settings.user.getExp());

        profilePhoto.setOnClickListener(v -> activity.startActivity(new Intent(activity, ProfilePhotoActivity.class)));
    }

    public void updateUserFromServer(ProfilePhotoUpdater profilePhotoUpdater){
        new GetUser(profilePhotoUpdater).execute();
    }


    class GetUser extends AsyncTask<Void, Void, User> {

        private final ProfilePhotoUpdater profilePhotoUpdater;

        public GetUser(ProfilePhotoUpdater profilePhotoUpdater) {
            this.profilePhotoUpdater = profilePhotoUpdater;
        }

        @Override
        protected User doInBackground(Void... voids) {

            HttpHeaders requestHeaders = new HttpRequestUtils().getRequestHeaders();
            RestTemplate restTemplate = new HttpRequestUtils().getRestTemplate();

            String path = Settings.ROOT_PATH + "/user";
            User user = null;
            try {
                ResponseEntity<User> response = restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<>(requestHeaders), User.class);
                user = response.getBody();
            }catch (HttpClientErrorException e){
                e.printStackTrace();
            }

            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            if(user == null)
                return;

            Settings.user = user;
            new PhotoGetter(this.profilePhotoUpdater).execute(user.getImgurl());
        }
    }


    class PhotoGetter extends AsyncTask<String, Void, Bitmap>{

        private final ProfilePhotoUpdater profilePhotoUpdater;

        public PhotoGetter(ProfilePhotoUpdater profilePhotoUpdater) {
            this.profilePhotoUpdater = profilePhotoUpdater;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                java.net.URL url = new java.net.URL(Settings.domain + strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Settings.profilePhotoBitmap = bitmap;
            profilePhotoUpdater.updateProfilePhoto();
        }
    }
}
