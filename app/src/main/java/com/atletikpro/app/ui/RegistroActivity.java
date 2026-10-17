package com.atletikpro.app.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atletikpro.app.R;
import com.atletikpro.app.data.UsuarioRepository;
import com.atletikpro.app.util.Validador;

/** Registro de usuarios con correo y contraseña (RF1). */
public class RegistroActivity extends AppCompatActivity {
    private UsuarioRepository usuarios;
    private EditText etNombre, etCorreo, etContrasena, etConfirmar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        usuarios = new UsuarioRepository(this);
        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etConfirmar = findViewById(R.id.etConfirmar);
        findViewById(R.id.btnRegistrar).setOnClickListener(v -> registrar());
    }

    private void registrar() {
        String nombre = etNombre.getText().toString();
        String correo = etCorreo.getText().toString();
        String pass = etContrasena.getText().toString();

        if (!Validador.nombreValido(nombre)) { etNombre.setError(getString(R.string.error_nombre)); return; }
        if (!Validador.correoValido(correo)) { etCorreo.setError(getString(R.string.error_correo)); return; }
        if (!Validador.contrasenaValida(pass)) { etContrasena.setError(getString(R.string.error_contrasena)); return; }
        if (!pass.equals(etConfirmar.getText().toString())) { etConfirmar.setError(getString(R.string.error_confirmar)); return; }
        if (usuarios.existeCorreo(correo)) { etCorreo.setError(getString(R.string.error_correo_existe)); return; }

        if (usuarios.registrar(nombre, correo, pass)) {
            Toast.makeText(this, R.string.registro_ok, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
