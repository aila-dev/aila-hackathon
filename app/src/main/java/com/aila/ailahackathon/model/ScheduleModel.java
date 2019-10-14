package com.aila.ailahackathon.model;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.util.Date;

public class ScheduleModel {
    private String judul;
    private String isi;
    private Timestamp waktu;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public Timestamp getWaktu() {
        return waktu;
    }

    public void setWaktu(Timestamp waktu) {
        this.waktu = waktu;
    }
}
