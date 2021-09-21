package com.chex.modules.checkplace.addphoto;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chex.R;

public class TouchedPhotoToRemoveListener implements View.OnTouchListener {

    private AddPlacePhotoActivity activity;
    private int idx = 1;

    public TouchedPhotoToRemoveListener(AddPlacePhotoActivity activity, int idx) {
        this.activity = activity;
        this.idx = idx;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            ImageButton removeBtn = activity.findViewById(R.id.bRemovePhoto);
            removeBtn.setVisibility(View.VISIBLE);
            this.activity.selectedPhotoIndex = idx;
            ImageView iv = this.activity.getPhotosList().get(idx);
            boolean alreadySelected = iv.getPaddingLeft() == 5;

            for(ImageView i : this.activity.getPhotosList()){
                i.setPadding(0,0,0,0);
                i.setBackgroundColor(Color.WHITE);
            }

            if(!alreadySelected) {
                iv.setBackgroundColor(Color.RED);
                iv.setPadding(5, 5, 5, 5);
            }else {
                activity.unselect();
            }
        }
        return false;
    }
}
