package com.example.app_tareos.ADAPTADORES;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_tareos.MODEL.Empleado;
import com.example.app_tareos.MODEL.Persona;
import com.example.app_tareos.R;

import java.util.List;

public class ADTRPersonas extends RecyclerView.Adapter<ADTRPersonas.wvhPersonas>{

    List<Persona> aPV_alertas;
    private Context objPV_Contexto;

    public ADTRPersonas(Context mContext, List<Persona> listaAlertas) {
        //System.out.println("SIZEE"+listaAlertas.size());
        this.objPV_Contexto = mContext;
        this.aPV_alertas = listaAlertas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ADTRPersonas.wvhPersonas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_empleados,parent,false);
        ADTRPersonas.wvhPersonas holder = new ADTRPersonas.wvhPersonas(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ADTRPersonas.wvhPersonas holder, int position) {
        int objL_Posicion = position;
        Persona objL_Empleados = aPV_alertas.get(position);

        holder.tvNombreEmp.setText(objL_Empleados.getDatos()+"");
        holder.tvDocumentoTpEmp.setText(objL_Empleados.getId_tpdocumento()+"");
        holder.tvDocumentoNrEmp.setText(objL_Empleados.getPer_documento()+"");
        holder.tvBancoEmp.setText(objL_Empleados.getBa_abreviatura()+"");
        holder.tvCuentaEmp.setText(objL_Empleados.getPhb_cci()+"");

        holder.tvSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(objPV_Contexto, "Editar",Toast.LENGTH_SHORT).show();
                objP_Listeners.onClickSeleccionar(view, objL_Posicion);
            }
        });

    }

    @Override
    public int getItemCount() {
        return aPV_alertas.size();
    }

    public class wvhPersonas extends RecyclerView.ViewHolder{
        TextView tvNombreEmp;
        TextView tvDocumentoTpEmp;
        TextView tvDocumentoNrEmp;
        TextView tvBancoEmp;
        TextView tvCuentaEmp;
        TextView tvSeleccionar;

        public wvhPersonas(@NonNull View itemView) {
            super(itemView);
            tvNombreEmp = itemView.findViewById(R.id.tvNombreEmp);
            tvDocumentoTpEmp = itemView.findViewById(R.id.tvDocumentoTpEmp);
            tvDocumentoNrEmp = itemView.findViewById(R.id.tvDocumentoNrEmp);
            tvBancoEmp = itemView.findViewById(R.id.tvBancoEmp);
            tvCuentaEmp = itemView.findViewById(R.id.tvCuentaEmp);
            tvSeleccionar = itemView.findViewById(R.id.tvSeleccionar);
        }
    }

    // PARADIGMA OLLENTE
    public OnClicksListener objP_Listeners;
    public interface OnClicksListener{
        void onClickSeleccionar(View v, int position);
    }

    public void setOnClicksListenerCustom(OnClicksListener onClicksListener){
        this.objP_Listeners = onClicksListener;
    }


}
