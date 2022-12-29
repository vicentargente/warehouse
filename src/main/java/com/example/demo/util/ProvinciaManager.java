package com.example.demo.util;

import java.util.HashMap;

import com.example.demo.entidades.Provincia;

public class ProvinciaManager {
    private static ProvinciaManager instance;

    public static ProvinciaManager getInstance() {
        if (instance == null)
            instance = new ProvinciaManager();
        return instance;
    }

    private HashMap<Integer, Provincia> provinces;

    private ProvinciaManager() {
        this.provinces = new HashMap<Integer, Provincia>();
    }

    public Provincia crearProvincia(int codigoProvincia, String nombreProvincia) {
        Provincia provincia = this.provinces.getOrDefault(codigoProvincia, null);

        if (provincia != null)
            return provincia;

        provincia = new Provincia(codigoProvincia, nombreProvincia);
        this.provinces.put(codigoProvincia, provincia);

        return provincia;
    }

    public Provincia[] obtenerProvincias() {
        Provincia[] provincias = new Provincia[this.provinces.size()];

        int i = 0;
        for (Provincia provincia : this.provinces.values()) {
            provincias[i] = provincia;
            i++;
        }

        return provincias;
    }

    public void clear() {
        this.provinces.clear();
    }
}