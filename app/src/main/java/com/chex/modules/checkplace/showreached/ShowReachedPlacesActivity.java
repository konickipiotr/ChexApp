package com.chex.modules.checkplace.showreached;

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
import android.widget.TextView;

import com.chex.Home;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.modules.Achievements.AchievementShortView;
import com.chex.modules.checkplace.AchievedPlaceDTO;
import com.chex.modules.checkplace.CheckPlaceView;
import com.chex.modules.checkplace.addphoto.AddPlacePhotoActivity;
import com.chex.utils.MenuHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowReachedPlacesActivity extends AppCompatActivity {

    private RecyclerView recyclerView, achievementRV;
    private Button bCancel, bNext;
    private LocalDateTime timestamp;
    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reached_places);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.chex_action_bar);
        timestamp = LocalDateTime.now();
        header = findViewById(R.id.tvHeader);

        if(Settings.user.getGender().equals("M"))
            header.setText(getResources().getText(R.string.you_visited_male));
        else
            header.setText(getResources().getText(R.string.you_visited_female));

        List<CheckPlaceView> list = (List<CheckPlaceView>) getIntent().getSerializableExtra("places");
        List<AchievementShortView> alist = (List<AchievementShortView>) getIntent().getSerializableExtra("achievements");

        recyclerView = findViewById(R.id.reachedPlace_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShowReachedAdapter adapter = new ShowReachedAdapter(list, this);
        recyclerView.setAdapter(adapter);

        achievementRV = findViewById(R.id.reachedPlace_achievementView);
        achievementRV.setLayoutManager(new LinearLayoutManager(this));
        ShowAchievementAdapter achAdapter = new ShowAchievementAdapter(alist, this);
        achievementRV.setAdapter(achAdapter);


        bCancel = findViewById(R.id.showreachedplaces_bCancel);
        bNext = findViewById(R.id.showreachedplaces_bNext);

        bCancel.setOnClickListener(v -> {
            Intent intent = new Intent(this, Home.class);
            finish();
            startActivity(intent);
        });

        bNext.setOnClickListener(v -> {
            AchievedPlaceDTO dto = new AchievedPlaceDTO();
            Map<String, Integer> places = new HashMap<>();

            for(CheckPlaceView cp :  list){
                places.put(cp.getId(), cp.getUserrating());
            }
            dto.setAchievedPlaces(places);
            dto.setTimestamp(this.timestamp);

            Intent intent = new Intent(this, AddPlacePhotoActivity.class);
            intent.putExtra("dto", dto);
            finish();
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chex_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean menu = MenuHandler.menu(item, this, ShowReachedPlacesActivity.this);
        if(!menu)
            return super.onOptionsItemSelected(item);
        return menu;
    }
}