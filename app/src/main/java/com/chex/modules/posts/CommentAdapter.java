package com.chex.modules.posts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.modules.posts.listeners.DeleteCommentListener;
import com.chex.modules.posts.model.CommentView;
import com.chex.modules.posts.model.PlaceShortView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> implements ItemRemover {

    private Context context;
    private List<CommentView> comments;

    public CommentAdapter(Context context, List<CommentView> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.comment_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentView cv = comments.get(position);
        Glide.with(context)
                .load(Settings.domain + cv.getAuthorPhoto())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                .into(holder.commentAuthorImg);
        holder.commentAuthor.setText(cv.getAuthorName());
        holder.commentDate.setText(cv.getCreatedAt());
        holder.commentContent.setText(cv.getContent());
        if(cv.getAuthorid().equals(Settings.user.getId())){
            holder.removeCommentBtn.setOnClickListener(new DeleteCommentListener(cv.getId(), context, this, position));
            holder.removeCommentBtn.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return Math.min(comments.size(), 3);
    }

    @Override
    public void removeItem(int position) {
        comments.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView commentAuthorImg;
        TextView commentAuthor, commentDate, commentContent;
        ImageButton removeCommentBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commentAuthorImg = itemView.findViewById(R.id.comment_author_img);
            commentAuthor = itemView.findViewById(R.id.comment_author);
            commentDate = itemView.findViewById(R.id.comment_created_time);
            commentContent = itemView.findViewById(R.id.comment_content);
            removeCommentBtn = itemView.findViewById(R.id.remover_comment_btn);


        }
    }
}
