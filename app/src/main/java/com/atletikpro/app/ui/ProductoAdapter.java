package com.atletikpro.app.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.atletikpro.app.R;
import com.atletikpro.app.model.Producto;
import com.atletikpro.app.util.Formato;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends BaseAdapter {
    public interface OnAgregar { void alAgregar(Producto p); }

    private final Context contexto;
    private final List<Producto> datos = new ArrayList<>();
    private final OnAgregar oyente;

    public ProductoAdapter(Context contexto, OnAgregar oyente) { this.contexto = contexto; this.oyente = oyente; }

    public void actualizar(List<Producto> nuevos) { datos.clear(); datos.addAll(nuevos); notifyDataSetChanged(); }

    @Override public int getCount() { return datos.size(); }
    @Override public Producto getItem(int i) { return datos.get(i); }
    @Override public long getItemId(int i) { return datos.get(i).getId(); }

    @Override public View getView(int pos, View vista, ViewGroup padre) {
        if (vista == null) vista = LayoutInflater.from(contexto).inflate(R.layout.item_producto, padre, false);
        Producto p = getItem(pos);
        ((TextView) vista.findViewById(R.id.tvNombre)).setText(p.getNombre());
        ((TextView) vista.findViewById(R.id.tvDetalle)).setText(
                contexto.getString(R.string.detalle_producto, p.getMarca(), p.getCategoria(), p.getTallasTexto()));
        ((TextView) vista.findViewById(R.id.tvPrecio)).setText(Formato.moneda(p.getPrecio()));
        ((Button) vista.findViewById(R.id.btnAgregar)).setOnClickListener(v -> oyente.alAgregar(p));
        return vista;
    }
}
