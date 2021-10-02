package com.chex.modules.posts.listeners;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chex.config.Settings;
import com.chex.modules.posts.model.Star;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ChangeStarListener implements View.OnClickListener {

    private final Long postid;
    private final ImageButton starOn, starOff;
    private final TextView starnum;

    public ChangeStarListener(ImageButton starOff, ImageButton starOn, TextView tv_starnum, Long postid) {
        this.postid = postid;
        this.starOn = starOn;
        this.starOff = starOff;
        this.starnum = tv_starnum;
    }

    @Override
    public void onClick(View v) {

        Star star = new Star();
        star.setPostid(postid);
        star.setUserid(Settings.user.getId());
        new ChangeStarAsync().execute(star);
    }

    @SuppressLint("StaticFieldLeak")
    class ChangeStarAsync extends AsyncTask<Star, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Star... stars) {
            Star star = stars[0];
            HttpRequestUtils requestUtils = new HttpRequestUtils();
            HttpHeaders requestHeaders = requestUtils.getRequestHeaders();
            RestTemplate restTemplate = requestUtils.getRestTemplate();
            String path = Settings.ROOT_PATH + "/post/changestar";
            ResponseEntity<Boolean> response = restTemplate.postForEntity(path, new HttpEntity<>(star, requestHeaders), Boolean.class);
            return response.getBody();
        }

        @Override
        protected void onPostExecute(Boolean star) {
            super.onPostExecute(star);

            int starN = Integer.parseInt(starnum.getText().toString());
            if(star){
                starOn.setVisibility(View.VISIBLE);
                starOff.setVisibility(View.GONE);
                starN++;
            }else {
                starOn.setVisibility(View.GONE);
                starOff.setVisibility(View.VISIBLE);
                starN--;
            }
            starnum.setText(String.valueOf(starN));
        }
    }
}
