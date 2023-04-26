package com.ugb.urban_shop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbUrban extends SQLiteOpenHelper {
    public static final String dbname = "db_shop";
    public static final int v = 1;
    static final String sqlDb = "CREATE TABLE mujer(idmujer integer primary key autoincrement, codigo text, descripcion text, marca text, presentacion text, precio text, urlfoto text)";

    public DbUrban(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {sqLiteDatabase.execSQL(sqlDb) ;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public String administrar_mujer(String id, String cod, String des, String mar, String pres, String prec,String urlfoto, String accion){
        try{
            SQLiteDatabase db = getWritableDatabase();
            if(accion.equals("nuevo")){
                db.execSQL("INSERT INTO mujer(codigo,descripcion,marca,presentacion,precio,urlfoto) VALUES('"+cod+"','"+des+"','"+mar+"','"+pres+"','"+prec+"','"+urlfoto+"')");

            } else if (accion.equals("modificar")) {
                db.execSQL("UPDATE mujer SET codigo='"+cod+"', descripcion='"+des+"', marca='"+mar+"', presentacion='"+pres+"',precio='"+prec+"',urlfoto='"+urlfoto+"' WHERE idmujer='"+id+"'");

            } else if (accion.equals("eliminar")){
                db.execSQL("DELETE FROM mujer WHERE idmujer='"+id+"'");

            }
            return "ok";
        }catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    public Cursor consultar_mujer(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM mujer ORDER BY codigo";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

}


