package com.atletikpro.app.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class Formato {
    private Formato() { }
    public static String moneda(double valor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));
        nf.setMaximumFractionDigits(0);
        return nf.format(valor);
    }
}
