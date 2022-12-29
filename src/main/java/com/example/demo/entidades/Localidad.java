package com.example.demo.entidades;

public class Localidad {
    private int codigo;
    private String nombre;
    private Provincia provincia;

    public Localidad(int codigo, String nombre, Provincia provincia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Provincia getProvincia() {
        return this.provincia;
    }
}