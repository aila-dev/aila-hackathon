package com.aila.ailahackathon.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aila.ailahackathon.BaseV.BaseView;
import com.aila.ailahackathon.R;
import com.aila.ailahackathon.auth.Registration;
import com.aila.ailahackathon.model.ScheduleModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSchedule extends AppCompatActivity implements BaseView {
    private static final String TAG = "AddSchedule";
    private int mYear, mMonth, mDay, mHour, mMinute;
    final static int RQS_1 = 1;

    Button btGetTanggal, btGetJam, btSimpan;
    EditText etTanggal, etJam, etJudul, etDeskripsi;

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

    public void addSchedule(View view) {
        Date waktu;
        String judul = etJudul.getText().toString();
        String deskripsi = etDeskripsi.getText().toString();
        FirebaseAuth user = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = user.getCurrentUser();
        ScheduleModel schedule = new ScheduleModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
        try {
            Date date = dateFormat.parse( etTanggal.getText().toString() + " " + etJam.getText().toString());
            Timestamp timestamp = new Timestamp(date);
            schedule.setIsi(deskripsi);
            schedule.setJudul(judul);
            schedule.setWaktu(timestamp);
            Calendar calendar = Calendar.getInstance();
            String[] tgl = etTanggal.getText().toString().split("/");
            String[] jm = etJam.getText().toString().split(":");
            calendar.set(
                    Integer.parseInt(tgl[0]),
                    Integer.parseInt(tgl[1]),
                    Integer.parseInt(tgl[2]),
                    Integer.parseInt(jm[0]),
                    Integer.parseInt(jm[1]),00
            );
            setAlarm(calendar);
            Registration
                .userRef
                .document("bD017zDCRfRpBVq0ec2PbA6QgsX2").collection("schedule")
                .add(schedule)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        documentReference.get();
                        onAddSuccess("Berhasil ditambahkan");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onAddError(e.getMessage());
                    }
                });
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(),etTanggal.getText().toString() + " " + etJam.getText().toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void setAlarm(Calendar calendar){
        Toast.makeText(getApplicationContext()," "+calendar.getTime(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(),ScheduleBroadcastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),RQS_1,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }

    @Override
    public void onAddSuccess(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void getTanggal(View view) {
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                etTanggal.setText(year + "/" + (monthOfYear) + "/" + dayOfMonth);
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    public void getJam(View view) {
        final Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                etJam.setText(hourOfDay + ":" + minute);
            }
        }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    public void moveActivity(View view) {
        onBackPressed();
    }

    public void test(int message) {
        Log.d(TAG, "test: " + message);
    }
}
