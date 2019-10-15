package com.aila.ailahackathon.inspector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aila.ailahackathon.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class ActivityFaceDetection extends AppCompatActivity {
    private static final String TAG = "FaceDetection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detection);

        final FaceDetector detector = new FaceDetector.Builder(this)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();

        Intent intent = getIntent();
        final ImageView imageView = findViewById(R.id.imageView);
        final String mCurrentPhotoPath = intent.getStringExtra("mCurrentPhotoPath");

        // run image related code after the view was laid out
        // to have all dimensions calculated

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        imageView.setImageBitmap(bitmap);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentPhotoPath != null) {
                    Bitmap bitmap = getBitmapFromPathForImageView(mCurrentPhotoPath, imageView);
                    imageView.setImageDrawable(null);
                    imageView.setImageBitmap(bitmap);

                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<Face> faces = detector.detect(frame);

                    Log.d(TAG, "Face Detected: " + String.valueOf(faces.size()));

                    Paint paint = new Paint();
                    paint.setColor(Color.GREEN);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(4);

                    Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas = new Canvas(mutableBitmap);
                    Bitmap resizedImage;
                    float[][] faceComp = new float[1][4];

                    for (int i = 0; i < faces.size(); i++) {
                        Face face = faces.valueAt(i);
                        for (Landmark landmark : face.getLandmarks()) {
                            int cx = (int) (landmark.getPosition().x);
                            int cy = (int) (landmark.getPosition().y);
                            canvas.drawCircle(cx, cy, 10, paint);
                        }

                        faceComp[0] = new float[]{face.getPosition().x, face.getPosition().y,
                                face.getWidth(), face.getHeight()};

                        Path path = new Path();
                        path.moveTo(face.getPosition().x, face.getPosition().y);
                        path.lineTo(face.getPosition().x + face.getWidth(), face.getPosition().y);
                        path.lineTo(face.getPosition().x + face.getWidth(), face.getPosition().y + face.getHeight());
                        path.lineTo(face.getPosition().x, face.getPosition().y + face.getHeight());
                        path.close();

                        Paint redPaint = new Paint();
                        redPaint.setColor(0XFFFF0000);
                        redPaint.setStyle(Paint.Style.STROKE);
                        redPaint.setStrokeWidth(8.0f);
                        canvas.drawPath(path, redPaint);
                    }
                    resizedImage = Bitmap.createBitmap(bitmap,
                            (int) faceComp[0][0] + (int) (faceComp[0][2] * 0.1),
                            (int) faceComp[0][1] + (int) (faceComp[0][3] * 0.1),
                            (int)faceComp[0][2] - (int) (faceComp[0][2] * 0.2),
                            (int) faceComp[0][3] - (int) (faceComp[0][3] * 0.1));

                    imageView.setImageBitmap(resizedImage);
                    TextView txt = findViewById(R.id.textView);
                    float mean = meanArray(getPixels(resizedImage));
                    float std = stdArray(getPixels(resizedImage));
                    float[] yeah = getPixels(resizedImage);
                    float std_adj;
                    if (std < 1.0 / sqrt(yeah.length)) {
                        std_adj = 1 / sqrt(yeah.length);
                    } else {
                        std_adj = std;
                    }
                    for (int i = 0; i < yeah.length; i++) {
                        yeah[i] -= mean;
                        yeah[i]  = yeah[i] * 1/std_adj;
                    }
                    float[][] output = new float[1][512];
                    Interpreter interpreter;
                    try {
                        float[][][][] gg = oneToThreeD(yeah);
                        interpreter = new Interpreter(loadModelFile());
                        interpreter.run(gg, output);
                        float[] berhasil = output[0];
                        txt.setText("Berhasil = " + Arrays.toString(berhasil));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                        txt.setText(e.toString());
                    }

//                    txt.setText(Arrays.toString(yeah));
                    TextView txtError = findViewById(R.id.txtError);
                    txtError.setText("Walah, panjangnya = " + yeah.length);
                }
            }
        });
    }

    private Bitmap getBitmapFromPathForImageView(String mCurrentPhotoPath, ImageView imageView) {
        // Get the dimensions of th View
        int targetW = imageView.getWidth();
        Log.w("lebar", "lebar_awal = " + targetW);
        int targetH = imageView.getHeight();
        Log.w("panjang", "panjang_awal = " + targetH);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        Log.w("lebar1", "lebar = " + photoW);
        int photoH = bmOptions.outHeight;
        Log.w("panjang1", "lebar = " + photoH);

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        Bitmap rotatedBitmap = bitmap;

        // Decode bitmap if needed
        try {
            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotatedImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotatedImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotatedImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return rotatedBitmap;
    }

    public static Bitmap rotatedImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0,
                source.getWidth(), source.getHeight(), matrix, true);
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

    private MappedByteBuffer loadModelFile() throws IOException {
        String MODEL_ASSETS_PATH = "my_facenet_new.tflite";
        AssetFileDescriptor assetFileDescriptor = getApplicationContext().getAssets().openFd(MODEL_ASSETS_PATH) ;
        FileInputStream fileInputStream = new FileInputStream( assetFileDescriptor.getFileDescriptor() ) ;
        FileChannel fileChannel = fileInputStream.getChannel() ;
        long startoffset = assetFileDescriptor.getStartOffset() ;
        long declaredLength = assetFileDescriptor.getDeclaredLength() ;
        return fileChannel.map( FileChannel.MapMode.READ_ONLY , startoffset , declaredLength ) ;
    }

    public float[][][][] oneToThreeD(float[] oneD) {
        float[][][][] twoD = new float[1][160][160][3];
        int col = 0, row = 0;
        for (int i = 0; i < oneD.length / 3; i++) {
            twoD[0][row][col][0] =oneD[i * 3];
            twoD[0][row][col][1] =oneD[i * 3 + 1];
            twoD[0][row][col][2] =oneD[i * 3 + 2];
            if ((col + 1) % 160 == 0) {
                col = 0;
                row++;
            } else {
                col++;
            }
        }
        return twoD;
    }
}
