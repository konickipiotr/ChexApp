package com.chex.modules.posts.async;

import android.os.AsyncTask;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.modules.posts.adapters.PhotoAdapter;
import com.chex.modules.posts.adapters.PlaceAdapter;
import com.chex.modules.posts.PostActivity;
import com.chex.modules.posts.adapters.AchievementAdatper;
import com.chex.modules.posts.adapters.CommentAdapter;
import com.chex.modules.posts.listeners.AddCommentListener;
import com.chex.modules.posts.listeners.ChangeStarListener;
import com.chex.modules.posts.listeners.DeletePostListener;
import com.chex.modules.posts.model.PostPhoto;
import com.chex.modules.posts.model.PostView;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.ref.WeakReference;
import java.util.List;

public class GetPostAsync extends AsyncTask<Long, Void, PostView>  {

    private final WeakReference<PostActivity> postReference;

    public GetPostAsync(PostActivity postActivity) {
        this.postReference = new WeakReference<>(postActivity);
    }

    @Override
    protected PostView doInBackground(Long... longs) {
        HttpRequestUtils requestUtils = new HttpRequestUtils();
        HttpHeaders requestHeaders = requestUtils.getRequestHeaders();
        RestTemplate restTemplate = requestUtils.getRestTemplate();
        String path = Settings.ROOT_PATH + "/post/" + longs[0];

        ResponseEntity<PostView> response = restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<>(requestHeaders), PostView.class);
        return response.getBody();
    }

    @Override
    protected void onPostExecute(PostView postView) {
        PostActivity activity = postReference.get();

        activity.tv_authorName.setText(postView.getAuthorName());
        activity.tv_postDate.setText(postView.getCreatedAt());

        if(postView.getAuthorPhoto() == null || postView.getAuthorPhoto().isEmpty()){
            Glide.with(activity)
                    .load(ContextCompat.getDrawable(activity, R.drawable.user))
                    .circleCrop()
                    .into(activity.authorPhoto);
        }else {
            Glide.with(activity)
                    .load(Settings.domain + postView.getAuthorPhoto())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                    .into(activity.authorPhoto);
        }


        activity.placesView.setLayoutManager(new LinearLayoutManager(activity));
        activity.subplacesView.setLayoutManager(new LinearLayoutManager(activity));
        activity.commentsView.setLayoutManager(new LinearLayoutManager(activity));
        activity.achivmentsView.setLayoutManager(new LinearLayoutManager(activity));

        activity.placesView.setAdapter(new PlaceAdapter(activity, postView.getPlaces()));

        if(postView.getAuthorId().equals(Settings.user.getId())){
            activity.removePostBtn.setOnClickListener(new DeletePostListener(activity, postView.getId()));
            activity.removePostBtn.setVisibility(View.VISIBLE);
        }

        if(!postView.getSubPlaces().isEmpty()){
            activity.subplacesView.setAdapter(new PlaceAdapter(activity, postView.getSubPlaces()));
            activity.tv_subplaceinfo.setVisibility(View.VISIBLE);
            activity.subplacesView.setVisibility(View.VISIBLE);
        }

        if(!postView.getAchievements().isEmpty()){
            activity.achivmentsView.setVisibility(View.VISIBLE);
            activity.tv_achievementLabel.setVisibility(View.VISIBLE);
            activity.achivmentsView.setAdapter(new AchievementAdatper(activity, postView.getAchievements())); //TODO
        }

        List<PostPhoto> photos = postView.getPhotos();
        if(!photos.isEmpty()) {
            activity.photoView.setAdapter(new PhotoAdapter(photos, activity));
        }

        activity.tv_description.setText(postView.getDescription());


        activity.starOff.setOnClickListener(new ChangeStarListener(activity.starOff,activity.starOn, activity.tv_starnum, postView.getId()));
        activity.starOn.setOnClickListener(new ChangeStarListener(activity.starOff,activity.starOn, activity.tv_starnum, postView.getId()));
        activity.tv_starnum.setText(String.valueOf(postView.getStanum()));
        if(postView.isVoted()){
            activity.starOn.setVisibility(View.VISIBLE);
            activity.starOff.setVisibility(View.GONE);
        }else {
            activity.starOn.setVisibility(View.GONE);
            activity.starOff.setVisibility(View.VISIBLE);
        }

        activity.tv_commentNum.setText(String.valueOf(postView.getCommentViews().size()));
        activity.addComment.setOnClickListener(new AddCommentListener(postView.getId(), activity, activity.newcomment));

        if(!postView.getCommentViews().isEmpty()){
            activity.commentsView.setAdapter(new CommentAdapter(activity, postView.getCommentViews(), false));
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
