package com.aila.ailahackathon.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aila.ailahackathon.BaseV.BaseView;
import com.aila.ailahackathon.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Registration extends AppCompatActivity implements BaseView {
    private static final String TAG = "Registration";
    private static final String USER_EMAIL = "email";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    Button regist;
    EditText etEmail,etUsername,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regist = findViewById(R.id.regist);
        etEmail = findViewById(R.id.email);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
    }

    @Override
    protected void onStart() {
        super.onStart();
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.regist :
                        doRegist();
                }
            }
        });
    }

    private void doRegist(){
        String email = etEmail.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        Log.d(TAG, "doRegist: oke");
        final HashMap<String, Object> user = new HashMap<>();
        user.put(USER_EMAIL, email);
        user.put(USER_USERNAME, username);
        user.put(USER_PASSWORD, password);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String uid = authResult.getUser().getUid();
                        firebaseFirestore.collection("users")
                                .document(uid)
                                .set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        onAddSuccess("Berhasil di daftarkan");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        onAddError(e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onAddError(e.getMessage());
                    }
                });
    }

    @Override
    public void onAddSuccess(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}
