package com.atletikpro.app.model;

/** Línea del carrito: un producto y su cantidad. */
public class ItemCarrito {
    private final Producto producto;
    private int cantidad;

    public ItemCarrito(Producto producto, int cantidad) { this.producto = producto; this.cantidad = cantidad; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    void incrementar() { cantidad++; }
    void decrementar() { cantidad--; }
    public double subtotal() { return producto.getPrecio() * cantidad; }
}
