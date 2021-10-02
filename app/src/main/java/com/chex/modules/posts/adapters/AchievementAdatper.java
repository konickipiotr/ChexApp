package com.chex.modules.posts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.modules.Achievements.AchievementShortView;

import java.util.List;

public class AchievementAdatper extends RecyclerView.Adapter<AchievementAdatper.ViewHolder> {

    private final Context context;
    private final List<AchievementShortView> achievements;

    public AchievementAdatper(Context context, List<AchievementShortView> achievements) {
        this.context = context;
        this.achievements = achievements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.post_element_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AchievementShortView achievementShortView = achievements.get(position);
        holder.placeName.setText(achievementShortView.getName());
        Glide.with(context).load(Settings.domain + achievementShortView.getImg()).circleCrop().into(holder.placeImg);
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView placeName;
        public ImageView placeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.row_place_name);
            placeImg = itemView.findViewById(R.id.row_place_img);
        }
    }
}
