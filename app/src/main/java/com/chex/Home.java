package com.chex;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chex.config.Settings;
import com.chex.modules.posts.GetPostsAsync;
import com.chex.modules.posts.PostUpdater;
import com.chex.modules.checkplace.CheckPlaceView;
import com.chex.modules.checkplace.LocationCatcher;
import com.chex.utils.MenuHandler;
import com.chex.utils.UIUtils;
import com.chex.utils.UserUpdater;

import java.util.List;

public class Home extends AppCompatActivity implements PostUpdater, ProfilePhotoUpdater {

    public RecyclerView postsView;
    public Button checkBtn;
    public ImageView profilePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.chex_action_bar);

        profilePhoto = findViewById(R.id.profilePhoto);
        postsView = findViewById(R.id.postsView);
        checkBtn = findViewById(R.id.chech_btn);
        postsView.setLayoutManager(new LinearLayoutManager(this));


        List<CheckPlaceView> list = (List<CheckPlaceView>) getIntent().getSerializableExtra("places");
        if(list != null){
            CheckPlaceView checkPlaceView = (CheckPlaceView)list.get(0);
        }

        //updatePosts();




        checkBtn.setOnClickListener(v -> {
            Location location = null;
            LocationCatcher ll = new LocationCatcher(Home.this);
            ll.startLocationUpdates();
        });
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
}