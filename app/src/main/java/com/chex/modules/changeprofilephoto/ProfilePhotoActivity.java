package com.chex.modules.changeprofilephoto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.utils.FileUtils;
import com.chex.utils.LongerEdge;
import com.chex.utils.PhotoType;
import com.chex.utils.UserUpdater;

import java.io.File;
import java.io.IOException;

public class ProfilePhotoActivity extends AppCompatActivity {

    private ImageButton takePhoto, fromGallery, deletePhoto;
    private Button cancel, next;
    private ImageView profilePhoto;
    private Uri tempUri;
    private Bitmap newPhotoBitmap;
    private boolean deleteProfilePhoto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.chex_action_bar);

        init();
        new UserUpdater().setPhoto(this, profilePhoto);

        if(ContextCompat.checkSelfPermission(ProfilePhotoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ProfilePhotoActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, Settings.CAMERA_SRC);
        }
    }

    private void init(){
        takePhoto = findViewById(R.id.makeprofilephoto_take_picture);
        fromGallery = findViewById(R.id.makeprofilephoto_from_gallery);
        deletePhoto = findViewById(R.id.makeprofilephoto_delete_photo);
        cancel = findViewById(R.id.makeprofilephoto_cancel);
        next = findViewById(R.id.makeprofilephoto_next);
        profilePhoto = findViewById(R.id.makeprofilephoto_photo);

        cancel.setOnClickListener(v -> finish());

        deletePhoto.setOnClickListener(v -> {
            Glide.with(this).load(ContextCompat.getDrawable(this, R.drawable.user)).circleCrop().into(profilePhoto);
            deleteProfilePhoto = true;
        });

        takePhoto.setOnClickListener(v -> {
            deleteProfilePhoto = false;
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = FileUtils.createImageFile(this, PhotoType.PROFILE);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.chex.fileprovider",
                            photoFile);

                    tempUri = photoURI;
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, Settings.CAMERA_SRC);
                }
            }
        });

        fromGallery.setOnClickListener(v -> {
            deleteProfilePhoto = false;
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, Settings.STORAGE_SRC);
        });

        next.setOnClickListener(v -> {
            if(deleteProfilePhoto){
                Settings.user.setImgurl("");
                Settings.profilePhotoBitmap = null;
                new DeleteProfilePhotoAsync().execute();
            }else {
                if(newPhotoBitmap == null){
                    Toast.makeText(this, "Nie wybrano zdjęcia", Toast.LENGTH_LONG).show();
                    return;
                }

                Settings.profilePhotoBitmap = newPhotoBitmap;
                String stringPhoto = FileUtils.bitmapToString(newPhotoBitmap);
                new SendNewProfilePhotoAsync().execute(stringPhoto);
            }

            finish();

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(Settings.CAMERA_SRC != requestCode && Settings.STORAGE_SRC != requestCode){
            return;
        }

        if(requestCode == Settings.CAMERA_SRC){
            newPhotoBitmap = FileUtils.getBitmapFromStorage(tempUri, ProfilePhotoActivity.this, LongerEdge.SMALL);
            if(newPhotoBitmap == null) return;

        }else if(requestCode == Settings.STORAGE_SRC){
            if(data == null) return;
            Uri selectedImageUri = data.getData();
            newPhotoBitmap = FileUtils.getBitmapFromStorage(selectedImageUri, ProfilePhotoActivity.this, LongerEdge.SMALL);
            if(newPhotoBitmap == null) return;
        }

        profilePhoto.setImageBitmap(newPhotoBitmap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new UserUpdater().setUserInfoBar(this);
    }
}