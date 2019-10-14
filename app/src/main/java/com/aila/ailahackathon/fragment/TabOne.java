package com.aila.ailahackathon.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aila.ailahackathon.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


public class TabOne extends Fragment {
    PieChart pieChartView;
    List<PieEntry> pieData = new ArrayList<>();

    public TabOne(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        pieChartView = view.findViewById(R.id.chart);
        pieData.add(new PieEntry(100, "Makan"));
        pieData.add(new PieEntry(100, "Tidur"));
        pieData.add(new PieEntry(100, "Minum"));
        pieData.add(new PieEntry(70, "Mata"));
        PieDataSet pieChartData = new PieDataSet(pieData,"Poin");
        pieChartData.setColors(new int[]{R.color.colorBoldBlue,R.color.colorGreen,R.color.colorYellow,R.color.colorOrange,R.color.colorMagenta}, getContext());
        PieData data=new PieData(pieChartData);
        pieChartView.setData(data);
        pieChartView.invalidate();
        return view;

    }

}
