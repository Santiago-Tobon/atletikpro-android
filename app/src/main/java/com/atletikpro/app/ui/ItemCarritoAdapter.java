package com.atletikpro.app.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atletikpro.app.R;
import com.atletikpro.app.model.Carrito;
import com.atletikpro.app.model.ItemCarrito;
import com.atletikpro.app.util.Formato;

public class ItemCarritoAdapter extends BaseAdapter {
    private final Context contexto;
    private final Runnable alCambiar;

    public ItemCarritoAdapter(Context contexto, Runnable alCambiar) { this.contexto = contexto; this.alCambiar = alCambiar; }

    @Override public int getCount() { return Carrito.getInstance().getItems().size(); }
    @Override public ItemCarrito getItem(int i) { return Carrito.getInstance().getItems().get(i); }
    @Override public long getItemId(int i) { return getItem(i).getProducto().getId(); }

    @Override public View getView(int pos, View vista, ViewGroup padre) {
        if (vista == null) vista = LayoutInflater.from(contexto).inflate(R.layout.item_carrito, padre, false);
        ItemCarrito item = getItem(pos);
        ((TextView) vista.findViewById(R.id.tvNombre)).setText(item.getProducto().getNombre());
        ((TextView) vista.findViewById(R.id.tvCantidad)).setText(String.valueOf(item.getCantidad()));
        ((TextView) vista.findViewById(R.id.tvSubtotal)).setText(Formato.moneda(item.subtotal()));
        vista.findViewById(R.id.btnMas).setOnClickListener(v -> {
            Carrito.getInstance().agregar(item.getProducto()); alCambiar.run();
        });
        vista.findViewById(R.id.btnMenos).setOnClickListener(v -> {
            Carrito.getInstance().disminuir(item.getProducto().getId()); alCambiar.run();
        });
        return vista;
    }
}
