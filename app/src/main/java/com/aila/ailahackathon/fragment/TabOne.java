package com.aila.ailahackathon.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aila.ailahackathon.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class TabOne extends Fragment {
    PieChartView pieChartView;
    List<SliceValue> pieData = new ArrayList<>();

    public TabOne(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        pieChartView= view.findViewById(R.id.chart);
        pieData.add(new SliceValue(15, Color.BLUE).setLabel("Makan"));
        pieData.add(new SliceValue(25, Color.GREEN).setLabel("Tidur"));
        pieData.add(new SliceValue(10, Color.RED).setLabel("Minum"));
        pieData.add(new SliceValue(60, Color.YELLOW).setLabel("Mata"));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartView.setPieChartData(pieChartData);
        return view;

    }

}
