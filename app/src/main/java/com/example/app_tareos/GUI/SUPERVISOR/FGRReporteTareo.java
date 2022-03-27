package com.example.app_tareos.GUI.SUPERVISOR;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.app_tareos.LIBS.TablaDinamica;
import com.example.app_tareos.R;

import java.util.List;

public class FGRReporteTareo extends Fragment {

    private TablaDinamica objP_TablaDinamica;
    private TableLayout tableLayout;
    List<String[]> ObjP_lsResultado;

    public FGRReporteTareo() {
        // Required empty public constructor
    }

    public FGRReporteTareo(List<String[]> lsResultado){
        this.ObjP_lsResultado = lsResultado;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fgt_reporte_tareo, container, false);

        // Cambio de titulo
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Reporte tareo");

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        String [] header = {
                "Cod Tareo",
                "Nombres",
                "Sede",
                "Estado",
                "Etapa",
                "Fech. Ingreso",
                "Hor. Ingreso",
                "Fech. Cierre",
                "Hor. Cierre",
                "Marcador"
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}