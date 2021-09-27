package com.chex.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import com.chex.utils.exceptions.WrongPhotoTypeException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {



    public static File createImageFile(Activity context, PhotoType photoType) throws IOException {
        // Create an image file name  /storage/emulated/0/Android/data/com.chex/files/Pictures
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "";

        switch (photoType){
            case POST: imageFileName = "JPEG_" + timeStamp + "_";break;
            case PROFILE: imageFileName = "profilePhoto";break;
            default: throw new WrongPhotoTypeException();
        }
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return file;
    }

    public static Bitmap getBitmapFromStorage(Uri uri, Activity mContext, int longerEdgeSize){
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = mContext.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
            bitmap = compressBitmap(bitmap, longerEdgeSize);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap compressBitmap(Bitmap bitmap, int longerEdgeSize){
        if(bitmap == null)
            return null;

        int longer = getLongerEdge(bitmap);

        while (longer > longerEdgeSize){
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            width -= (int) (width * 0.1f);
            height -= (int) (height * 0.1f);

            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            longer = getLongerEdge(bitmap);
        }
        return bitmap;
    }

    private static int getLongerEdge(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        return width > height ? width : height;
    }

    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        byte[] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
