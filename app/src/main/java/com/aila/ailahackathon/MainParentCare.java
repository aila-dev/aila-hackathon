package com.aila.ailahackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aila.ailahackathon.auth.Login;
import com.aila.ailahackathon.auth.Registration;

public class MainParentCare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_parent_care);
    }
    public void movelogin(View view){
        Intent in=new Intent(getBaseContext(), Login.class);
        startActivity(in);
    }
    public void moveregistration(View view){
        Intent in=new Intent(getBaseContext(), Registration.class);
        startActivity(in);
    }
}
