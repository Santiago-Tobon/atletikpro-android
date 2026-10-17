package com.atletikpro.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.atletikpro.app.R;
import com.atletikpro.app.model.Carrito;
import com.atletikpro.app.util.Formato;

/** Carrito de compras (RF7): agregar, quitar y ver el total. */
public class CarritoActivity extends AppCompatActivity {
    private ItemCarritoAdapter adapter;
    private TextView tvTotal, tvVacio;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        tvTotal = findViewById(R.id.tvTotal);
        tvVacio = findViewById(R.id.tvVacio);
        adapter = new ItemCarritoAdapter(this, this::refrescar);
        ((ListView) findViewById(R.id.lvCarrito)).setAdapter(adapter);

        findViewById(R.id.btnVaciar).setOnClickListener(v -> { Carrito.getInstance().vaciar(); refrescar(); });
        findViewById(R.id.btnVolver).setOnClickListener(v -> finish());
        findViewById(R.id.btnPagar).setOnClickListener(v -> {
            if (Carrito.getInstance().estaVacio()) return;
            new AlertDialog.Builder(this)
                    .setTitle(R.string.pagar)
                    .setMessage(getString(R.string.resumen_pago, Formato.moneda(Carrito.getInstance().total())))
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        });
        refrescar();
    }

    private void refrescar() {
        adapter.notifyDataSetChanged();
        tvTotal.setText(getString(R.string.total, Formato.moneda(Carrito.getInstance().total())));
        tvVacio.setVisibility(Carrito.getInstance().estaVacio() ? View.VISIBLE : View.GONE);
    }
}
