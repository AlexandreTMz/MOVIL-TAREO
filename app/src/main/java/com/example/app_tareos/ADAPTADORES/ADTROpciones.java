package com.example.app_tareos.ADAPTADORES;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.app_tareos.MODEL.Opciones;
import com.example.app_tareos.R;

import java.util.List;

public class ADTROpciones extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Opciones> aPV_Opciones;
    private LayoutInflater objPV_Layout;
    private Context objPV_Contexto;
    private int intPV_TipoPlantilla;
    private OnItemClickListener mListener;

    public ADTROpciones(Context context, List<Opciones> data, int type) {
        this.objPV_Layout = LayoutInflater.from(context);
        this.aPV_Opciones = data;
        this.objPV_Contexto= context;
        this.intPV_TipoPlantilla = type;
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viwPlantillaHome;
        switch (this.intPV_TipoPlantilla) {
            case 1:
                viwPlantillaHome = LayoutInflater.from(this.objPV_Contexto).inflate(R.layout.adtr_opciones_list, parent, false);
                viwPlantillaHome.startAnimation(AnimationUtils.loadAnimation(this.objPV_Contexto, android.R.anim.slide_in_left));
                return new ListViewHolder(viwPlantillaHome);
            case 2:
                viwPlantillaHome = LayoutInflater.from(this.objPV_Contexto).inflate(R.layout.adtr_opciones_grid, parent, false);
                viwPlantillaHome.startAnimation(AnimationUtils.loadAnimation(this.objPV_Contexto, android.R.anim.slide_in_left));
                return new GridViewHolder(viwPlantillaHome);
        }

        viwPlantillaHome = LayoutInflater.from(this.objPV_Contexto).inflate(R.layout.adtr_opciones_grid, parent, false);
        return new GridViewHolder(viwPlantillaHome);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (this.intPV_TipoPlantilla == 1) {
            if (holder instanceof ListViewHolder) {
                ListViewHolder objL_listViewHolder = (ListViewHolder) holder;

                String LStr_titulo = aPV_Opciones.get(position).getStrtitulo();
                objL_listViewHolder.tvwTitulo.setText(LStr_titulo);

                String LStr_descripcion = aPV_Opciones.get(position).getStrdescripcion();
                objL_listViewHolder.tvwDescripcion.setText(LStr_descripcion);

                objL_listViewHolder.viwLinea.setBackgroundColor(Color.parseColor(aPV_Opciones.get(position).getStrcolor()));

                int id = objPV_Contexto.getResources().getIdentifier("com.example.app_tareos:drawable/" + aPV_Opciones.get(position).getStricono(), null, null);

                objL_listViewHolder.ivwIcono.setImageResource(id);

                objL_listViewHolder.ivwIcono.setColorFilter(Color.parseColor( aPV_Opciones.get(position).getStrcolor()),   PorterDuff.Mode.SRC_ATOP);
            }
        } else if (this.intPV_TipoPlantilla == 2) {
            if (holder instanceof GridViewHolder) {
                GridViewHolder objL_gridViewHolder = (GridViewHolder) holder;
                String LStr_titulo = aPV_Opciones.get(position).getStrtitulo();
                String LStr_color = aPV_Opciones.get(position).getStrcolor();
                objL_gridViewHolder.tvwTitulo.setText(LStr_titulo);
                objL_gridViewHolder.setColor(LStr_color);

                int id = objPV_Contexto.getResources().getIdentifier("com.example.app_tareos:drawable/" + aPV_Opciones.get(position).getStricono(), null, null);

                objL_gridViewHolder.ivwIcono.setImageResource(id);

                objL_gridViewHolder.ivwIcono.setColorFilter(Color.parseColor( aPV_Opciones.get(position).getStrcolor()),   PorterDuff.Mode.SRC_ATOP);

            }
        }
    }

    @Override
    public int getItemCount() {
        return aPV_Opciones.size();
    }

    public void setVIEW_TYPE(int viewType) {
        this.intPV_TipoPlantilla = viewType;
        notifyDataSetChanged();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvwTitulo;
        LinearLayout lltFondoOpcion;
        LayerDrawable objLayerDrawable;
        GradientDrawable objGradientDrawable;
        ImageView ivwIcono;
        CardView cvwMain;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvwTitulo = itemView.findViewById(R.id.tvwTitulo);
            lltFondoOpcion= itemView.findViewById(R.id.lltFondoOpcion);
            objLayerDrawable = (LayerDrawable)  ResourcesCompat.getDrawable(
                    lltFondoOpcion.getResources(),
                    R.drawable.shp_fondo_opcion,
                    null
            ).mutate();
            objGradientDrawable = (GradientDrawable) objLayerDrawable.findDrawableByLayerId(R.id.myshape);
            lltFondoOpcion.setBackground(objLayerDrawable);
            ivwIcono = itemView.findViewById(R.id.ivwIcono);
            cvwMain = itemView.findViewById(R.id.cvwMain);

            cvwMain.setOnClickListener(this::onClick);
        }

        public void setColor(String color) {
            objGradientDrawable.setColor(Color.parseColor(color));
            //lyOpsps.setBackgroundDrawable(gradientDrawable);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(aPV_Opciones.get(position));
                }
            }
        }
    }

    public class ListViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        TextView tvwTitulo,tvwDescripcion;
        View viwLinea;
        ImageView ivwIcono;
        CardView cvwMain;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvwTitulo = itemView.findViewById(R.id.tvwTitulo);
            tvwDescripcion = itemView.findViewById(R.id.tvwDescripcion);
            viwLinea = itemView.findViewById(R.id.viwLinea);
            ivwIcono = itemView.findViewById(R.id.ivwIcono);
            cvwMain = itemView.findViewById(R.id.cvwMain);

            cvwMain.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(aPV_Opciones.get(position));
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Opciones opciones);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}