package com.atletikpro.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atletikpro.app.R;
import com.atletikpro.app.data.ProductoRepository;
import com.atletikpro.app.data.SesionManager;
import com.atletikpro.app.model.Carrito;
import com.atletikpro.app.model.Producto;

import java.util.List;

/** Catálogo con búsqueda y filtro por categoría (RF3, RF5, RF6). */
public class CatalogoActivity extends AppCompatActivity {
    private ProductoRepository productos;
    private SesionManager sesion;
    private ProductoAdapter adapter;
    private EditText etBuscar;
    private Spinner spCategoria;
    private TextView tvVacio;
    private Button btnCarrito;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
        productos = new ProductoRepository(this);
        sesion = new SesionManager(this);
        etBuscar = findViewById(R.id.etBuscar);
        spCategoria = findViewById(R.id.spCategoria);
        tvVacio = findViewById(R.id.tvVacio);
        btnCarrito = findViewById(R.id.btnCarrito);
        ((TextView) findViewById(R.id.tvSaludo)).setText(getString(R.string.saludo, sesion.getNombre()));

        adapter = new ProductoAdapter(this, this::agregarAlCarrito);
        ((ListView) findViewById(R.id.lvProductos)).setAdapter(adapter);

        spCategoria.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Todas", "Deportivo", "Casual"}));
        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) { cargar(); }
            @Override public void onNothingSelected(AdapterView<?> p) { }
        });
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) { }
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) { }
            @Override public void afterTextChanged(Editable s) { cargar(); }
        });
        btnCarrito.setOnClickListener(v -> startActivity(new Intent(this, CarritoActivity.class)));
        findViewById(R.id.btnSalir).setOnClickListener(v -> {
            sesion.cerrar();
            Carrito.getInstance().vaciar();
            irALogin();
        });
    }

    @Override protected void onResume() {
        super.onResume();
        if (!sesion.activa()) { irALogin(); return; }
        sesion.renovar();
        btnCarrito.setText(getString(R.string.ver_carrito, Carrito.getInstance().cantidadTotal()));
    }

    private void agregarAlCarrito(Producto p) {
        Carrito.getInstance().agregar(p);
        btnCarrito.setText(getString(R.string.ver_carrito, Carrito.getInstance().cantidadTotal()));
        Toast.makeText(this, getString(R.string.agregado, p.getNombre()), Toast.LENGTH_SHORT).show();
    }

    private void cargar() {
        String categoria = spCategoria.getSelectedItemPosition() == 0 ? null : (String) spCategoria.getSelectedItem();
        List<Producto> lista = productos.buscar(etBuscar.getText().toString(), categoria);
        adapter.actualizar(lista);
        tvVacio.setVisibility(lista.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void irALogin() {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
