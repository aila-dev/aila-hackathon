package com.aila.ailahackathon.percobaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aila.ailahackathon.R;

public class MainVideoFaceDetection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_video_face_detection);
    }

    public void onVideoFromCameraClick(View view) {
        Intent intent = new Intent(this, VideoFaceDetection.class);
        startActivity(intent);
    }
}
