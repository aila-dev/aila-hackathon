package com.aila.ailahackathon.model;

import com.google.firebase.Timestamp;

public class MinumModel {
    private int minum_perhari;
    private Timestamp tanggal;
    private int total_minum;

    public int getMinum_perhari() {
        return minum_perhari;
    }

    public void setMinum_perhari(int minum_perhari) {
        this.minum_perhari = minum_perhari;
    }

    public int getTotal_minum() {
        return total_minum;
    }

    public void setTotal_minum(int total_minum) {
        this.total_minum = total_minum;
    }

    public Timestamp getTanggal() {
        return tanggal;
    }

    public void setTanggal(Timestamp tanggal) {
        this.tanggal = tanggal;
    }
}
