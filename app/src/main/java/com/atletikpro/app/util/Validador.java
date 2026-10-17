package com.atletikpro.app.util;

import java.util.regex.Pattern;

/** Reglas de validación de formularios. */
public final class Validador {
    private static final Pattern CORREO = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private Validador() { }

    public static boolean nombreValido(String n) { return n != null && n.trim().length() >= 3; }
    public static boolean correoValido(String c) { return c != null && CORREO.matcher(c.trim()).matches(); }

    /** Mínimo 8 caracteres, con al menos una letra y un número. */
    public static boolean contrasenaValida(String p) {
        if (p == null || p.length() < 8) return false;
        boolean letra = false, numero = false;
        for (char c : p.toCharArray()) {
            if (Character.isLetter(c)) letra = true;
            if (Character.isDigit(c)) numero = true;
        }
        return letra && numero;
    }
}
