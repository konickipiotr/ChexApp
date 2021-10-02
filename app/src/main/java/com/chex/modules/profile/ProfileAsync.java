package com.chex.modules.profile;

import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chex.config.Settings;
import com.chex.modules.Achievements.AchievementView;
import com.chex.modules.place.PlaceView;
import com.chex.utils.HttpRequestUtils;
import com.chex.utils.MarginItemDecoration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.ref.WeakReference;
import java.util.List;

public class ProfileAsync extends AsyncTask<String, Void, ProfileResponse> {

    private final WeakReference<ProfileActivity> activity;

    public ProfileAsync(ProfileActivity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected ProfileResponse doInBackground(String... strings) {
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils();
        HttpHeaders requestHeaders = httpRequestUtils.getRequestHeaders();
        RestTemplate restTemplate = httpRequestUtils.getRestTemplate();
        String path = Settings.ROOT_PATH + "/profile/" + strings[0];

        ResponseEntity<ProfileResponse> response = restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<>(requestHeaders), ProfileResponse.class);

        return response.getBody();
    }

    @Override
    protected void onPostExecute(ProfileResponse profileResponse) {
        super.onPostExecute(profileResponse);
        ProfileActivity profileActivity = activity.get();

        profileActivity.ach_num.setText(String.valueOf(profileResponse.getAchNum()));
        profileActivity.places_num.setText(String.valueOf(profileResponse.getPlaceNum()));
        profileActivity.friends_num.setText(String.valueOf(profileResponse.getFriendsNum()));

        List<PlaceView> places = profileResponse.getPlaces();

        profileActivity.placesView.setLayoutManager(new LinearLayoutManager(profileActivity));
        profileActivity.placesView.addItemDecoration(new MarginItemDecoration(10));
        profileActivity.placesView.setAdapter(new ProfilePlaceAdapter(places, profileActivity));

        List<PlaceView> lastThree = profileResponse.getLastThree();
        profileActivity.lastThreeView.setLayoutManager(new LinearLayoutManager(profileActivity));
        profileActivity.lastThreeView.addItemDecoration(new MarginItemDecoration(10));
        profileActivity.lastThreeView.setAdapter(new ProfilePlaceAdapter(lastThree, profileActivity));

        List<AchievementView> achievementViews = profileResponse.getAchievementViews();
        profileActivity.achView.setLayoutManager(new LinearLayoutManager(profileActivity));
        profileActivity.achView.addItemDecoration(new MarginItemDecoration(10));
        profileActivity.achView.setAdapter(new ProfileAchievementAdapter(achievementViews, profileActivity));

        List<AchievementView> achInProgress = profileResponse.getAchInProgress();
        profileActivity.achInPView.setLayoutManager(new LinearLayoutManager(profileActivity));
        profileActivity.achInPView.addItemDecoration(new MarginItemDecoration(10));
        profileActivity.achInPView.setAdapter(new ProfileAchievementAdapter(achInProgress, profileActivity));
    }
}
