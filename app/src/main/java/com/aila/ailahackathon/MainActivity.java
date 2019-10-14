package com.aila.ailahackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.aila.ailahackathon.inspector.Inspector;
import com.aila.ailahackathon.schedule.AddSchedule;
import com.aila.ailahackathon.schedule.Schedule;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void moveparentcare(View view){
        Intent in=new Intent(getBaseContext(),MainParentCare.class);
        startActivity(in);
    }
    public void RuntimePermissionForUser() {
        Intent PermissionIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(PermissionIntent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }
    public void activateaila(View view){
        startService(new Intent(MainActivity.this, Aila.class));
        if(SYSTEM_ALERT_WINDOW_PERMISSION!=7) {
            RuntimePermissionForUser();
            Toast.makeText(MainActivity.this, "System Alert Window Permission is Required For Floating WIdget.", Toast.LENGTH_LONG).show();
        }
    }
}
