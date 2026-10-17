package com.atletikpro.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.atletikpro.app.util.Seguridad;

/** Acceso a datos de usuarios (RF1, RF2). */
public class UsuarioRepository {
    private final DbHelper helper;

    public UsuarioRepository(Context c) { helper = DbHelper.getInstance(c); }

    public boolean existeCorreo(String correo) {
        try (Cursor c = helper.getReadableDatabase().query("usuarios", new String[]{"id"},
                "correo = ?", new String[]{correo.trim().toLowerCase()}, null, null, null)) {
            return c.moveToFirst();
        }
    }

    /** @return true si se creó la cuenta. */
    public boolean registrar(String nombre, String correo, String contrasena) {
        String sal = Seguridad.generarSal();
        ContentValues v = new ContentValues();
        v.put("nombre", nombre.trim());
        v.put("correo", correo.trim().toLowerCase());
        v.put("sal", sal);
        v.put("hash", Seguridad.hash(contrasena, sal));
        return helper.getWritableDatabase().insert("usuarios", null, v) != -1;
    }

    /** @return el nombre del usuario si las credenciales son correctas; null si no. */
    public String iniciarSesion(String correo, String contrasena) {
        SQLiteDatabase db = helper.getReadableDatabase();
        try (Cursor c = db.query("usuarios", new String[]{"nombre", "sal", "hash"},
                "correo = ?", new String[]{correo.trim().toLowerCase()}, null, null, null)) {
            if (c.moveToFirst() && Seguridad.verificar(contrasena, c.getString(1), c.getString(2))) {
                return c.getString(0);
            }
            return null;
        }
    }
}
