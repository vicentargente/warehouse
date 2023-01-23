package com.example.demo.util;

import java.util.HashMap;

import com.example.demo.entidades.CentroSanitario;
import com.example.demo.entidades.Localidad;

public class CentroSanitarioManager {
    private static CentroSanitarioManager instance;

    public static CentroSanitarioManager getInstance() {
        if (instance == null)
            instance = new CentroSanitarioManager();
        return instance;
    }

    private HashMap<String, CentroSanitario> centers;
    private int currentId;

    private CentroSanitarioManager() {
        this.centers = new HashMap<String, CentroSanitario>();
        this.currentId = 0;
    }

    public boolean crearHospital(String nombre, String tipo, String direccion, int codigoPostal,
            double longitud, double latitud, int telefono, String descripcion, Localidad localidad) {
        String key = nombre +
                tipo +
                direccion +
                codigoPostal +
                telefono;

        CentroSanitario centro = this.centers.getOrDefault(key, null);

        if (centro != null)
            return false;

        centro = new CentroSanitario(this.currentId, nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono,
                descripcion, localidad);
        this.centers.put(key, centro);

        this.currentId++;

        return true;
    }

    public CentroSanitario[] obtenerCentrosSanitarios() {
        CentroSanitario[] centrosSanitarios = new CentroSanitario[this.centers.size()];

        int i = 0;
        for (CentroSanitario centroSanitario : this.centers.values()) {
            centrosSanitarios[i] = centroSanitario;
            i++;
        }

        return centrosSanitarios;
    }

    public void clear(){
        this.centers.clear();
        this.currentId = 0;
    }

    public int getAmount(){
        return this.centers.size();
    }
}