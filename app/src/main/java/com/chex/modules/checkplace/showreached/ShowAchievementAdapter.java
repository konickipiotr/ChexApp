package com.chex.modules.checkplace.showreached;

import android.app.Activity;
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

public class ShowAchievementAdapter extends RecyclerView.Adapter<ShowAchievementAdapter.ViewHolder> {


    private final List<AchievementShortView> list;
    private final Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView placeName, label, points;
        private final ImageView photo;

        public ViewHolder(@NonNull View view) {
            super(view);

            placeName = view.findViewById(R.id.checkelement_name);
            photo = view.findViewById(R.id.checkelement_photo);
            label = view.findViewById(R.id.checkelement_label);
            points = view.findViewById(R.id.checkelement_points);
        }
    }

    public ShowAchievementAdapter(List<AchievementShortView> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.reached_place_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AchievementShortView viewModel = list.get(position);

        if(Settings.user.getGender().equals("M"))
            holder.label.setText(activity.getResources().getText(R.string.you_achieved_female));
        else
            holder.label.setText(activity.getResources().getText(R.string.you_achieved_female));

        holder.placeName.setText(viewModel.getName());
        Glide.with(activity).load(Settings.domain + viewModel.getImg()).circleCrop().into(holder.photo);
        holder.points.setText(String.valueOf(viewModel.getPoints()));

    }
}
