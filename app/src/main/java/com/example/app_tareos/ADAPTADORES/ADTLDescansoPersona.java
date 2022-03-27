package com.example.app_tareos.ADAPTADORES;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.app_tareos.MODEL.DescansoPersona;
import com.example.app_tareos.R;

import java.util.List;

public class ADTLDescansoPersona extends BaseAdapter {

    protected Activity activity;
    protected List<DescansoPersona> items;

    //costructor en el cual enviaremos informacion
    public ADTLDescansoPersona(Activity actividad, List<DescansoPersona> items) {
        this.activity = actividad;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;  //Creamos nuestro objeto ViewHolder
        if (convertView==null){
            //Inflamos el layout de nuestros items en nuestra vista v
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.listview_personas_descanso,null);
            holder = new ViewHolder();  // Lo asingamos la primera vez
            //instanciamos el ImageView o el TextView de nuestro Layout para los items.
            holder.tvNombre = convertView.findViewById(R.id.tvNombre);
            holder.tvError = convertView.findViewById(R.id.tvError);
            holder.imgStatus = convertView.findViewById(R.id.imgStatus);

            convertView.setTag(holder);  // establecemos como tag del convertView el objeto holder
        }
        else{
            holder=(ViewHolder) convertView.getTag();   //obtenemos el tag.
        }
        //Obtenemos el nombre de la lista según la posición que nos pasan.
        DescansoPersona objWebs = items.get(position);
        //En el textView si ponemos el nombre.
        holder.tvNombre.setText(objWebs.getPersona());
        holder.tvError.setText(objWebs.getError());

        return convertView;
    }

    // The ViewHolder, only one item for simplicity and demonstration purposes, you can put all the views inside a row of the list into this ViewHolder
    private static class ViewHolder {
        TextView tvNombre;
        TextView tvError;
        ImageView imgStatus;
    }

    public void addAll(List<DescansoPersona> Producto){
        for (int i= 0; i<Producto.size(); i++) {
            items.add(Producto.get(i));
        }
    }

}