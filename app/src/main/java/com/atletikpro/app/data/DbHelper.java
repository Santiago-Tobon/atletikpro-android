package com.atletikpro.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** Base de datos local SQLite: usuarios y catálogo de productos. */
public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static DbHelper instancia;

    // nombre, marca, categoría, tallas, precio (COP). Datos de ejemplo.
    private static final String[][] SEMILLA = {
        {"Zapatilla Running Aero", "Nike", "Deportivo", ",38,39,40,41,42,", "329900"},
        {"Zapatilla Training Flex", "Nike", "Deportivo", ",39,40,41,42,43,", "289900"},
        {"Botín Fútbol Striker", "Adidas", "Deportivo", ",38,39,40,41,", "259900"},
        {"Zapatilla Baloncesto Court", "Adidas", "Deportivo", ",40,41,42,43,", "349900"},
        {"Zapatilla Urbana Classic", "Adidas", "Casual", ",37,38,39,40,41,", "219900"},
        {"Zapatilla Running Velocity", "Puma", "Deportivo", ",38,39,40,41,42,", "269900"},
        {"Zapatilla Street Retro", "Puma", "Casual", ",37,38,39,40,", "199900"},
        {"Zapatilla Casual Daily", "Reebok", "Casual", ",38,39,40,41,42,", "179900"},
        {"Zapatilla Training Pro", "Reebok", "Deportivo", ",39,40,41,42,43,", "239900"},
        {"Zapatilla Urbana Wave", "Nike", "Casual", ",37,38,39,40,41,", "309900"},
    };

    public static synchronized DbHelper getInstance(Context c) {
        if (instancia == null) instancia = new DbHelper(c.getApplicationContext());
        return instancia;
    }

    private DbHelper(Context c) { super(c, "atletikpro.db", null, VERSION); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, "
                + "correo TEXT NOT NULL UNIQUE, sal TEXT NOT NULL, hash TEXT NOT NULL)");
        db.execSQL("CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, "
                + "marca TEXT NOT NULL, categoria TEXT NOT NULL, tallas TEXT NOT NULL, precio REAL NOT NULL)");
        for (String[] f : SEMILLA) {
            ContentValues v = new ContentValues();
            v.put("nombre", f[0]); v.put("marca", f[1]); v.put("categoria", f[2]);
            v.put("tallas", f[3]); v.put("precio", Double.parseDouble(f[4]));
            db.insert("productos", null, v);
        }
    }

    @Override public void onUpgrade(SQLiteDatabase db, int vieja, int nueva) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS productos");
        onCreate(db);
    }
}
