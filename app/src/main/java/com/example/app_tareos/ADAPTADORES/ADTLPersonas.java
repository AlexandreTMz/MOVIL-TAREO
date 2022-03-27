package com.example.app_tareos.ADAPTADORES;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.app_tareos.MODEL.Persona;
import com.example.app_tareos.R;

import java.util.List;

public class ADTLPersonas extends BaseAdapter {

    public Activity activity;
    public List<Persona> items;

    //costructor en el cual enviaremos informacion
    public ADTLPersonas(Activity actividad, List<Persona> items, ButtonCallback btButtonCallback) {
        this.activity = actividad;
        this.items = items;
        this.buttonCallback = btButtonCallback;
    }

    public ADTLPersonas(Activity actividad, List<Persona> items) {
        this.activity = actividad;
        this.items = items;
    }

    public void setButtonCallback(ButtonCallback btButtonCallback){
        this.buttonCallback = btButtonCallback;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.listview_personas_suplente, null);
        }

        //creamos un objeto de la clase WebsEsTl
        Persona persona = items.get(position);

        //Asignamos los recursos a las variable
        TextView tvLsvNombre = v.findViewById(R.id.tvLsvNombre);
        TextView tvLsvDocumento = v.findViewById(R.id.tvLsvDocumento);
        Button btnSeleccionar=  v.findViewById(R.id.btnLsvSeleccionar);
        TextView tvLsvBanco = v.findViewById(R.id.tvLsvBanco);
        TextView tvLsvCuenta = v.findViewById(R.id.tvLsvCuenta);

        //Enviamos informacion a la vista apartir de la informacion que contenga la clase:
        tvLsvNombre.setText(persona.getDatos());
        tvLsvDocumento.setText(persona.getPer_documento());
        tvLsvBanco.setText(persona.getBa_abreviatura());
        tvLsvCuenta.setText(persona.getPhb_cci());

        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCallback.onClickButton(v, position);
            }
        });

        return v;
    }

    public void addAll(List<Persona> Producto){
        for (int i= 0; i<Producto.size(); i++) {
            items.add(Producto.get(i));
        }
        this.notifyDataSetChanged();
    }

    // PARADIGMA OLLENTE
    public ButtonCallback buttonCallback;
    public interface ButtonCallback{
        void onClickButton(View v, int position);
    }

}