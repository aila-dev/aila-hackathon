package com.aila.ailahackathon.model;

public class MakanModel {
    private String nama_makanan;
    private String jumlah_kalori;
    private String tanggal;
    private String jenis_makanan;

    public MakanModel(String nama_makanan, String jumlah_kalori, String tanggal, String jenis_makanan) {
        this.nama_makanan = nama_makanan;
        this.jumlah_kalori = jumlah_kalori;
        this.tanggal = tanggal;
        this.jenis_makanan = jenis_makanan;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getJumlah_kalori() {
        return jumlah_kalori;
    }

    public void setJumlah_kalori(String jumlah_kalori) {
        this.jumlah_kalori = jumlah_kalori;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJenis_makanan() {
        return jenis_makanan;
    }

    public void setJenis_makanan(String jenis_makanan) {
        this.jenis_makanan = jenis_makanan;
    }
}
