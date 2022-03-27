package com.example.app_tareos.ADAPTADORES;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_tareos.MODEL.Empleado;
import com.example.app_tareos.R;

import java.util.List;

public class ADTREmpleadoLista extends RecyclerView.Adapter<ADTREmpleadoLista.wvhPersonas>{

    List<Empleado> aPV_alertas;
    private Context objPV_Contexto;

    public ADTREmpleadoLista(Context mContext, List<Empleado> listaAlertas) {
        System.out.println("SIZEE"+listaAlertas.size());
        this.objPV_Contexto = mContext;
        this.aPV_alertas = listaAlertas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ADTREmpleadoLista.wvhPersonas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_personas_sede,parent,false);
        ADTREmpleadoLista.wvhPersonas holder = new ADTREmpleadoLista.wvhPersonas(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ADTREmpleadoLista.wvhPersonas holder, int position) {
        int objL_Posicion = position;
        Empleado objL_Empleados = aPV_alertas.get(position);
        holder.tvwNombreEm.setText(objL_Empleados.getPersona().getDatos()+"");
        holder.tvwMesReg.setText(objL_Empleados.getMES()+"");
        holder.tvwDiaReg.setText(objL_Empleados.getDIA()+"");
        holder.tvwAnioReg.setText(objL_Empleados.getANIO()+"");
        holder.tvwTpDocEm.setText(objL_Empleados.getPersona().getId_tpdocumento()+"");
        holder.tvwNrDocEm.setText(objL_Empleados.getPersona().getPer_documento()+"");
        holder.tvwNacionaldiadEm.setText(objL_Empleados.getPersona().getNacionalidad().getNa_descripcion()+"");
        holder.tvwCcEm.setText(objL_Empleados.getCuentaCc().isEmpty() ? objL_Empleados.getCuentaCci() : objL_Empleados.getCuentaCc());
        holder.tvwBancoEm.setText(objL_Empleados.getBanco().getBa_nombre()+"");
        holder.tvwTipoTeEm.setText(objL_Empleados.getTitular()+"");
        holder.tvwTipoOperaEm.setText(objL_Empleados.getCargo().getDatos()+"");
        /*holder.tvDocumentoTpEmpFal.setText(objL_Empleados.getPersona().getId_tpdocumento()+"");
        holder.tvDocumentoNrEmpFal.setText(objL_Empleados.getPersona().getPer_documento()+"");
        holder.tvSedeEmp.setText(objL_Empleados.getSede().getSe_descripcion()+"");
        */
        holder.imgVerMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(objPV_Contexto, "Editar",Toast.LENGTH_SHORT).show();
                //objP_Listeners.onClickSeleccionar(view, objL_Posicion);
                if(holder.getAdapterPosition() == objL_Posicion) {
                    holder.mtd_MostrarMas(objL_Empleados.isBln_IsVerMas());
                    objL_Empleados.setBln_IsVerMas(!objL_Empleados.isBln_IsVerMas());
                }
                else{
                    holder.mtd_MostrarMas(objL_Empleados.isBln_IsVerMas());
                    objL_Empleados.setBln_IsVerMas(false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return aPV_alertas.size();
    }

    float objL_HightList;
    float objL_MinValor = 106;
    float objL_MaxValor = 146;

    public class wvhPersonas extends RecyclerView.ViewHolder{
        TextView tvwDiaReg;
        TextView tvwMesReg;
        TextView tvwAnioReg;
        TextView tvwNombreEm;
        TextView tvwTpDocEm;
        TextView tvwNrDocEm;
        TextView tvwTipoTeEm;
        TextView tvwNacionaldiadEm;
        TextView tvwTipoOperaEm;
        TextView tvwBancoEm;
        TextView tvwCcEm;
        ImageView imgVerMas;
        CardView cvwContenidoPr;
        LinearLayout lnySecundario;

        public wvhPersonas(@NonNull View itemView) {
            super(itemView);
            tvwDiaReg = itemView.findViewById(R.id.tvwDiaReg);
            tvwMesReg = itemView.findViewById(R.id.tvwMesReg);
            tvwAnioReg = itemView.findViewById(R.id.tvwAnioReg);
            tvwNombreEm = itemView.findViewById(R.id.tvwNombreEm);
            tvwTpDocEm = itemView.findViewById(R.id.tvwTpDocEm);
            tvwNrDocEm = itemView.findViewById(R.id.tvwNrDocEm);
            tvwTipoTeEm = itemView.findViewById(R.id.tvwTipoTeEm);
            tvwNacionaldiadEm = itemView.findViewById(R.id.tvwNacionaldiadEm);
            tvwTipoOperaEm = itemView.findViewById(R.id.tvwTipoOperaEm);
            tvwBancoEm = itemView.findViewById(R.id.tvwBancoEm);
            tvwCcEm = itemView.findViewById(R.id.tvwCcEm);
            imgVerMas = itemView.findViewById(R.id.imgVerMas);
            cvwContenidoPr = itemView.findViewById(R.id.cvwContenidoPr);
            lnySecundario = itemView.findViewById(R.id.lnySecundario);
        }

        public void mtd_MostrarMas(boolean estado){
            // TAMAÑO
            if(estado){
                objL_HightList = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objL_MaxValor, objPV_Contexto.getResources().getDisplayMetrics());
                cvwContenidoPr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)objL_HightList));
                lnySecundario.setVisibility(View.VISIBLE);
            }else{
                objL_HightList = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, objL_MinValor, objPV_Contexto.getResources().getDisplayMetrics());
                cvwContenidoPr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)objL_HightList));
                lnySecundario.setVisibility(View.GONE);
            }
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
