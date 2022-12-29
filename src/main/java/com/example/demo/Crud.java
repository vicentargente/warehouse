package com.example.demo;

import java.sql.*;
import java.util.HashMap;
import java.util.Locale;

import com.example.demo.entidades.CentroSanitario;

import com.example.demo.entidades.Localidad;
import com.example.demo.entidades.Provincia;

public class Crud {
    private static Crud instance;

    public static Crud getInstance() throws SQLException {
        if (instance == null)
            instance = new Crud();
        return instance;
    }

    private Connection conn = null;
    Statement sqlSt;

    private Crud() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            String dbURL = "jdbc:mariadb://IEI-006-v0.dsicv.upv.es:3306";

            conn = DriverManager.getConnection(dbURL, "root", "");
            sqlSt = conn.createStatement(); // Permite a SQL ser ejecutado
            sqlSt.executeQuery("use test");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

    }

    public void createHospital(CentroSanitario[] hospitales) throws SQLException {
        Statement stmt = conn.createStatement();

        CentroSanitario h;
        String codigoPostalString, latitudString, longitudString, telefonoString, descripcionString;
        int codigoPostal, telefono;
        double latitud, longitud;
        for (int i = 0; i < hospitales.length; i++) {
            h = hospitales[i];

            codigoPostal = h.getCodigoPostal();
            codigoPostalString = codigoPostal == -1 ? null : "'" + Integer.toString(codigoPostal) + "'";

            longitud = h.getLongitud();
            longitudString = Double.isNaN(longitud) ? null : "'" + Double.toString(longitud) + "'";

            latitud = h.getLatitud();
            latitudString = Double.isNaN(latitud) ? null : "'" + Double.toString(latitud) + "'";

            telefono = h.getTelefono();
            telefonoString = telefono == -1 ? null : "'" + Integer.toString(telefono) + "'";

            descripcionString = h.getDescripcion();
            descripcionString = descripcionString == null ? null : "'" + descripcionString + "'";

            stmt.addBatch(
                    String.format(
                            Locale.ROOT,
                            "insert into centro_sanitario (ID, Nombre,Tipo,Direccion,CodigoPostal,Longitud,Latitud,Telefono,Descripcion,Localidad) values ('%d','%s','%s','%s',%s,%s,%s,%s,%s,'%d')",
                            h.getID(),
                            h.getNombre(),
                            h.getTipo(),
                            h.getDireccion(),
                            codigoPostalString,
                            longitudString,
                            latitudString,
                            telefonoString,
                            descripcionString,
                            h.getLocalidad().getCodigo()));
        }

        stmt.executeBatch();
    }

    public void createLocalidad(Localidad[] localidades) throws SQLException {
        Statement stmt = conn.createStatement();

        Localidad l;
        for (int i = 0; i < localidades.length; i++) {
            l = localidades[i];
            stmt.addBatch(String.format("insert into localidad (Codigo, Nombre, Cod_provincia) values ('%d','%s','%d')",
                    l.getCodigo(), l.getNombre(), l.getProvincia().getCodigo()));
        }

        stmt.executeBatch();
    }

    public void createProvincia(Provincia[] provincias) throws SQLException {
        Statement stmt = conn.createStatement();

        Provincia p;
        for (int i = 0; i < provincias.length; i++) {
            p = provincias[i];
            stmt.addBatch(String.format("insert into provincia (Codigo, Nombre) values ('%d','%s')", p.getCodigo(),
                    p.getNombre()));
        }

        stmt.executeBatch();
    }

    public Provincia[] getProvincias(){
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultados = stmt.executeQuery("SELECT * FROM provincia");

            if(resultados == null) return null;

            resultados.last();
            int nProvincias = resultados.getRow();
            resultados.beforeFirst();

            Provincia[] res = new Provincia[nProvincias];

            for (int i = 0; resultados.next(); i++) {
                res[i] = new Provincia(
                    resultados.getInt("Codigo"),
                    resultados.getString("Nombre")
                );
            }

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Localidad[] getLocalidades() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultados = stmt.executeQuery("SELECT * FROM localidad");

            if (resultados == null)
                return null;

            Provincia[] provincias = this.getProvincias();
            HashMap<Integer, Provincia> provinciasByCodigo = new HashMap<Integer, Provincia>();
            for(int i = 0; i < provincias.length; i++){
                provinciasByCodigo.put(provincias[i].getCodigo(),provincias[i]);
            }
            
            resultados.last();
            int nLocalidades = resultados.getRow();
            resultados.beforeFirst();

            Localidad[] res = new Localidad[nLocalidades];

            for (int i = 0; resultados.next(); i++) {
                res[i] = new Localidad(
                        resultados.getInt("Codigo"),
                        resultados.getString("Nombre"),
                        provinciasByCodigo.get(resultados.getInt("Cod_provincia")));
            }

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CentroSanitario[] getCentrosSanitarios(){
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultados = stmt.executeQuery("SELECT * FROM centro_sanitario");

            if(resultados == null) return null;

            Localidad[] localidades = this.getLocalidades();
            HashMap<Integer, Localidad> localidadesByCodigo = new HashMap<Integer, Localidad>();
            for (int i = 0; i < localidades.length; i++) {
                localidadesByCodigo.put(localidades[i].getCodigo(), localidades[i]);
            }

            resultados.last();
            int nCentros = resultados.getRow();
            resultados.beforeFirst();

            CentroSanitario[] res = new CentroSanitario[nCentros];

            for(int i = 0; resultados.next(); i++){
                res[i] = new CentroSanitario(
                    resultados.getInt("ID"),
                    resultados.getString("Nombre"),
                    resultados.getString("Tipo"),
                    resultados.getString("Direccion"),
                    resultados.getInt("CodigoPostal"),
                    resultados.getDouble("Longitud"),
                    resultados.getDouble("Latitud"),
                    resultados.getInt("Telefono"),
                    resultados.getString("Descripcion"),
                    localidadesByCodigo.get(resultados.getInt("Localidad"))
                );
            }

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearTables(){
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeQuery("DELETE FROM centro_sanitario");
            stmt.executeQuery("DELETE FROM localidad");
            stmt.executeQuery("DELETE FROM provincia");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}