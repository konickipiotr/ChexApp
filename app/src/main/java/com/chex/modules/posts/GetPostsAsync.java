package com.chex.modules.posts;


import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chex.Home;
import com.chex.config.Settings;
import com.chex.modules.posts.model.PostView;
import com.chex.modules.posts.model.PostsResponse;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.ref.WeakReference;
import java.util.List;


public class GetPostsAsync extends AsyncTask<Void, Void, List<PostView>> {

    private WeakReference<Home> homeReference;

    public GetPostsAsync(Home activity) {
        this.homeReference = new WeakReference<>(activity);
    }

    @Override
    protected List<PostView> doInBackground(Void... voids) {

        HttpRequestUtils requestUtils = new HttpRequestUtils();
        HttpHeaders requestHeaders = requestUtils.getRequestHeaders();
        RestTemplate restTemplate = requestUtils.getRestTemplate();
        String path = Settings.ROOT_PATH + "/post/public/1";

        ResponseEntity<PostsResponse> response = restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<>(requestHeaders), PostsResponse.class);

        PostsResponse body = response.getBody();
        return body.getPosts();
    }

    @Override
    protected void onPostExecute(List<PostView> postViews) {
        super.onPostExecute(postViews);
        Home home = homeReference.get();

        home.postsView.setLayoutManager(new LinearLayoutManager(home));
        home.postsView.setAdapter(new MiniPostAdapter(home, postViews));

    }
}
