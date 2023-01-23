package com.example.demo.entidades;

public class CentroSanitario {
    private int ID;
    private String nombre;
    private String tipo;
    private String direccion;
    private int codigoPostal;
    private double longitud;
    private double latitud;
    private int telefono;
    private String descripcion;
    private Localidad localidad;

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public CentroSanitario(int id, String nombre, String tipo, String direccion, int codigoPostal, double longitud,
            double latitud, int telefono,
            String descripcion, Localidad localidad) {
        this.ID = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.longitud = longitud;
        this.latitud = latitud;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.localidad = localidad;
    }

    // GETTERS

    public String getNombre() {
        return this.nombre;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public int getCodigoPostal() {
        return this.codigoPostal;
    }

    public double getLongitud() {
        return this.longitud;
    }

    public double getLatitud() {
        return this.latitud;
    }

    public int getTelefono() {
        return this.telefono;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Localidad getLocalidad() {
        return this.localidad;
    }

    // SETTERS

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return "\n\t{\n" +
                "\t\t\"nombre\": \"" + this.nombre + "\",\n" +
                "\t\t\"tipo\": \"" + this.tipo + "\",\n" +
                "\t\t\"direccion\": \"" + this.direccion + "\",\n" +
                "\t\t\"cp\": \"" + this.codigoPostal + "\",\n" +
                "\t\t\"longitud\": \"" + this.longitud + "\",\n" +
                "\t\t\"latitud\": \"" + this.latitud + "\",\n" +
                "\t\t\"telefono\": \"" + this.telefono + "\",\n" +
                "\t\t\"descripcion\": \"" + (this.descripcion == null ? null : this.descripcion.replace("\n"," ").replace("\"","\\\"")) + "\",\n" +
                "\t\t\"localidad\": {\n\t\t\t\"nombre\":\"" + this.localidad.getNombre() + "\",\n\t\t\t\"codigo\": \"" + this.localidad.getCodigo() + "\"\n\t\t},\n" +
                "\t\t\"provincia\": {\n\t\t\t\"nombre\":\"" + this.localidad.getProvincia().getNombre() + "\",\n\t\t\t\"codigo\": \"" + this.localidad.getProvincia().getCodigo() + "\"\n\t\t}\n" +
                "\t}\n";
    }

}