package com.example.app_tareos.ADAPTADORES;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_tareos.MODEL.Empleado;
import com.example.app_tareos.R;

import java.util.List;

public class ADTREmpleadoFalta extends RecyclerView.Adapter<ADTREmpleadoFalta.wvhPersonas>{

    List<Empleado> aPV_alertas;
    private Context objPV_Contexto;

    public ADTREmpleadoFalta(Context mContext, List<Empleado> listaAlertas) {
        //System.out.println("SIZEE"+listaAlertas.size());
        this.objPV_Contexto = mContext;
        this.aPV_alertas = listaAlertas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ADTREmpleadoFalta.wvhPersonas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_falta,parent,false);
        ADTREmpleadoFalta.wvhPersonas holder = new ADTREmpleadoFalta.wvhPersonas(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ADTREmpleadoFalta.wvhPersonas holder, int position) {
        int objL_Posicion = position;
        Empleado objL_Empleados = aPV_alertas.get(position);

        holder.tvNombreEmpFal.setText(objL_Empleados.getPersona().getDatos()+"");
        holder.tvDocumentoTpEmpFal.setText(objL_Empleados.getPersona().getId_tpdocumento()+"");
        holder.tvDocumentoNrEmpFal.setText(objL_Empleados.getPersona().getPer_documento()+"");
        holder.tvSedeEmp.setText(objL_Empleados.getSede().getSe_descripcion()+"");
        holder.tvSeleccionarFal.setOnClickListener(new View.OnClickListener() {
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
        TextView tvNombreEmpFal;
        TextView tvDocumentoTpEmpFal;
        TextView tvDocumentoNrEmpFal;
        TextView tvSedeEmp;
        TextView tvSeleccionarFal;

        public wvhPersonas(@NonNull View itemView) {
            super(itemView);
            tvNombreEmpFal = itemView.findViewById(R.id.tvNombreEmpFal);
            tvDocumentoTpEmpFal = itemView.findViewById(R.id.tvDocumentoTpEmpFal);
            tvDocumentoNrEmpFal = itemView.findViewById(R.id.tvDocumentoNrEmpFal);
            tvSedeEmp = itemView.findViewById(R.id.tvSedeEmp);
            tvSeleccionarFal = itemView.findViewById(R.id.tvSeleccionarFal);
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
