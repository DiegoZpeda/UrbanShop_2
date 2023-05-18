package com.ugb.urban_shop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class login2 extends AppCompatActivity {
    EditText username, passw;
    private Cursor fila;
    String Title = "Inicio de Sesion";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        this.setTitle(Title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_launcher_background);
        username = (EditText) findViewById(R.id.Username);
        passw = (EditText) findViewById(R.id.Pass);
    }

    public void  InicioSesion(View view){
        DBUSER admin = new DBUSER(this, "ropas", null, 1);
        SQLiteDatabase db=admin.getWritableDatabase();
        String usuario = username.getText().toString();
        String contrasena = passw.getText().toString();
        fila=db.rawQuery("select username,clave_user from userstable where username='"+
                usuario + "' and clave_user = '" + contrasena +"'", null);
        try {
            if (fila.moveToFirst()){
                String usua=fila.getString(0);
                String pass=fila.getString(1);
                if(usuario.equals(usua)&&contrasena.equals(pass)){
                    Intent ven=new Intent(this, MainActivity2.class);
                    ven.putExtra("usuario",usuario);
                    startActivityForResult(ven, 1234);
                    startActivity(ven);
                    username.setText("");
                    passw.setText("");
                }
            }else {
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void RegistroData(View view){
        Intent rdata=new Intent(this, RegistroData.class);
        startActivity(rdata);
    }
}