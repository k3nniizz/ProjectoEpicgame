package com.example.myapplication4.Ranking;

import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Usuario {

    String Alias,Contraseña,Edad,Email, Imagen,Nombre,Pais;
    int Score, Score2,Score3;
    String Uid;


    public Usuario() {

    }

    public Usuario(String alias, String contraseña, String edad, String email, String imagen, String nombre, String pais, int score, int score2, int score3, String uid) {
        Alias = alias;
        Contraseña = contraseña;
        Edad = edad;
        Email = email;
        Imagen = imagen;
        Nombre = nombre;
        Pais = pais;
        Score = score;
        Score2 = score2;
        Score3 = score3;
        Uid = uid;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getScore2() {
        return Score2;
    }

    public void setScore2(int score2) {
        Score2 = score2;
    }

    public int getScore3() {
        return Score3;
    }

    public void setScore3(int score3) {
        Score3 = score3;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
