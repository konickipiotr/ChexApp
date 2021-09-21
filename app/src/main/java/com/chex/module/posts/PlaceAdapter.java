package com.chex.module.posts;

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
import com.chex.module.posts.model.PlaceShortView;
import com.chex.module.posts.model.PostView;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private Context context;
    private List<PlaceShortView> places;

    public PlaceAdapter(Context context, List<PlaceShortView> places) {
        this.context = context;
        this.places = places;
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
        PlaceShortView placeShortView = places.get(position);
        holder.placeName.setText(placeShortView.getName());
        Glide.with(context).load(Settings.domain + placeShortView.getImgUrl()).circleCrop().into(holder.placeImg);
    }

    @Override
    public int getItemCount() {
        return places.size();
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
