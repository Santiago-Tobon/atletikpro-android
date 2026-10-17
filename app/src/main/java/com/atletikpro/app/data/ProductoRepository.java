package com.atletikpro.app.data;

import android.content.Context;
import android.database.Cursor;

import com.atletikpro.app.model.Producto;

import java.util.ArrayList;
import java.util.List;

/** Consulta del catálogo con búsqueda y filtro (RF3, RF5, RF6). */
public class ProductoRepository {
    private final DbHelper helper;

    public ProductoRepository(Context c) { helper = DbHelper.getInstance(c); }

    /** @param texto busca en nombre o marca; @param categoria null = todas. */
    public List<Producto> buscar(String texto, String categoria) {
        StringBuilder where = new StringBuilder("1=1");
        List<String> args = new ArrayList<>();
        if (texto != null && !texto.trim().isEmpty()) {
            where.append(" AND (nombre LIKE ? OR marca LIKE ?)");
            String like = "%" + texto.trim() + "%";
            args.add(like); args.add(like);
        }
        if (categoria != null) { where.append(" AND categoria = ?"); args.add(categoria); }

        List<Producto> lista = new ArrayList<>();
        try (Cursor c = helper.getReadableDatabase().query("productos", null, where.toString(),
                args.toArray(new String[0]), null, null, "marca, nombre")) {
            while (c.moveToNext()) {
                lista.add(new Producto(c.getLong(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("nombre")),
                        c.getString(c.getColumnIndexOrThrow("marca")),
                        c.getString(c.getColumnIndexOrThrow("categoria")),
                        c.getString(c.getColumnIndexOrThrow("tallas")),
                        c.getDouble(c.getColumnIndexOrThrow("precio"))));
            }
        }
        return lista;
    }
}
