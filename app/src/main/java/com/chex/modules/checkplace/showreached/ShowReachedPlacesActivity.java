package com.chex.modules.checkplace.showreached;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.chex.Home;
import com.chex.R;
import com.chex.modules.checkplace.AchievedPlaceDTO;
import com.chex.modules.checkplace.CheckPlaceView;
import com.chex.modules.checkplace.addphoto.AddPlacePhotoActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowReachedPlacesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button bCancel, bNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reached_places);

        List<CheckPlaceView> list = (List<CheckPlaceView>) getIntent().getSerializableExtra("places");
        CheckPlaceView checkPlaceView = list.get(0);

        recyclerView = findViewById(R.id.reachedPlace_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShowReachedAdapter adapter = new ShowReachedAdapter(list, this);
        recyclerView.setAdapter(adapter);


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
                places.put(cp.getId(), 0);
            }
            dto.setAchievedPlaces(places);

            Intent intent = new Intent(this, AddPlacePhotoActivity.class);
            intent.putExtra("dto", (Serializable) dto);
            finish();
            startActivity(intent);
        });

    }
}