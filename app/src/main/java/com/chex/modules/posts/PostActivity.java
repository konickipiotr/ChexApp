package com.chex.modules.posts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chex.R;
import com.chex.modules.posts.async.GetPostAsync;
import com.chex.utils.MarginItemDecoration;

public class PostActivity extends AppCompatActivity implements PostUpdater{

    public TextView tv_authorName, tv_postDate, tv_subplaceinfo, tv_achievementLabel, tv_description, tv_commentNum, tv_starnum;
    public ImageView authorPhoto;
    public RecyclerView placesView, achivmentsView, subplacesView, commentsView, photoView;
    public ImageButton removePostBtn, addComment, starOn, starOff;
    public EditText newcomment;
    private Long postid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.chex_action_bar);
        init();

        postid = getIntent().getLongExtra("postid", -1);
        if(postid == -1)
            throw new IllegalArgumentException();


        new GetPostAsync(this).execute(postid);

    }

    private void init(){
        tv_authorName = findViewById(R.id.post_authorname);
        authorPhoto = findViewById(R.id.post_authorphoto);
        tv_postDate = findViewById(R.id.post_postdate);
        placesView = findViewById(R.id.postelement_places);
        subplacesView = findViewById(R.id.postelement_subplaces);
        tv_subplaceinfo = findViewById(R.id.postelement_subregionsinfo);
        commentsView = findViewById(R.id.comment_view);
        achivmentsView = findViewById(R.id.postelement_achievements);
        tv_achievementLabel = findViewById(R.id.post_achievement_label);
        tv_description = findViewById(R.id.post_description);
        //firstPhoto = findViewById(R.id.minipost_firstimg);
        removePostBtn = findViewById(R.id.post_removepost);
        newcomment = findViewById(R.id.post_newcomment);
        addComment = findViewById(R.id.post_addcomment);
        starOn = findViewById(R.id.post_star_on);
        starOff = findViewById(R.id.post_star_off);
        tv_commentNum = findViewById(R.id.post_comment_num);
        tv_starnum = findViewById(R.id.post_star_num);
        photoView = findViewById(R.id.postelement_photos);
        photoView.setLayoutManager(new LinearLayoutManager(this));
        photoView.addItemDecoration(new MarginItemDecoration(10));
    }

    @Override
    public void updatePosts() {
        new GetPostAsync(this).execute(postid);
    }
}