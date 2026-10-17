package com.atletikpro.app.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/** Almacenamiento de contraseñas con sal y SHA-256 (RNF1: nunca en texto plano). */
public final class Seguridad {
    private Seguridad() { }

    public static String generarSal() {
        byte[] b = new byte[16];
        new SecureRandom().nextBytes(b);
        return aHex(b);
    }

    public static String hash(String contrasena, String sal) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return aHex(md.digest((sal + contrasena).getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean verificar(String contrasena, String sal, String hashGuardado) {
        return MessageDigest.isEqual(hash(contrasena, sal).getBytes(StandardCharsets.UTF_8),
                hashGuardado.getBytes(StandardCharsets.UTF_8));
    }

    private static String aHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte x : bytes) sb.append(String.format("%02x", x));
        return sb.toString();
    }
}
