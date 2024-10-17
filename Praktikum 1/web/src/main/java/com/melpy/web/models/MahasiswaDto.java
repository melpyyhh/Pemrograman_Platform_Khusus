package com.melpy.web.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MahasiswaDto {
    @NotEmpty(message = "Nama tidak boleh kosong")
    private String nama;

    @NotEmpty(message = "Jurusan tidak boleh kosong")
    private String jurusan;

    @NotEmpty(message = "NIM tidak boleh kosong")
    private String nim;

    @NotNull(message = "Tanggal Lahir tidak boleh kosong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggalLahir;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}
