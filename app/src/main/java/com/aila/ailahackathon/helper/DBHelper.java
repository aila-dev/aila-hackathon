package com.aila.ailahackathon.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "aila.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TBL_SCCHEDULE = "CREATE TABLE schedule(schedule_id string primary key, judul string, isi string, waktu datetime )";
        db.execSQL(TBL_SCCHEDULE);
        String TBL_USER = "CREATE TABLE user(user_id string primary key,username string, email string, password string)";
        db.execSQL(TBL_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
