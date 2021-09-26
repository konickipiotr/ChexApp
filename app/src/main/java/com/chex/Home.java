package com.chex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Location;
import android.os.Bundle;
import android.widget.Button;

import com.chex.modules.posts.GetPostsAsync;
import com.chex.modules.posts.PostUpdater;
import com.chex.modules.checkplace.CheckPlaceView;
import com.chex.modules.checkplace.LocationCatcher;

import java.util.List;

public class Home extends AppCompatActivity implements PostUpdater {

    public RecyclerView postsView;
    public Button checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        postsView = findViewById(R.id.postsView);
        checkBtn = findViewById(R.id.chech_btn);
        postsView.setLayoutManager(new LinearLayoutManager(this));


        List<CheckPlaceView> list = (List<CheckPlaceView>) getIntent().getSerializableExtra("places");
        if(list != null){
            CheckPlaceView checkPlaceView = (CheckPlaceView)list.get(0);
        }

        updatePosts();




        checkBtn.setOnClickListener(v -> {
            Location location = null;
            LocationCatcher ll = new LocationCatcher(Home.this);
            ll.startLocationUpdates();
        });
    }

    public void updatePosts(){
        new GetPostsAsync(this).execute();
    }
}