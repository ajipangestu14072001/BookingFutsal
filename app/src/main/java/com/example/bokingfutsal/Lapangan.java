package com.example.bokingfutsal;

public class Lapangan {
    private String a1id;
    private String a2judul;
    private String a3deskripsi;
    private String a4rating;
    private String a5fasilitas;
    private String a6harga;
    private String a7image;

    public Lapangan(){

    }

    public Lapangan(String a1id, String a2judul, String a3deskripsi, String a4rating, String a5fasilitas, String a6harga, String a7image) {
        this.a1id = a1id;
        this.a2judul = a2judul;
        this.a3deskripsi = a3deskripsi;
        this.a4rating = a4rating;
        this.a5fasilitas = a5fasilitas;
        this.a6harga = a6harga;
        this.a7image = a7image;
    }

    public String getA1id() {
        return a1id;
    }

    public void setA1id(String a1id) {
        this.a1id = a1id;
    }

    public String getA2judul() {
        return a2judul;
    }

    public void setA2judul(String a2judul) {
        this.a2judul = a2judul;
    }

    public String getA3deskripsi() {
        return a3deskripsi;
    }

    public void setA3deskripsi(String a3deskripsi) {
        this.a3deskripsi = a3deskripsi;
    }

    public String getA4rating() {
        return a4rating;
    }

    public void setA4rating(String a4rating) {
        this.a4rating = a4rating;
    }

    public String getA5fasilitas() {
        return a5fasilitas;
    }

    public void setA5fasilitas(String a5fasilitas) {
        this.a5fasilitas = a5fasilitas;
    }

    public String getA6harga() {
        return a6harga;
    }

    public void setA6harga(String a6harga) {
        this.a6harga = a6harga;
    }

    public String getA7image() {
        return a7image;
    }

    public void setA7image(String a7image) {
        this.a7image = a7image;
    }
}
