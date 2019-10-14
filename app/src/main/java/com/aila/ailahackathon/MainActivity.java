package com.aila.ailahackathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 7;

    ImageView imageView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.activeaila);
        if (!Settings.canDrawOverlays(this)) {
            RuntimePermissionForUser();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    startService(new Intent(MainActivity.this, Aila.class));
                    finish();
                } else if (Settings.canDrawOverlays(MainActivity.this)) {
                    startService(new Intent(MainActivity.this, Aila.class));
                } else {
                    RuntimePermissionForUser();
                    Toast.makeText(MainActivity.this, "System Alert Window Permission is Required For Floating WIdget.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void moveparentcare(View view){
        Intent in=new Intent(getBaseContext(),MainParentCare.class);
        startActivity(in);
    }
    public void RuntimePermissionForUser() {
        Intent PermissionIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));

        startActivityForResult(PermissionIntent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }
}
