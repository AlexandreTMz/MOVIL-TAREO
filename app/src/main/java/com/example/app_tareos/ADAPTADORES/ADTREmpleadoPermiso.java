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

public class ADTREmpleadoPermiso extends RecyclerView.Adapter<ADTREmpleadoPermiso.wvhPersonas>{

    List<Empleado> aPV_alertas;
    private Context objPV_Contexto;

    public ADTREmpleadoPermiso(Context mContext, List<Empleado> listaAlertas) {
        //System.out.println("SIZEE"+listaAlertas.size());
        this.objPV_Contexto = mContext;
        this.aPV_alertas = listaAlertas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ADTREmpleadoPermiso.wvhPersonas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_permiso,parent,false);
        ADTREmpleadoPermiso.wvhPersonas holder = new ADTREmpleadoPermiso.wvhPersonas(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ADTREmpleadoPermiso.wvhPersonas holder, int position) {
        int objL_Posicion = position;
        Empleado objL_Empleados = aPV_alertas.get(position);

        holder.tvNombreEmpPer.setText(objL_Empleados.getPersona().getDatos()+"");
        holder.tvDocumentoTpEmpPer.setText(objL_Empleados.getPersona().getId_tpdocumento()+"");
        holder.tvDocumentoNrEmpPer.setText(objL_Empleados.getPersona().getPer_documento()+"");
        holder.tvSedeEmp.setText(objL_Empleados.getSede().getSe_descripcion()+"");
        holder.tvSeleccionarPer.setOnClickListener(new View.OnClickListener() {
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
        TextView tvNombreEmpPer;
        TextView tvDocumentoTpEmpPer;
        TextView tvDocumentoNrEmpPer;
        TextView tvSedeEmp;
        TextView tvSeleccionarPer;

        public wvhPersonas(@NonNull View itemView) {
            super(itemView);
            tvNombreEmpPer = itemView.findViewById(R.id.tvNombreEmpPer);
            tvDocumentoTpEmpPer = itemView.findViewById(R.id.tvDocumentoTpEmpPer);
            tvDocumentoNrEmpPer = itemView.findViewById(R.id.tvDocumentoNrEmpPer);
            tvSedeEmp = itemView.findViewById(R.id.tvSedeEmp);
            tvSeleccionarPer = itemView.findViewById(R.id.tvSeleccionarPer);
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
