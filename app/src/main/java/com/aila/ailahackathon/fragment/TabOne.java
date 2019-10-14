package com.aila.ailahackathon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aila.ailahackathon.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabOne extends Fragment {
    public TabOne(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_one, container, false);
    }
}
