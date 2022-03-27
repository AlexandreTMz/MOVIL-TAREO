package com.example.app_tareos.GUI.OPERARIO;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.app_tareos.LIBS.TablaDinamica;
import com.example.app_tareos.R;

import java.util.List;

public class FGRReporteTareoOperario extends Fragment {

    private TablaDinamica objP_TablaDinamica;
    private TableLayout tableLayout;
    List<String[]> ObjP_lsResultado;

    public FGRReporteTareoOperario() {
        // Required empty public constructor
    }

    public FGRReporteTareoOperario(List<String[]> lsResultado){
        this.ObjP_lsResultado = lsResultado;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fgt_reporte_tareo_operario, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        String [] header = {
                "Cod Tareo",
                "Documento",
                "Datos",
                "Cargo",
                "Turno",
                "F Registro",
                "H Registro",
                "F Cierre",
                "H Cierre",
                "Estado",
                "Pago"
        };

        tableLayout=(TableLayout) view.findViewById(R.id.table);
        objP_TablaDinamica = new TablaDinamica(tableLayout,getActivity());
        objP_TablaDinamica.addHeader(header);
        objP_TablaDinamica.addData(this.ObjP_lsResultado);
        objP_TablaDinamica.backgroundHeader(Color.parseColor("#273c75"));
        objP_TablaDinamica.backgroundData(Color.parseColor("#f5f6fa"),Color.LTGRAY);
        objP_TablaDinamica.lineColor(Color.parseColor("#192a56"));
        objP_TablaDinamica.textColorData(Color.BLACK);
        objP_TablaDinamica.textColorHeader(Color.WHITE);

        return view;
    }
}