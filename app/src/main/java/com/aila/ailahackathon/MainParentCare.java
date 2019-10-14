package com.aila.ailahackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aila.ailahackathon.auth.Login;
import com.aila.ailahackathon.auth.Registration;

public class MainParentCare extends AppCompatActivity {
    Button login,regist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_parent_care);
        login=findViewById(R.id.tologin);
        regist=findViewById(R.id.toregist);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getBaseContext(), Login.class);
                startActivity(in);
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getBaseContext(), Registration.class);
                startActivity(in);
            }
        });

    }

//    public void movelogin(View view){
//        Intent in=new Intent(getBaseContext(), Login.class);
//        startActivity(in);
//    }
//    public void moveregistration(View view){
//        Intent in=new Intent(getBaseContext(), Registration.class);
//        startActivity(in);
//    }
}
