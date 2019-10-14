package com.aila.ailahackathon.schedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aila.ailahackathon.BaseV.BaseView;
import com.aila.ailahackathon.R;
import com.aila.ailahackathon.auth.Registration;
import com.aila.ailahackathon.model.ScheduleModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Schedule extends AppCompatActivity implements BaseView {
    private static final String TAG = "Schedule";
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;
    List<ScheduleModel> listSchedule = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        recyclerView = findViewById(R.id.reyclerSchedule);
    }

    @Override
    public void onAddSuccess(String message) {

    }

    @Override
    public void onAddError(String message) {

    }
    public void moveaddschedule(View view){
        Intent in=new Intent(getBaseContext(), AddSchedule.class);
        startActivity(in);
    }
    static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
        listSchedule.clear();
        Registration.userRef
                .document("bD017zDCRfRpBVq0ec2PbA6QgsX2")
                .collection("schedule")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "listen:error", e);
                            return;
                        }

                        for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                            ScheduleModel scheduleModel = dc.getDocument().toObject(ScheduleModel.class);
                            listSchedule.add(scheduleModel);
                            scheduleAdapter = new ScheduleAdapter(listSchedule);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(scheduleAdapter);
                        }

                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
}
