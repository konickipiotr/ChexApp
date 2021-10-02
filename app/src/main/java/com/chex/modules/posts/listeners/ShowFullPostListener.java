package com.chex.modules.posts.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chex.modules.posts.PostActivity;

public class ShowFullPostListener implements View.OnClickListener {

    private final Long postid;
    private final Activity activity;

    public ShowFullPostListener(Long postid, Activity activity) {
        this.postid = postid;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(activity, PostActivity.class);
        intent.putExtra("postid", postid);
        activity.startActivity(intent);
    }
}
