package com.atletikpro.app.data;

import android.content.Context;
import android.content.SharedPreferences;

/** Sesión del usuario; expira tras 20 minutos de inactividad (requisito del cliente). */
public class SesionManager {
    private static final long EXPIRA_MS = 20 * 60 * 1000L;
    private final SharedPreferences prefs;

    public SesionManager(Context c) { prefs = c.getSharedPreferences("sesion", Context.MODE_PRIVATE); }

    public void iniciar(String nombre) {
        prefs.edit().putString("nombre", nombre).putLong("ultima", System.currentTimeMillis()).apply();
    }

    public boolean activa() {
        if (!prefs.contains("nombre")) return false;
        if (System.currentTimeMillis() - prefs.getLong("ultima", 0) > EXPIRA_MS) { cerrar(); return false; }
        return true;
    }

    public void renovar() {
        if (prefs.contains("nombre")) prefs.edit().putLong("ultima", System.currentTimeMillis()).apply();
    }

    public String getNombre() { return prefs.getString("nombre", ""); }
    public void cerrar() { prefs.edit().clear().apply(); }
}
