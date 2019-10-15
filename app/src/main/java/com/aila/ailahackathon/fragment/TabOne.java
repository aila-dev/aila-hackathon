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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.view.PieChartView;


public class TabOne extends Fragment {
    PieChart pieChartViewTidur,pieChartViewMinum,pieChartViewMata;
    List<PieEntry> pieDataTidur = new ArrayList<>();
    List<PieEntry> pieDataMinum = new ArrayList<>();
    List<PieEntry> pieDataMata = new ArrayList<>();

    public TabOne(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        pieChartViewMinum = view.findViewById(R.id.chartminum);
        pieDataMinum.add(new PieEntry(80));
        pieDataMinum.add(new PieEntry(20));
        PieDataSet pieChartDataMinum = new PieDataSet(pieDataMinum,"Grafik Jumlah Minum Anda per Hari");
        pieChartDataMinum.setColors(new int[]{R.color.colorBoldBlue,R.color.colorWhite}, getContext());
        PieData dataminum=new PieData(pieChartDataMinum);
        pieChartViewMinum.setData(dataminum);
        pieChartViewMinum.invalidate();
        Description descriptionmi = pieChartViewMinum.getDescription();
        descriptionmi.setEnabled(false);
// set the position of the description on the screen
//        description.setPosition(50, 0);

        pieChartViewTidur = view.findViewById(R.id.charttidur);
        pieDataTidur.add(new PieEntry(90));
        pieDataTidur.add(new PieEntry(10));
        PieDataSet pieChartDataTidur = new PieDataSet(pieDataTidur,"Grafik Waktu Tidur Anda per Hari");
        Description descriptionti = pieChartViewTidur.getDescription();
        descriptionti.setEnabled(false);
        pieChartDataTidur.setColors(new int[]{R.color.colorGreen,R.color.colorWhite}, getContext());
        PieData datatidur=new PieData(pieChartDataTidur);
        pieChartViewTidur.setData(datatidur);
        pieChartViewTidur.invalidate();

        pieChartViewMata = view.findViewById(R.id.chartmata);
        pieDataMata.add(new PieEntry(60));
        pieDataMata.add(new PieEntry(40));
        PieDataSet pieChartDataMata = new PieDataSet(pieDataMata,"Grafik Penggunaan Smartphone Anda per Hari");
        Description descriptionma = pieChartViewMata.getDescription();
        descriptionma.setEnabled(false);
        pieChartDataMata.setColors(new int[]{R.color.colorRed,R.color.colorWhite}, getContext());
        PieData datamata=new PieData(pieChartDataMata);
        pieChartViewMata.setData(datamata);
        pieChartViewMata.invalidate();

        return view;

    }

}
