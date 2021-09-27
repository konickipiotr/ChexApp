package com.chex.modules.checkplace.addphoto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.chex.Home;
import com.chex.R;
import com.chex.config.Settings;
import com.chex.modules.checkplace.AchievedPlaceDTO;
import com.chex.modules.checkplace.UploadPlaceAsync;
import com.chex.utils.FileUtils;
import com.chex.utils.LongerEdge;
import com.chex.utils.PhotoType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPlacePhotoActivity extends AppCompatActivity {


    private final int POST_LIMIT_PHOTOS = 9;
    private Uri tempUri;

    private Button bPublish, bCancel;
    private ImageButton bTakePhoto, bFromGallery, bDeletePhoto;
    private EditText description;
    private final List<ImageView> photosList = new ArrayList<>();
    private final List<Bitmap> bitmapList = new ArrayList<>();

    public int selectedPhotoIndex = -1;
    private AchievedPlaceDTO dto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_photo);
        dto = (AchievedPlaceDTO) getIntent().getSerializableExtra("dto");

        if(ContextCompat.checkSelfPermission(AddPlacePhotoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddPlacePhotoActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, Settings.CAMERA_SRC);
        }

        init();
        initListeners();
    }

    public List<ImageView> getPhotosList() {
        return photosList;
    }

    public void unselect(){
        for(ImageView i : this.photosList){
            i.setPadding(0,0,0,0);
            i.setBackgroundColor(Color.WHITE);
        }
        this.selectedPhotoIndex = -1;
        bDeletePhoto.setVisibility(View.GONE);
    }

    private void init(){
        bPublish = findViewById(R.id.bPublish);
        bCancel = findViewById(R.id.bCancel);
        bTakePhoto = findViewById(R.id.bTakePhoto);
        bFromGallery = findViewById(R.id.bFromGallery);
        bDeletePhoto = findViewById(R.id.bRemovePhoto);
        description = findViewById(R.id.description);
        photosList.add(findViewById(R.id.photo1));
        photosList.add(findViewById(R.id.photo2));
        photosList.add(findViewById(R.id.photo3));
        photosList.add(findViewById(R.id.photo4));
        photosList.add(findViewById(R.id.photo5));
        photosList.add(findViewById(R.id.photo6));
        photosList.add(findViewById(R.id.photo7));
        photosList.add(findViewById(R.id.photo8));
        photosList.add(findViewById(R.id.photo9));

        for(int i = 0; i < photosList.size(); i++){
            photosList.get(i).setVisibility(View.GONE);
            photosList.get(i).setOnTouchListener(new TouchedPhotoToRemoveListener(this, i));
        }
    }

    private void initListeners(){
        bFromGallery.setOnClickListener(v -> {
            unselect();

            if(bitmapList.size() == POST_LIMIT_PHOTOS){
                Toast.makeText(AddPlacePhotoActivity.this, "max 9 zdjęć", Toast.LENGTH_LONG).show();
                return;
            }

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, Settings.STORAGE_SRC);
        });


        bTakePhoto.setOnClickListener(v -> {

            unselect();

            if(bitmapList.size() == POST_LIMIT_PHOTOS){
                Toast.makeText(AddPlacePhotoActivity.this, "max 9 photos", Toast.LENGTH_LONG).show();
                return;
            }

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = FileUtils.createImageFile(this, PhotoType.POST);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
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

        bCancel.setOnClickListener(v ->{
            finish();
            startActivity(new Intent(this, Home.class));
        });

        bDeletePhoto.setOnClickListener(v ->{
            bitmapList.remove(selectedPhotoIndex);

            for(ImageView i : photosList){
                i.setImageBitmap(null);
                i.setPadding(0,0,0,0);
                i.setBackgroundColor(Color.WHITE);
                i.setVisibility(View.GONE);
            }
            photosList.forEach(i -> i.setImageBitmap(null));

            for(int i = 0; i < bitmapList.size(); i++){
                photosList.get(i).setImageBitmap(bitmapList.get(i));
                photosList.get(i).setVisibility(View.VISIBLE);
            }
            bDeletePhoto.setVisibility(View.GONE);
        });

        bPublish.setOnClickListener(v ->{

            if(bitmapList != null && !bitmapList.isEmpty()){
                for(int i = 0; i< bitmapList.size(); i++){
                    String stringBytes = FileUtils.bitmapToString(bitmapList.get(i));
                    dto.addSFile(stringBytes);
                }
            }

            dto.setDescription(description.getText().toString());
            new UploadPlaceAsync(this).execute(dto);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int size = bitmapList.size();
        int idx = size;
        if(Settings.CAMERA_SRC == requestCode || Settings.STORAGE_SRC == requestCode){
            size++;
            if(size > POST_LIMIT_PHOTOS){
                return;
            }
        }

        if(requestCode == Settings.CAMERA_SRC){
            Bitmap bitmapFromStorage = FileUtils.getBitmapFromStorage(tempUri, AddPlacePhotoActivity.this, LongerEdge.LARGE);
            if(bitmapFromStorage == null) return;
            bitmapList.add(bitmapFromStorage);

            photosList.get(idx).setImageBitmap(bitmapFromStorage);
            photosList.get(idx).setVisibility(View.VISIBLE);

        }else if(requestCode == Settings.STORAGE_SRC){
            if(data == null) return;
            Uri selectedImageUri = data.getData();
            Bitmap bitmapFromStorage = FileUtils.getBitmapFromStorage(selectedImageUri, AddPlacePhotoActivity.this, LongerEdge.LARGE);
            bitmapList.add(bitmapFromStorage);

            photosList.get(idx).setImageBitmap(bitmapFromStorage);
            photosList.get(idx).setVisibility(View.VISIBLE);
        }
    }
}
