package com.chex.modules.posts;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.modules.posts.listeners.AddCommentListener;
import com.chex.modules.posts.listeners.ChangeStarListener;
import com.chex.modules.posts.listeners.DeletePostListener;
import com.chex.modules.posts.model.PostPhoto;
import com.chex.modules.posts.model.PostView;

import java.io.FileNotFoundException;
import java.util.List;

public class MiniPostAdapter extends RecyclerView.Adapter<MiniPostAdapter.ViewHolder> implements ItemRemover {

    private final Activity activity;
    private final List<PostView> posts;

    public MiniPostAdapter(Activity activity, List<PostView> posts) {
        this.activity = activity;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.minipost_element, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostView postView = posts.get(position);

        holder.tv_authorName.setText(postView.getAuthorName());
        holder.tv_postDate.setText(postView.getCreatedAt());

        Glide.with(activity)
                .load(Settings.domain + postView.getAuthorPhoto())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                .into(holder.authorPhoto);


        holder.placesView.setLayoutManager(new LinearLayoutManager(activity));
        holder.subplacesView.setLayoutManager(new LinearLayoutManager(activity));
        holder.commentsView.setLayoutManager(new LinearLayoutManager(activity));
        holder.achivmentsView.setLayoutManager(new LinearLayoutManager(activity));

        holder.placesView.setAdapter(new PlaceAdapter(activity, postView.getPlaces()));

        if(postView.getAuthorId().equals(Settings.user.getId())){
            holder.removePostBtn.setOnClickListener(new DeletePostListener(activity, postView.getId(), this, position));
            holder.removePostBtn.setVisibility(View.VISIBLE);
        }

        if(!postView.getSubPlaces().isEmpty()){
            holder.subplacesView.setAdapter(new PlaceAdapter(activity, postView.getSubPlaces()));
            holder.tv_subplaceinfo.setVisibility(View.VISIBLE);
            holder.subplacesView.setVisibility(View.VISIBLE);
        }

        if(!postView.getAchievements().isEmpty()){
            holder.achivmentsView.setVisibility(View.VISIBLE);
            holder.tv_achievementLabel.setVisibility(View.VISIBLE);
            //holder.achivmentsView.setAdapter(); //TODO
        }

        List<PostPhoto> photos = postView.getPhotos();
        if(!photos.isEmpty()) {
            Glide.with(activity).load(Settings.domain + photos.get(0).getWebAppPath()).into(holder.firstPhoto);
        }

        holder.starOff.setOnClickListener(new ChangeStarListener(holder.starOff,holder.starOn, holder.tv_starnum, postView.getId()));
        holder.starOn.setOnClickListener(new ChangeStarListener(holder.starOff,holder.starOn, holder.tv_starnum, postView.getId()));
        holder.tv_starnum.setText(String.valueOf(postView.getStanum()));
        if(postView.isVoted()){
            holder.starOn.setVisibility(View.VISIBLE);
            holder.starOff.setVisibility(View.GONE);
        }else {
            holder.starOn.setVisibility(View.GONE);
            holder.starOff.setVisibility(View.VISIBLE);
        }

        holder.tv_commentNum.setText(String.valueOf(postView.getCommentViews().size()));
        holder.addComment.setOnClickListener(new AddCommentListener(postView.getId(), activity, holder.newcomment));

        if(!postView.getCommentViews().isEmpty()){
            holder.commentsView.setAdapter(new CommentAdapter(activity, postView.getCommentViews()));
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public void removeItem(int position) {
        posts.remove(position);
        notifyItemRemoved(position);
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_authorName, tv_postDate, tv_subplaceinfo, tv_achievementLabel, tv_description, tv_commentNum, tv_starnum;
        public ImageView authorPhoto, firstPhoto;
        public RecyclerView placesView;
        public RecyclerView subplacesView;
        public RecyclerView achivmentsView;
        public RecyclerView commentsView;
        public ImageButton removePostBtn, addComment, starOn, starOff;
        public EditText newcomment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_authorName = itemView.findViewById(R.id.minipost_authorname);
            authorPhoto = itemView.findViewById(R.id.minpost_authorphoto);
            tv_postDate = itemView.findViewById(R.id.minitpost_postdate);
            placesView = itemView.findViewById(R.id.minipostelement_places);
            subplacesView = itemView.findViewById(R.id.minipostelement_subplaces);
            tv_subplaceinfo = itemView.findViewById(R.id.minipostelement_subregionsinfo);
            commentsView = itemView.findViewById(R.id.comment_view);
            achivmentsView = itemView.findViewById(R.id.postelement_achievements);
            tv_achievementLabel = itemView.findViewById(R.id.minipost_achievement_label);
            tv_description = itemView.findViewById(R.id.minipost_description);
            firstPhoto = itemView.findViewById(R.id.minipost_firstimg);
            removePostBtn = itemView.findViewById(R.id.minipost_removepost);
            newcomment = itemView.findViewById(R.id.minipost_newcomment);
            addComment = itemView.findViewById(R.id.minipost_addcomment);
            starOn = itemView.findViewById(R.id.post_star_on);
            starOff = itemView.findViewById(R.id.post_star_off);
            tv_commentNum = itemView.findViewById(R.id.minipost_comment_num);
            tv_starnum = itemView.findViewById(R.id.post_star_num);
        }
    }
}
