package com.ugb.urban_shop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivitywoman extends AppCompatActivity {
    DbUrban db_shop;
    String accion="nuevo";
    String id="";
    Button btn;
    TextView temp;
    ImageView img;
    String urlCompletaImg="";
    Intent tomarFotoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnGuardar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar_mujer();
            }
        });

        img = findViewById(R.id.imgropa);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFotoRopa();
            }
        });
        mostrar_datos_ropa();
    }
    void mostrar_datos_ropa(){
        try {
            Bundle parametros = getIntent().getExtras();
            accion = parametros.getString("accion");
            if (accion.equals("modificar")) {
                String ropa[] = parametros.getStringArray("ropa");
                id = ropa[0];

                temp = findViewById(R.id.txtcodigo);
                temp.setText(ropa[1]);

                temp = findViewById(R.id.txtdescripcion);
                temp.setText(ropa[2]);

                temp = findViewById(R.id.txtmarca);
                temp.setText(ropa[3]);

                temp = findViewById(R.id.txtpresentacion);
                temp.setText(ropa[4]);

                temp = findViewById(R.id.txtprecio);
                temp.setText(ropa[5]);

                urlCompletaImg = ropa[6];
                Bitmap bitmap = BitmapFactory.decodeFile(urlCompletaImg);
                img.setImageBitmap(bitmap);
            }
        }catch (Exception ex){
            Toast.makeText(this, "Error al mostrar los datos: "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    void guardar_mujer(){
        try {
            temp = (TextView) findViewById(R.id.txtcodigo);
            String codigo = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtdescripcion);
            String descripcion = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtmarca);
            String marca = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtpresentacion);
            String presentacion = temp.getText().toString();

            temp = (TextView) findViewById(R.id.txtprecio);
            String precio = temp.getText().toString();

            db_shop = new DbUrban(MainActivitywoman.this, "",null,1);
            String result = db_shop.administrar_mujer(id, codigo, descripcion, marca, presentacion,precio,urlCompletaImg, accion);
            String msg = result;
            if( result.equals("ok") ){
                msg = "Registro guardado con exito";
                regresarListaropa();
            }
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, "Error en guardar ropa: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    void regresarListaropa(){
        Intent ilistawoman = new Intent(MainActivitywoman.this, Woman.class);
        startActivity(ilistawoman);

    }
    private void tomarFotoRopa(){
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if( tomarFotoIntent.resolveActivity(getPackageManager())!=null ){
            File fotoRopa = null;
            try{
                fotoRopa = crearImagenRopa();
                if( fotoRopa!=null ){
                    Uri urifotoropa = FileProvider.getUriForFile(MainActivitywoman.this,
                            "com.ugb.miapp.fileprovider", fotoRopa);
                    tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, urifotoropa);
                    startActivityForResult(tomarFotoIntent, 1);
                }
            }catch (Exception ex){
                Toast.makeText(this, "Error al tomar la foto: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "NO se selecciono una foto... ", Toast.LENGTH_LONG).show();
        }
    }

    private File crearImagenRopa() throws Exception{
        String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "imagen_"+ fechaHoraMs +"_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if(dirAlmacenamiento.exists()==false ){
            dirAlmacenamiento.mkdirs();
        }
        File image = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        urlCompletaImg = image.getAbsolutePath();
        return image;
    }
}