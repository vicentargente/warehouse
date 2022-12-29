package com.example.demo.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.demo.entidades.Localidad;
import com.example.demo.entidades.Provincia;

public class LocalidadManager {
    private static LocalidadManager instance;

    public static LocalidadManager getInstance() {
        if (instance == null)
            instance = new LocalidadManager();
        return instance;
    }

    private HashMap<String, Localidad> towns;
    private int currentId;

    private String persistenceFilePath;

    private LocalidadManager() {
        this.towns = new HashMap<String, Localidad>();
        this.currentId = 0;
    }

    /**
     * Devuelve un ID único para cada par (Provincia, Municipio)
     * 
     * @param nombreProvincia
     * @param localidad
     * @return ID asignada al municipio
     * @throws Exception
     */
    public Localidad crearLocalidad(String nombreLocalidad, Provincia provincia) {
        if (this.currentId == Integer.MAX_VALUE) {
            System.err.println("Error: No se puede guardar más localidades");
            System.exit(-1);
        }

        // Intentamos buscar la provincia+municipio
        String key = provincia.getNombre() + nombreLocalidad;
        Localidad localidad = this.towns.getOrDefault(key, null);

        // Si ya la tenemos, devolvemos su id
        if (localidad != null)
            return localidad;

        // Si no la tenemos, la ponemos y le asignamos una id
        localidad = new Localidad(currentId, nombreLocalidad, provincia);
        this.towns.put(key, localidad);

        this.currentId++;

        return localidad;
    }

    public void persistir() {
        try {
            PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(this.persistenceFilePath), "UTF-8"));
            JSONArray json = new JSONArray();
            JSONObject jsonObject;

            for (Map.Entry<String, Localidad> set : this.towns.entrySet()) {
                jsonObject = new JSONObject();
                jsonObject.put("key", set.getKey());
                jsonObject.put("codigo", set.getValue().getCodigo());
                jsonObject.put("nombre", set.getValue().getNombre());
                json.put(jsonObject);
            }

            writer.print(json.toString());

            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
        } catch (UnsupportedEncodingException e) {
        }
    }

    public Localidad[] obtenerLocalidades() {
        Localidad[] localidades = new Localidad[this.towns.size()];

        int i = 0;
        for (Localidad localidad : this.towns.values()) {
            localidades[i] = localidad;
            i++;
        }

        return localidades;
    }

    public void clear() {
        this.towns.clear();
        this.currentId = 0;
    }
}