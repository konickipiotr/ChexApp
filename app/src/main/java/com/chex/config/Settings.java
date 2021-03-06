package com.chex.config;

import android.graphics.Bitmap;

import com.chex.user.User;

public class Settings {

    public static String username;
    public static String password;
    public static User user;
    public static Bitmap profilePhotoBitmap;

    //public static String domain = "http://80.211.245.217:8080";
    public static String domain = "http://10.0.2.2:8080";
    public static String ROOT_PATH = Settings.domain + "/api";

    public static void clearData(){
        username = "";
        password = "";
        user = null;
    }

    public static final int CAMERA_SRC = 100;
    public static final int STORAGE_SRC = 200;
}
