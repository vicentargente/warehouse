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
                "\t\tnombre: '" + this.nombre + "',\n" +
                "\t\ttipo: '" + this.tipo + "',\n" +
                "\t\tdireccion: '" + this.direccion + "',\n" +
                "\t\tcp: " + this.codigoPostal + ",\n" +
                "\t\tlongitud: " + this.longitud + ",\n" +
                "\t\tlatitud: " + this.latitud + ",\n" +
                "\t\ttelefono: " + this.telefono + ",\n" +
                "\t\tdescripcion: '" + this.descripcion + "',\n" +
                "\t\tlocalidad: {\n\t\t\tnombre:'" + this.localidad.getNombre() + "',\n\t\t\tcodigo: '" + this.localidad.getCodigo() + "'\n\t\t},\n" +
                "\t\tprovincia: {\n\t\t\tnombre:'" + this.localidad.getProvincia().getNombre() + "',\n\t\t\tcodigo: '" + this.localidad.getProvincia().getCodigo() + "'\n\t\t}\n" +
                "\t}\n";
    }

}