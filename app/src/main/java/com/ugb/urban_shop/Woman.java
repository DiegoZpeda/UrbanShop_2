package com.ugb.urban_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Woman extends AppCompatActivity {
    Bundle parametros = new Bundle();
    DbUrban db_shop;
    ListView lts;
    Cursor cRopa;
    FloatingActionButton btn;
    final ArrayList<ropas> alRopa = new ArrayList<ropas>();
    final ArrayList<ropas> alRopaCopy = new ArrayList<ropas>();
    ropas misRopas;
    JSONArray datosJSON; //para los datos que vienen del servidor
    JSONObject jsonObject;
    ProgressDialog progreso; //para la barra de progreso...
    obtenerDatosServidor datosServidor;
    int posicion = 0;
    @Override
    protected void onCreate(Bundle instance) {
        super.onCreate(instance);
        setContentView(R.layout.activity_woman);
        obtenerDatosRopa();
        buscarRopa();
        btn = findViewById(R.id.btnmujer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                abrirAgregarRopa(parametros);
            }
        });
    }
    public void abrirAgregarRopa(Bundle parametros) {
        Intent iAgregarRopa = new Intent(Woman.this, MainActivitywoman.class);
        iAgregarRopa.putExtras(parametros);
        startActivity(iAgregarRopa);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

          MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.mimenu, menu);
         try {
         AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
    //cRopa.moveToPosition(info.position);
         posicion=info.position;
         menu.setHeaderTitle(datosJSON.getJSONObject(posicion).getJSONObject("value").getString("marca")); //nombre del producto...

         }catch (Exception ex){
         Toast.makeText(getApplicationContext(), "Error al mostrar el menu: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
         }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try{
            switch (item.getItemId()){
                case R.id.mnxAgregar:
                    parametros.putString("accion", "nuevo");
                    abrirAgregarRopa(parametros);

                    return true;
                case R.id.mnxModificar:
                   /* String ropa[] = {
                            cRopa.getString(0), //idmujer
                            cRopa.getString(1), //codigo
                            cRopa.getString(2), //descripcion
                            cRopa.getString(3), //marca
                            cRopa.getString(4), //presentacion
                            cRopa.getString(5), //precio
                            cRopa.getString(6)  //foto->url

                    };*/
                    parametros.putString("accion", "modificar");
                    //parametros.putStringArray("ropas", datosJSON.getJSONObject(posicion).toString());
                    parametros.putString("ropas", datosJSON.getJSONObject(posicion).toString());
                    abrirAgregarRopa(parametros);
                    return true;
                case R.id.mnxEliminar:
                    eliminarDatosRopa();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }catch (Exception e){
            return super.onContextItemSelected(item);
        }
    }
    void eliminarDatosRopa(){
        try{
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(Woman.this);
            confirmacion.setTitle("Esta seguro de eliminar a: ");
            confirmacion.setMessage(cRopa.getString(2));
            confirmacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db_shop.administrar_mujer("","",cRopa.getString(2), "", "", "","","","","eliminar" );
                    obtenerDatosRopa();
                    dialogInterface.dismiss();
                }
            });
            confirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            confirmacion.create().show();
        }catch (Exception e){
            Toast.makeText(this, "Error al eliminar: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void obtenerDatosRopa(){
        try {
            db_shop = new DbUrban(Woman.this, "", null, 1);
            cRopa = db_shop.consultar_mujer();
            /*if(cRopa.moveToFirst()){
                lts = findViewById(R.id.ltsWoman);
                final ArrayAdapter<String> adropa = new ArrayAdapter<String>(Woman.this,
                        android.R.layout.simple_expandable_list_item_1, alRopa);
                lts.setAdapter(adropa);
                do{
                    //alRopa.add(cRopa.getString(2));//2 es el nombre del amigo, pues 0 es el idAmigo.
                    misRopas = new ropas(
                            cRopa.getString(0),//idRopa
                            cRopa.getString(1),//codigo
                            cRopa.getString(2),//descripcion
                            cRopa.getString(3),//marca
                            cRopa.getString(4),//presentacion
                            cRopa.getString(5),//precio
                            cRopa.getString(6) //urlFotoropa
                    );
                    alRopa.add(misRopas);
                }while(cRopa.moveToNext());
                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alRopa);
                lts.setAdapter(adImagenes);
                alRopaCopy.addAll(alRopa);
                //adropa.notifyDataSetChanged();
                registerForContextMenu(lts);
            }else{*/
            obtenerDatosRopasServer();
               // Toast.makeText(this, "NO HAY datos que mostrar", Toast.LENGTH_SHORT).show();
            //}
        }catch (Exception e){
            Toast.makeText(this, "Error al obtener amigos: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    void obtenerDatosRopasServer(){
        try {
            datosServidor = new obtenerDatosServidor();
            String data = datosServidor.execute().get();
            Log.d("RECIBIENDO: ", "DATA: "+ data);
            jsonObject = new JSONObject(data);
            datosJSON = jsonObject.getJSONArray("rows");
            mostrarDatosRopa();
        }catch (Exception ex){
            Toast.makeText(this, "Error al obtener datos desde el servidor: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    void mostrarDatosRopa(){
        try {
            if( datosJSON.length()>0 ){
                lts = findViewById(R.id.ltsWoman);
                alRopa.clear();
                alRopaCopy.clear();

                JSONObject misDatosJSONObject;
                for (int i=0; i<datosJSON.length(); i++){
                    misDatosJSONObject = datosJSON.getJSONObject(i).getJSONObject("value");
                    Log.d("MOSTRANDO: ", "DATA: "+ misDatosJSONObject.toString());

                    misRopas = new ropas(
                            "123",
                            misDatosJSONObject.getString("_rev"),
                            misDatosJSONObject.getString("idUnico"),
                            misDatosJSONObject.getString("codigo"),
                            misDatosJSONObject.getString("descripcion"),
                            misDatosJSONObject.getString("marca"),
                            misDatosJSONObject.getString("presentacion"),
                            misDatosJSONObject.getString("precio"),
                            misDatosJSONObject.getString("urlFoto")
                    );
                    alRopa.add(misRopas);
                }
                adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alRopa);
                lts.setAdapter(adImagenes);
                alRopaCopy.addAll(alRopa);
                //adAmigos.notifyDataSetChanged();
                registerForContextMenu(lts);
            }else{
                Toast.makeText(this, "No hay datos que mostrar", Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(this, "Error al mostrar datos ropas: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    void buscarRopa(){
        TextView temp = findViewById(R.id.txtBuscarAmigos);
        temp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    alRopa.clear();
                    String valor = temp.getText().toString().trim().toLowerCase();
                    if( valor.length()<=0 ){//es porque no esta escribiendo mostramos
                        // la lista completa de amigos
                        alRopa.addAll(alRopaCopy);
                    }else{ //si esta buscando amigos...
                        for(ropas ropas : alRopaCopy){
                            String idropas = ropas.getId();
                            //String codigo = ropas.getCodigo();
                            String descripcion = ropas.getDescripcion();
                            String marca = ropas.getMarca();
                            //String presentacion = ropas.getPresentacion();
                            String precio = ropas.getPresentacion();

                            if( idropas.toLowerCase().trim().contains(valor) ||
                                    descripcion.toLowerCase().trim().contains(valor) ||
                                    marca.toLowerCase().trim().contains(valor) ||
                                    precio.toLowerCase().trim().contains(valor)){
                                alRopa.add(ropas);
                            }
                        }
                        adaptadorImagenes adImagenes = new adaptadorImagenes(getApplicationContext(), alRopa);
                        lts.setAdapter(adImagenes);
                    }
                }catch (Exception e){
                    Toast.makeText(Woman.this, "Error al buscar amigos: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}