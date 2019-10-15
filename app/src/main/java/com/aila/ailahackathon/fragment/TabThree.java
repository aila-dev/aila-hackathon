package com.aila.ailahackathon.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aila.ailahackathon.R;
import com.aila.ailahackathon.fragment.makan.MakanAdapter;
import com.aila.ailahackathon.fragment.minum.MinumAdapter;
import com.aila.ailahackathon.model.MakanModel;
import com.aila.ailahackathon.model.MinumModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabThree extends Fragment {
    List<MakanModel> listMakan = new ArrayList<>();
    RecyclerView recyclerView;
    private MakanAdapter makanAdapter;
    public TabThree() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        MakanModel makanModel = new MakanModel("Bakso","120kkl","2019/10/15","Berlemak");
        listMakan.add(makanModel);
        makanAdapter = new MakanAdapter(listMakan);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(makanAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_three, container, false);
        recyclerView = view.findViewById(R.id.recycleMakan);
        return view;
    }

}
