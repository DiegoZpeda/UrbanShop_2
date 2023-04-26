package com.ugb.urban_shop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class adaptadorImagenes extends BaseAdapter {
    Context context;
    ArrayList<ropas> datosRopaArrayList;
    ropas misRopas;
    LayoutInflater layoutInflater;

    public adaptadorImagenes(Context context, ArrayList<ropas> datosRopaArrayList){
        this.context = context;
        this.datosRopaArrayList = datosRopaArrayList;
    }
    @Override
    public int getCount() {
        return datosRopaArrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return datosRopaArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(datosRopaArrayList.get(i).getIdropas());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.listview_image, viewGroup, false);
        try {
            misRopas = datosRopaArrayList.get(i);

            TextView tempVal = itemView.findViewById(R.id.lblDescripcion);
            tempVal.setText(misRopas.getDescripcion());

            tempVal = itemView.findViewById(R.id.lblMarca);
            tempVal.setText(misRopas.getMarca());

            tempVal = itemView.findViewById(R.id.lblPrecio);
            tempVal.setText(misRopas.getPrecio());

            ImageView imgView = itemView.findViewById(R.id.imgFoto);
            Bitmap bitmap = BitmapFactory.decodeFile(misRopas.getUrlImg());
            imgView.setImageBitmap(bitmap);
        }catch (Exception ex){
            Toast.makeText(context, "Error al mostrar la foto en el ListView: "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}
