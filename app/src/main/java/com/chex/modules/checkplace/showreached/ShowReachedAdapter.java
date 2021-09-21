package com.chex.modules.checkplace.showreached;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chex.R;
import com.chex.modules.checkplace.CheckPlaceView;

import java.util.List;

public class ShowReachedAdapter extends RecyclerView.Adapter<ShowReachedAdapter.ViewHolder> {

    private List<CheckPlaceView> list;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView placeName;

        public ViewHolder(View view){
            super(view);
            placeName = view.findViewById(R.id.placeName);
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
        holder.getPlaceName().setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
