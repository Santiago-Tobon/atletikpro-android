package com.atletikpro.app.model;

/** Zapatilla del catálogo (RF3). */
public class Producto {
    private final long id;
    private final String nombre, marca, categoria, tallas;
    private final double precio;

    public Producto(long id, String nombre, String marca, String categoria, String tallas, double precio) {
        this.id = id; this.nombre = nombre; this.marca = marca;
        this.categoria = categoria; this.tallas = tallas; this.precio = precio;
    }
    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMarca() { return marca; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    /** Tallas guardadas como ",38,39,40," y mostradas como "38, 39, 40". */
    public String getTallasTexto() { return tallas.replaceAll("^,|,$", "").replace(",", ", "); }
}
