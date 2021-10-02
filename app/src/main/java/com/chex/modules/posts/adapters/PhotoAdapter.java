package com.chex.modules.posts.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.modules.posts.model.PostPhoto;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private final List<PostPhoto> photos;
    private final Activity activity;

    public PhotoAdapter(List<PostPhoto> photos, Activity activity) {
        this.photos = photos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.post_element_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PostPhoto postPhoto = photos.get(position);
        Glide.with(activity).load(Settings.domain + postPhoto.getWebAppPath()).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.element_photo);
        }
    }
}
