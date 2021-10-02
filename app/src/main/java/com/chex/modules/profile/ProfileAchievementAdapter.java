package com.chex.modules.profile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.modules.Achievements.AchievementView;
import com.chex.modules.place.CompleteStatus;

import java.util.List;

public class ProfileAchievementAdapter extends RecyclerView.Adapter<ProfileAchievementAdapter.ViewHolder> {

    private final List<AchievementView> list;
    private final Activity activity;

    public ProfileAchievementAdapter(List<AchievementView> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.profile_place_achievement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AchievementView achievementView = list.get(position);

        holder.achName.setText(achievementView.getName());
        holder.usersAchieved.setText(String.valueOf(achievementView.getUsersReached()));
        holder.points.setText(String.valueOf(achievementView.getPoints()));
        Glide.with(activity).load(Settings.domain + achievementView.getImg()).circleCrop().into(holder.achImg);

        if(achievementView.getCompleteStatus().equals(CompleteStatus.INPROGRESS)){
            holder.achievedPlacesNum.setText(String.valueOf(achievementView.getUserPlacesNum()));
            holder.achievedPlacesNum.setVisibility(View.VISIBLE);
            holder.allPlacesNum.setText(String.valueOf(achievementView.getAllPlacesNum()));
            holder.allPlacesNum.setVisibility(View.VISIBLE);
            holder.toAchieve.setMin(0);
            holder.toAchieve.setMax(achievementView.getAllPlacesNum());
            holder.toAchieve.setProgress(achievementView.getUserPlacesNum());
            holder.toAchieve.setVisibility(View.VISIBLE);

        }else {
            if(achievementView.getAchievedAt() != null) {
                String day = String.valueOf(achievementView.getAchievedAt().getDayOfMonth());
                String mon = String.valueOf(achievementView.getAchievedAt().getMonthValue());
                String year = String.valueOf(achievementView.getAchievedAt().getYear());

                String date = day + "." + mon + "." + year;
                holder.reachedAt.setText(date);
                holder.reachedAt.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView achName, reachedAt, usersAchieved, points, achievedPlacesNum, allPlacesNum;
        private final ImageView achImg;
        private final ProgressBar toAchieve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            achImg = itemView.findViewById(R.id.profleelement_img);
            achName = itemView.findViewById(R.id.profileelement_placename);
            reachedAt = itemView.findViewById(R.id.profileelement_date);
            usersAchieved = itemView.findViewById(R.id.profileelemnt_users_achieved);
            points = itemView.findViewById(R.id.profileelement_points);
            achievedPlacesNum = itemView.findViewById(R.id.profileelement_achieved_num);
            allPlacesNum = itemView.findViewById(R.id.profileelement_ach_all);
            toAchieve = itemView.findViewById(R.id.profileelement_achievement_bar);
        }
    }

}
