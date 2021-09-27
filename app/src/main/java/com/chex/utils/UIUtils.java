package com.chex.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chex.config.Settings;

public class UIUtils {

    public static void loadProfilePhoto(ImageView imageView, Activity activity){
        Glide.with(activity)
                .load(Settings.domain + Settings.user.getImgurl())
                .circleCrop()
                .into(imageView);
    }
}
