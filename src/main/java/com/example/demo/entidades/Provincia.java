package com.example.demo.entidades;

public class Provincia {
    private int codigo;
    private String nombre;

    public Provincia(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }
}