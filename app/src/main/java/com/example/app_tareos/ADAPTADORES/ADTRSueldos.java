package com.example.app_tareos.ADAPTADORES;

import android.content.Context;
import android.graphics.Color;
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

import com.airbnb.lottie.LottieAnimationView;
import com.example.app_tareos.MODEL.Empleado;
import com.example.app_tareos.MODEL.Sueldo;
import com.example.app_tareos.R;

import java.util.List;

public class ADTRSueldos extends RecyclerView.Adapter<ADTRSueldos.wvhPersonas>{

    List<Sueldo> aPV_alertas;
    private Context objPV_Contexto;

    public ADTRSueldos(Context mContext, List<Sueldo> listaAlertas) {
        System.out.println("SIZEE"+listaAlertas.size());
        this.objPV_Contexto = mContext;
        this.aPV_alertas = listaAlertas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ADTRSueldos.wvhPersonas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_sueldos,parent,false);
        ADTRSueldos.wvhPersonas holder = new ADTRSueldos.wvhPersonas(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ADTRSueldos.wvhPersonas holder, int position) {
        int objL_Posicion = position;
        Sueldo objL_Empleados = aPV_alertas.get(position);
        holder.tvwSueldo.setText(objL_Empleados.getDblP_sueldo()+"");
        holder.tvwInicioSueldo.setText(objL_Empleados.getStrP_FechaInicio()+"");
        holder.tvwFinSueldo.setText(objL_Empleados.getStrP_FechaFin()+"");
        holder.mtd_CambiarEstado(objL_Empleados.isInactive());
    }

    @Override
    public int getItemCount() {
        return aPV_alertas.size();
    }

    float objL_HightList;
    float objL_MinValor = 106;
    float objL_MaxValor = 146;

    public class wvhPersonas extends RecyclerView.ViewHolder{
        TextView tvwSueldo;
        TextView tvwInicioSueldo;
        TextView tvwFinSueldo;
        LinearLayout lyEstadoS;
        ImageView ivwEstado;

        public wvhPersonas(@NonNull View itemView) {
            super(itemView);
            tvwSueldo = itemView.findViewById(R.id.tvwSueldo);
            tvwInicioSueldo = itemView.findViewById(R.id.tvwInicioSueldo);
            tvwFinSueldo = itemView.findViewById(R.id.tvwFinSueldo);
            lyEstadoS = itemView.findViewById(R.id.lyEstadoS);
            ivwEstado = itemView.findViewById(R.id.ivwEstado);
        }

        public void mtd_CambiarEstado(boolean estado){
            if(estado){
                lyEstadoS.setBackgroundColor(Color.parseColor("#30BFB1"));
                ivwEstado.setBackground(objPV_Contexto.getResources().getDrawable(R.drawable.ico_accept));
            }else{
                lyEstadoS.setBackgroundColor(Color.parseColor("#BF3030"));
                ivwEstado.setBackground(objPV_Contexto.getResources().getDrawable(R.drawable.ico_inactive));
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
