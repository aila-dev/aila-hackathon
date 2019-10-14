package com.aila.ailahackathon.inspector;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FaceDetection {

    public static String mCurrentPhotoPath;
    private static final String TAG = "FaceDetection";

    /*public static void onImageFromCamera(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, initiate request
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            // Create an image file
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.
                    getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            try {
                File image = File.createTempFile(
                        imageFileName, // prefix
                        ".jpg", //suffix
                        storageDir // directory
                );
            } catch (IOException e) {
                Log.d("ERR_IMG_CAPTURE", e.getMessage());
            }
        }
            mCurrentPhotoPath = image.getAbsolutePath(); // save this to use in the intent
            return image;
    }*/


    public void detectFace(Bitmap bitmap, Context context) {
        final com.google.android.gms.vision.face.FaceDetector detector =
                new com.google.android.gms.vision.face.FaceDetector.Builder(context)
                        .setTrackingEnabled(false)
                        .setLandmarkType(com.google.android.gms.vision.face.FaceDetector.ALL_LANDMARKS)
                        .build();

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faces = detector.detect(frame);


    }
}
