package com.aila.ailahackathon.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aila.ailahackathon.BaseV.BaseView;
import com.aila.ailahackathon.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Schedule extends AppCompatActivity implements BaseView {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference userRef = firebaseFirestore.collection("users");
    private static final String TAG = "Schedule";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
    }

    private void addSchedule(){
        FirebaseAuth user = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = user.getCurrentUser();
        Schedule schedule = new Schedule();
        userRef.document(firebaseUser.getUid()).collection("schedule")
                .add(schedule)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        firebaseFirestore.collection("schedule")
                .add(schedule)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        onAddSuccess("Berhasil");
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

    }

    @Override
    public void onAddError(String message) {

    }
}
