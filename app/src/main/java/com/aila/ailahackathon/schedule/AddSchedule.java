package com.aila.ailahackathon.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.aila.ailahackathon.BaseV.BaseView;
import com.aila.ailahackathon.R;
import com.aila.ailahackathon.auth.Registration;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import java.util.Calendar;

public class AddSchedule extends AppCompatActivity implements BaseView {
    private static final String TAG = "AddSchedule";
    private int mYear,mMonth,mDay,mHour,mMinute;

    Button btGetTanggal,btGetJam,btSimpan;
    EditText etTanggal,etJam,etJudul,etDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        btGetTanggal = (Button) findViewById(R.id.get_tanggal);
        btGetJam = (Button) findViewById(R.id.get_jam);
        btSimpan = (Button) findViewById(R.id.simpan);
        etTanggal = findViewById(R.id.tanggal);
        etJam = findViewById(R.id.jam);
        etJudul = findViewById(R.id.judul);
        etDeskripsi = findViewById(R.id.deskripsi);
    }

    public void addSchedule(View view){
        FirebaseAuth user = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = user.getCurrentUser();
        Schedule schedule = new Schedule();
        Registration
                .userRef
                .document(firebaseUser.getUid()).collection("schedule")
                .add(schedule)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        documentReference.get();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        Registration
                .firebaseFirestore.collection("schedule")
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
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    public void getTanggal(View view){
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//            }
//        });
    }

    public void test(int message){
        Log.d(TAG, "test: "+message);
    }
}
