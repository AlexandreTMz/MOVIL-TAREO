package com.example.app_tareos.ADAPTADORES;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.app_tareos.MODEL.Empleado;
import com.example.app_tareos.MODEL.Persona;
import com.example.app_tareos.R;

import java.util.List;

public class ADTLEmpleado extends BaseAdapter {

    public Activity activity;
    public List<Empleado> items;

    //costructor en el cual enviaremos informacion
    public ADTLEmpleado(Activity actividad, List<Empleado> items, ButtonCallback btButtonCallback) {
        this.activity = actividad;
        this.items = items;
        this.buttonCallback = btButtonCallback;
    }

    public ADTLEmpleado(Activity actividad, List<Empleado> items) {
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
            v = inf.inflate(R.layout.listview_personas_empleado, null);
        }

        //creamos un objeto de la clase WebsEsTl
        Empleado empleado = items.get(position);

        //Asignamos los recursos a las variable
        TextView tvLsvNombre =  v.findViewById(R.id.tvLsvNombre);
        TextView tvLsvDocumento =  v.findViewById(R.id.tvLsvDocumento);
        TextView tvLsvSede =  v.findViewById(R.id.tvLsvSede);
        Button btnSeleccionar=  v.findViewById(R.id.btnLsvSeleccionar);

        //Enviamos informacion a la vista apartir de la informacion que contenga la clase:
        tvLsvNombre.setText(empleado.getPersona().getDatos());
        tvLsvDocumento.setText(empleado.getPersona().getPer_documento());
        tvLsvSede.setText(empleado.getSede().getSe_lugar()+" / "+empleado.getSede().getSe_descripcion());

        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCallback.onClickButton(v, position);
            }
        });

        return v;
    }

    public void addAll(List<Empleado> Producto){
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