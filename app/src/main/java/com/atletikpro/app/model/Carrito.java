package com.atletikpro.app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/** Carrito de compras (RF7). Patrón Singleton para compartirlo entre pantallas. */
public class Carrito {
    private static Carrito instancia;
    private final List<ItemCarrito> items = new ArrayList<>();

    public static synchronized Carrito getInstance() {
        if (instancia == null) instancia = new Carrito();
        return instancia;
    }

    public void agregar(Producto p) {
        for (ItemCarrito i : items) {
            if (i.getProducto().getId() == p.getId()) { i.incrementar(); return; }
        }
        items.add(new ItemCarrito(p, 1));
    }

    /** Resta una unidad; si queda en cero, quita el producto. */
    public void disminuir(long productoId) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            ItemCarrito i = it.next();
            if (i.getProducto().getId() == productoId) {
                if (i.getCantidad() > 1) i.decrementar(); else it.remove();
                return;
            }
        }
    }

    public void vaciar() { items.clear(); }
    public boolean estaVacio() { return items.isEmpty(); }
    public List<ItemCarrito> getItems() { return Collections.unmodifiableList(items); }

    public int cantidadTotal() {
        int n = 0;
        for (ItemCarrito i : items) n += i.getCantidad();
        return n;
    }

    public double total() {
        double t = 0;
        for (ItemCarrito i : items) t += i.subtotal();
        return t;
    }
}
