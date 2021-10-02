package com.chex.modules.checkplace.showreached;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.modules.checkplace.CheckPlaceView;

import java.util.List;

public class ShowReachedAdapter extends RecyclerView.Adapter<ShowReachedAdapter.ViewHolder> {

    private final List<CheckPlaceView> list;
    private final Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView placeName, label, points, ratinglabel;
        private final ImageView photo;
        private final RatingBar ratingBar;

        public ViewHolder(View view){
            super(view);
            placeName = view.findViewById(R.id.checkelement_name);
            photo = view.findViewById(R.id.checkelement_photo);
            label = view.findViewById(R.id.checkelement_label);
            points = view.findViewById(R.id.checkelement_points);
            ratingBar = view.findViewById(R.id.place_rating);
            ratinglabel = view.findViewById(R.id.checkelement_ratinglabel);
        }

        public TextView getPlaceName() {
            return placeName;
        }
    }

    public ShowReachedAdapter(List<CheckPlaceView> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.reached_place_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckPlaceView placeView = list.get(position);

        if(Settings.user.getGender().equals("M"))
            holder.label.setText(context.getResources().getText(R.string.you_visited_female));
        else
            holder.label.setText(context.getResources().getText(R.string.you_visited_female));

        holder.placeName.setText(placeView.getName());
        Glide.with(context).load(Settings.domain + placeView.getImage()).circleCrop().into(holder.photo);
        holder.points.setText(String.valueOf(placeView.getPoints()));

        String id = placeView.getId();
        if(!id.endsWith(".00000")){
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.ratinglabel.setVisibility(View.VISIBLE);
            holder.ratingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> {
                if(b){
                    placeView.setUserrating((int)v);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
