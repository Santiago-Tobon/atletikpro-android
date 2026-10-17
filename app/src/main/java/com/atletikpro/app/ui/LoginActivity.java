package com.atletikpro.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.atletikpro.app.R;
import com.atletikpro.app.data.SesionManager;
import com.atletikpro.app.data.UsuarioRepository;
import com.atletikpro.app.util.Validador;

/** Inicio de sesión (RF2). */
public class LoginActivity extends AppCompatActivity {
    private UsuarioRepository usuarios;
    private SesionManager sesion;
    private EditText etCorreo, etContrasena;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sesion = new SesionManager(this);
        if (sesion.activa()) { irACatalogo(); return; }
        setContentView(R.layout.activity_login);
        usuarios = new UsuarioRepository(this);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        findViewById(R.id.btnIngresar).setOnClickListener(v -> intentarIngreso());
        findViewById(R.id.tvIrRegistro).setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));
    }

    private void intentarIngreso() {
        String correo = etCorreo.getText().toString().trim();
        String pass = etContrasena.getText().toString();
        if (!Validador.correoValido(correo)) { etCorreo.setError(getString(R.string.error_correo)); return; }
        if (pass.isEmpty()) { etContrasena.setError(getString(R.string.error_contrasena_vacia)); return; }

        String nombre = usuarios.iniciarSesion(correo, pass);
        if (nombre == null) {
            Toast.makeText(this, R.string.error_credenciales, Toast.LENGTH_LONG).show();
            return;
        }
        sesion.iniciar(nombre);
        irACatalogo();
    }

    private void irACatalogo() {
        startActivity(new Intent(this, CatalogoActivity.class));
        finish();
    }
}
