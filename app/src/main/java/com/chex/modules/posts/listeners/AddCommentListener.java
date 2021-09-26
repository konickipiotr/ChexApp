package com.chex.modules.posts.listeners;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import com.chex.config.Settings;
import com.chex.modules.posts.PostUpdater;
import com.chex.modules.posts.model.Comment;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

public class AddCommentListener implements View.OnClickListener {
    private Long postid;
    private Activity activity;
    private EditText commentEdit;

    public AddCommentListener(Long postid, Activity activity, EditText commentEdit) {
        this.postid = postid;
        this.activity = activity;
        this.commentEdit = commentEdit;
    }

    @Override
    public void onClick(View v) {
        String content = commentEdit.getText().toString();
        if(content.isEmpty())
            return;
        Comment comment = new Comment();
        comment.setAuthorid(Settings.user.getId());
        comment.setContent(content);
        comment.setCreated(LocalDateTime.now());
        comment.setPostid(postid);
        new AddCommentAsync().execute(comment);


    }

    class AddCommentAsync extends AsyncTask<Comment, Void, Void>{

        @Override
        protected Void doInBackground(Comment... comments) {
            Comment comment = comments[0];
            HttpRequestUtils requestUtils = new HttpRequestUtils();
            HttpHeaders requestHeaders = requestUtils.getRequestHeaders();
            RestTemplate restTemplate = requestUtils.getRestTemplate();
            String path = Settings.ROOT_PATH + "/post/comment/";
            restTemplate.postForEntity(path, new HttpEntity<>(comment, requestHeaders), Void.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            PostUpdater postUpdater = (PostUpdater) activity;
            postUpdater.updatePosts();
        }
    }
}
