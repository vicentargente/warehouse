package com.example.demo.extractores;

import org.json.*;

import com.example.demo.entidades.CentroSanitario;
import com.example.demo.entidades.Localidad;
import com.example.demo.entidades.Provincia;
import com.example.demo.util.CentroSanitarioManager;
import com.example.demo.util.LocalidadManager;
import com.example.demo.util.Logger;
import com.example.demo.util.ProvinciaManager;

public class ExtractorEUS {
    private JSONArray json;

    public ExtractorEUS(String json) {
        try {
            this.json = new JSONArray(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CentroSanitario[] convertir() {
        Logger logger = Logger.getInstance();

        int nHospitales = this.json.length();

        CentroSanitarioManager centroSanitarioManager = CentroSanitarioManager.getInstance();
        LocalidadManager localidadManager = LocalidadManager.getInstance();
        ProvinciaManager provinciaManager = ProvinciaManager.getInstance();

        // CentroSanitario[] hospitales = new CentroSanitario[nHospitales];
        CentroSanitario[] hospitales = new CentroSanitario[0];

        // Atributos globales
        String nombre;
        String tipo = "";
        String direccion;
        int codigoPostal;
        double longitud;
        double latitud;
        int telefono;
        String descripcion = null;
        Localidad localidad;
        Provincia provincia;

        // Atributos EUS
        String Tipodecentro;
        String codigoPostalString;
        String telefonos;
        String horarioAtencionCiudadana;
        String horarioEspecial;
        // int codLocalidad;
        String nombreLocalidad;
        int codProvincia;
        String nombreProvincia;

        JSONObject jsonHospital;

        for (int i = 0; i < nHospitales; i++) {
            jsonHospital = this.json.getJSONObject(i);

            // Nombre
            nombre = (String) jsonHospital.get("Nombre");
            // Tipo
            Tipodecentro = (String) jsonHospital.get("Tipodecentro");
            if (Tipodecentro.equals("Hospital")) {
                tipo = "Hospital";
            } else if (Tipodecentro.equals("Centro de Salud")
                    || Tipodecentro.equals("Centro de Salud Mental")
                    || Tipodecentro.equals("Consultorio")
                    || Tipodecentro.equals("Ambulatorio")) {
                tipo = "Centro de salud";

            } else if (Tipodecentro.equals("Otros")) {
                tipo = "Otros";
            }
            // Direccion
            direccion = (String) jsonHospital.get("Direccion");
            // Latitud y longitud
            latitud = Double.parseDouble((String) jsonHospital.get("LONWGS84"));
            longitud = Double.parseDouble((String) jsonHospital.get("LATWGS84"));
            // Codigo Postal
            codigoPostalString = (String) jsonHospital.get("Codigopostal");
            try {
                codigoPostal = Integer.parseInt(codigoPostalString);
            } catch (NumberFormatException e) {
                codigoPostal = Integer.parseInt(codigoPostalString.replace(".", "").substring(0, 5));
            }

            // Telefono
            try {
                telefonos = (String) jsonHospital.get("Telefono");
                try {
                    telefono = Integer.parseInt(telefonos);
                } catch (NumberFormatException e) {
                    telefono = Integer.parseInt(telefonos.replace(".", "").split(" ")[0].substring(0, 9));
                }
            } catch (JSONException e) {
                telefono = -1;
            }

            // Descripcion
            try {
                horarioAtencionCiudadana = (String) jsonHospital.get("HorarioatencionCiudadana");
            } catch (JSONException e) {
                horarioAtencionCiudadana = null;
            }
            try {
                horarioEspecial = (String) jsonHospital.get("Horarioespecial");
            } catch (JSONException e) {
                horarioEspecial = null;
            }
            descripcion = horarioAtencionCiudadana + "\n" + horarioEspecial;
            // Provincia
            codProvincia = codigoPostal / 1000;
            nombreProvincia = (String) jsonHospital.get("Provincia");
            provincia = provinciaManager.crearProvincia(codProvincia, nombreProvincia);
            // Localidad
            nombreLocalidad = (String) jsonHospital.get("Municipio");
            localidad = localidadManager.crearLocalidad(nombreLocalidad, provincia);

            if(!centroSanitarioManager.crearHospital(nombre, Tipodecentro, direccion, codigoPostal, longitud, latitud, telefono, descripcion, localidad)){
                logger.log("EUS -> " + nombre + " esta repetido (solo se guarda una vez)");
            }

            // hospitales[i] = new CentroSanitario(nombre, tipo, direccion, codigoPostal,
            // longitud, latitud, telefono, descripcion, localidad, provincia);
        }

        return hospitales;
    }
}