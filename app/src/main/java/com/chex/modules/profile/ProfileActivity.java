package com.chex.modules.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chex.R;
import com.chex.config.Settings;

public class ProfileActivity extends AppCompatActivity {

    public TextView header, levelName, currentLevel,
            nextLevel, exp, place_last3_label, label_places,
            achi_label, achi_inprog_label, places_num, ach_num, friends_num;
    public ImageView photo, placeIco, achIco, friendIco;
    public RecyclerView lastThreeView, placesView, achView, achInPView;
    public ProgressBar exp_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        header.setText(Settings.user.getName());

        if(Settings.profilePhotoBitmap == null)
            Glide.with(this).load(ContextCompat.getDrawable(this, R.drawable.user)).circleCrop().into(photo);
        else
            Glide.with(this).load(Settings.profilePhotoBitmap).circleCrop().into(photo);
        currentLevel.setText(String.valueOf(Settings.user.getLevel()));
        nextLevel.setText(String.valueOf(Settings.user.getLevel()+1));
        levelName.setText(Settings.user.getTitle());
        exp.setText(String.valueOf(Settings.user.getExp()));
        exp_bar.setMin(0);
        exp_bar.setMax(Settings.user.getNextlevel());
        exp_bar.setProgress(Settings.user.getExp());

        new ProfileAsync(this).execute("all");

    }


    private void init(){
        header = findViewById(R.id.profie_header);
        photo = findViewById(R.id.profil_photo);
        levelName = findViewById(R.id.profile_levelname);
        currentLevel = findViewById(R.id.profile_currentLevel);
        nextLevel = findViewById(R.id.profile_nextLevel);
        exp = findViewById(R.id.profile_exp_points);
        places_num = findViewById(R.id.profile_place_num);
        ach_num = findViewById(R.id.profile_achievement_num);
        friends_num = findViewById(R.id.profile_friends_num);
        place_last3_label = findViewById(R.id.profile_last_three_visited_label);
        label_places = findViewById(R.id.profile_places);
        achi_label = findViewById(R.id.profile_achievements_label);
        achi_inprog_label = findViewById(R.id.profile_achievemet_in_progress_label);
        exp_bar = findViewById(R.id.profile_exp_bar);
        placeIco = findViewById(R.id.profile_place_img);
        achIco = findViewById(R.id.profile_achievement_img);
        friendIco = findViewById(R.id.profile_img_firends);
        lastThreeView = findViewById(R.id.profile_recyc_last_three);
        placesView = findViewById(R.id.profile_recyc_places);
        achView = findViewById(R.id.profile_recyc_achievements);
        achInPView = findViewById(R.id.profile_recyc_arch_in_progress);
    }
}