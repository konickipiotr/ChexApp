package com.chex.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;

import com.chex.R;

public class MenuHandler {

    public static boolean menu(MenuItem item, Activity context, Context packageContext) {

//        switch (item.getItemId()) {
//            case R.id.:
//                context.finish();
//                context.startActivity(new Intent(packageContext, Home.class));
//                return true;
//            case R.id.bInfo:
//                context.startActivity(new Intent(packageContext, WeddingInfoActivity.class));
//                return true;
//            case R.id.bGifts:
//                context.startActivity(new Intent(packageContext, GiftsActivity.class));
//                return true;
//            case R.id.bTable:
//                context.startActivity(new Intent(packageContext, TableActivity.class));
//                return true;
//            case R.id.bLogout:
//                context.finish();
//                Settings.user = null;
//                Settings.guest = null;
//                Settings.profilePhotoBitmap = null;
//                Settings.username = null;
//                Settings.passoword = null;
//                context.startActivity(new Intent(packageContext, MainActivity.class));
//                SharedPreferences sp = context.getSharedPreferences("loginSaved", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("username", null);
//                editor.putString("password", null);
//                editor.commit();
//                return true;
//            case R.id.bOptions:
//                context.startActivity(new Intent(packageContext, SettingActivity.class));
//                return true;
//            default:
//                return false;
//        }
        return true;
    }
}