package com.example.demo.util;

import java.util.HashMap;

import com.example.demo.entidades.Provincia;

public class ProvinciaManager {
    private static ProvinciaManager instance;

    // Método que devuelve la provincia instanciada aplicando el singleton.
    public static ProvinciaManager getInstance() {
        if (instance == null)
            instance = new ProvinciaManager();
        return instance;
    }

    private HashMap<Integer, Provincia> provinces;

    private ProvinciaManager() {
        this.provinces = new HashMap<Integer, Provincia>();
    }

    // Este método comprueba si en el HashMap ya se encuentra la provincia creada, en el caso de que no lo esté
    // crea el objeto Provincia correspondiente a esta y la añade al HashMap, devolviendo en ambos caso la 
    // provincia en cuestión.
    public Provincia crearProvincia(int codigoProvincia, String nombreProvincia) {
        Provincia provincia = this.provinces.getOrDefault(codigoProvincia, null);

        if (provincia != null)
            return provincia;

        provincia = new Provincia(codigoProvincia, nombreProvincia);
        this.provinces.put(codigoProvincia, provincia);

        return provincia;
    }

    // Este método crea un Array que será devuelto tras haber sido rellenado con los objetos
    // Provincia existentes.
    public Provincia[] obtenerProvincias() {
        Provincia[] provincias = new Provincia[this.provinces.size()];

        int i = 0;
        for (Provincia provincia : this.provinces.values()) {
            provincias[i] = provincia;
            i++;
        }

        return provincias;
    }

    // Para limpiar el HashMap tras cada carga.
    public void clear() {
        this.provinces.clear();
    }
}