package com.aila.ailahackathon.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.aila.ailahackathon.R;
import com.aila.ailahackathon.schedule.Schedule;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAila extends Fragment{
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 7;

    public FragmentAila() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RuntimePermissionForUser();
        return inflater.inflate(R.layout.fragment_aila, container, false);
    }
    public void RuntimePermissionForUser() {
        Intent PermissionIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        startActivityForResult(PermissionIntent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }
}
