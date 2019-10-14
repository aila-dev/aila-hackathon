package com.aila.ailahackathon.inspector;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.FaceDetector;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

    public SparseArray<Face> detectFace(Bitmap bitmap, Context context) {
        final com.google.android.gms.vision.face.FaceDetector detector =
                new com.google.android.gms.vision.face.FaceDetector.Builder(context)
                        .setTrackingEnabled(false)
                        .setLandmarkType(com.google.android.gms.vision.face.FaceDetector.ALL_LANDMARKS)
                        .build();

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faces = detector.detect(frame);
        return faces;
    }

    public boolean isFaceDetected(SparseArray<Face> faces, Context context) {
        if (faces.size() != -1) {
            Toast.makeText(context, "Face Detected", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    public void recognizeFace(Bitmap bitmap, Context context) {
        final com.google.android.gms.vision.face.FaceDetector detector =
                new com.google.android.gms.vision.face.FaceDetector.Builder(context)
                        .setTrackingEnabled(false)
                        .setLandmarkType(com.google.android.gms.vision.face.FaceDetector.ALL_LANDMARKS)
                        .build();

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faces = detector.detect(frame);
        Bitmap resizedImage;
        float[][] faceComp = new float[][]{};
        for (int i = 0; i < faces.size(); i++) {
            Face face = faces.valueAt(i);
            faceComp[0] = new float[]{face.getPosition().x, face.getPosition().y,
                    face.getWidth(), face.getHeight()};
        }
        resizedImage = Bitmap.createBitmap(bitmap,
                (int) faceComp[0][0] + (int) (faceComp[0][2] * 0.1),
                (int) faceComp[0][1] + (int) (faceComp[0][3] * 0.1),
                (int)faceComp[0][2] - (int) (faceComp[0][2] * 0.2),
                (int) faceComp[0][3] - (int) (faceComp[0][3] * 0.1));
        float[] arrayImage = getPixels(resizedImage);
        float mean = meanArray(arrayImage);
        float std = stdArray(arrayImage);
        float std_adj;
        if (std < 1.0 / sqrt(arrayImage.length)) {
            std_adj = 1 / sqrt(arrayImage.length);
        } else {
            std_adj = std;
        }
        for (int i = 0; i < arrayImage.length; i++) {
            arrayImage[i] -= mean;
            arrayImage[i]  = arrayImage[i] * 1/std_adj;
        }
        float[][] output = new float[1][512];


    }

    public static float[] getPixels(Bitmap bitmap) {
        final int IMAGE_SIZE = 160;

        int[] intValues = new int[IMAGE_SIZE * IMAGE_SIZE];
        float[] floatValues = new float[IMAGE_SIZE * IMAGE_SIZE * 3];

        if (bitmap.getWidth() != IMAGE_SIZE || bitmap.getHeight() != IMAGE_SIZE) {
            // rescale the bitmap if needed
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, IMAGE_SIZE, IMAGE_SIZE);
        }

        bitmap.getPixels(intValues, 0,
                bitmap.getWidth(), 0, 0,
                bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < intValues.length; i++) {
            final int val = intValues[i];
            // bitwise shifting - without our image is shaped [1,160,160,1] but we need [1,160,160,3]
            floatValues[i * 3 + 2] = Color.red(val);
            floatValues[i * 3 + 1] = Color.green(val);
            floatValues[i * 3 ] = Color.blue(val);
        }
        return floatValues;
    }

    public static float meanArray(float[] array) {
        float sum = 0;
        // Calculate Sum of array
//        for (int i = 0; i < array.length; i++) {
//            sum = sum + array[i];
//        }
        for (float value : array) {
            sum += value;
        }
        // Calculate Mean of Array
        float mean = sum / array.length;

        return mean;
    }

    public static float stdArray(float[] array) {
        float sum = 0;
        for (float value : array) {
            sum += value;
        }
        // Calculate Mean of Array
        float mean = sum / array.length;
        float std = 0;
        for (float value : array) {
            std += Math.pow(value - mean, 2);
        }
        float temp = 0;
        float sr = std/array.length / 2;

        do {
            temp = sr;
            sr = (temp + ((std / array.length) / temp)) / 2;
        } while ((temp - sr) != 0);

        return sr;
    }

    public static float sqrt(float value) {
        float temp = 0;
        float sr = value / 2;

        do {
            temp = sr;
            sr = (temp + (value / temp)) / 2;
        } while ((temp - sr) != 0);
        return sr;
    }

    public float[][][][] fourDimensionArrayConverter(float[] oneD) {
        float[][][][] fourD = new float[1][160][160][3];
        int col = 0, row = 0;
        for (int i = 0; i < oneD.length / 3; i++) {
            fourD[0][row][col][0] =oneD[i * 3];
            fourD[0][row][col][1] =oneD[i * 3 + 1];
            fourD[0][row][col][2] =oneD[i * 3 + 2];
            if ((col + 1) % 160 == 0) {
                col = 0;
                row++;
            } else {
                col++;
            }
        }
        return fourD;
    }
}
