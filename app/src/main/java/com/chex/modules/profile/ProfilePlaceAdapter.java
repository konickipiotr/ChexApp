package com.chex.modules.profile;

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
import com.chex.modules.place.PlaceView;

import java.util.List;

public class ProfilePlaceAdapter extends RecyclerView.Adapter<ProfilePlaceAdapter.ViewHolder> {

    private final List<PlaceView> list;
    private final Activity activity;

    public ProfilePlaceAdapter(List<PlaceView> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.profile_place_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaceView placeView = list.get(position);

        Glide.with(activity).load(Settings.domain + placeView.getImg()).circleCrop().into(holder.placeImg);
        holder.placeName.setText(placeView.getName());
        if(placeView.getAchievedAt() != null){
            String day = Integer.toString(placeView.getAchievedAt().getDayOfMonth());
            String mon = Integer.toString(placeView.getAchievedAt().getMonthValue());
            String year = Integer.toString(placeView.getAchievedAt().getYear());

            String date = day + "." + mon + "." + year;
            holder.reachedAt.setText(date);
        }


        holder.usersAchieved.setText(String.valueOf(placeView.getUsersReached()));
        holder.usersGrade.setText(String.valueOf(placeView.getPlaceRating()));
        String userRating = "(" + placeView.getUserRating() + ")";
        holder.userGrade.setText(userRating);
        holder.points.setText(String.valueOf(placeView.getPoints()));



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView placeName, reachedAt, usersAchieved, usersGrade, userGrade, points;
        private final ImageView placeImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImg = itemView.findViewById(R.id.profleelement_img);
            placeName = itemView.findViewById(R.id.profileelement_placename);
            reachedAt = itemView.findViewById(R.id.profileelement_date);
            usersAchieved = itemView.findViewById(R.id.profileelemnt_users_achieved);
            userGrade = itemView.findViewById(R.id.profileelement_usergrade);
            usersGrade = itemView.findViewById(R.id.profileelement_usersgrade);
            points = itemView.findViewById(R.id.profileelement_points);
        }
    }
}
