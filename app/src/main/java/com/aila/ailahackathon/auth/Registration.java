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
import com.aila.ailahackathon.schedule.Schedule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Registration extends AppCompatActivity implements BaseView {
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static CollectionReference userRef = firebaseFirestore.collection("users");
    private static final String TAG = "Registration";
    private static final String USER_EMAIL = "email";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";

    public static FirebaseAuth auth = FirebaseAuth.getInstance();

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
    }

    public void doRegist(View view){
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
                        userRef
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
        onBackPressed();
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
    public void moveActivity(View view) {
        onBackPressed();
    }
}
