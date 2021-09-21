package com.chex.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    private static final int LONGER_EDGE_SIZE = 2500;

    public static File createImageFile(Activity context) throws IOException {
        // Create an image file name  /storage/emulated/0/Android/data/com.myweddi/files/Pictures
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return file;
    }

    public static Bitmap getBitmapFromStorage(Uri uri, Activity mContext){
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = mContext.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
            bitmap = compressBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap compressBitmap(Bitmap bitmap){
        if(bitmap == null)
            return null;

        int longer = getLongerEdge(bitmap);

        while (longer > LONGER_EDGE_SIZE){
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
