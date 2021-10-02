package com.chex;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.chex.modules.posts.async.GetPostsAsync;
import com.chex.modules.posts.PostUpdater;
import com.chex.modules.checkplace.LocationCatcher;
import com.chex.modules.profile.ProfileActivity;
import com.chex.utils.MenuHandler;
import com.chex.utils.UserUpdater;

import java.util.Objects;

public class Home extends AppCompatActivity implements PostUpdater, ProfilePhotoUpdater {

    public RecyclerView postsView;
    public Button checkBtn, profile_btn;
    public ImageView profilePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.chex_action_bar);
        init();


        postsView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        new UserUpdater().updateUserFromServer(this);
        updatePosts();
    }

    public void updatePosts(){
        new GetPostsAsync(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chex_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean menu = MenuHandler.menu(item, this, Home.this);
        if(!menu)
            return super.onOptionsItemSelected(item);
        return menu;
    }

    @Override
    public void updateProfilePhoto() {
        new UserUpdater().setUserInfoBar(this);
    }

    private void init(){
        profilePhoto = findViewById(R.id.profilePhoto);
        postsView = findViewById(R.id.postsView);
        checkBtn = findViewById(R.id.chech_btn);
        profile_btn = findViewById(R.id.profile_btn);

        checkBtn.setOnClickListener(v -> {
            LocationCatcher ll = new LocationCatcher(Home.this);
            ll.startLocationUpdates();
        });

        profile_btn.setOnClickListener(v -> startActivity(new Intent(Home.this, ProfileActivity.class)));
    }
}
