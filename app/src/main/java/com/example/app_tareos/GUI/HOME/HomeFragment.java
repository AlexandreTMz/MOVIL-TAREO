package com.example.app_tareos.GUI.HOME;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.app_tareos.ADAPTADORES.ADTROpciones;
import com.example.app_tareos.CONFIG.UrlServer;
import com.example.app_tareos.GUI.OPERARIO.FGRSueldos;
import com.example.app_tareos.GUI.PUBLICO.ACTLogin;
import com.example.app_tareos.GUI.SUPERVISOR.FGRRegistrarFaltas;
import com.example.app_tareos.GUI.SUPERVISOR.FRGRegistrarDescanso;
import com.example.app_tareos.INTERFACE.IResultVolley;
import com.example.app_tareos.LIBS.ErrorToastyServer;
import com.example.app_tareos.LIBS.VolleyService;
import com.example.app_tareos.MODEL.Opciones;
import com.example.app_tareos.MODEL.Sede;
import com.example.app_tareos.MODEL.Usuario;
import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;
import com.example.app_tareos.UTILIDADES.Utilidades;
import com.example.app_tareos.databinding.FgtHomeBinding;
import com.example.app_tareos.GUI.SUPERVISOR.FGRAgregarPersona;
import com.example.app_tareos.GUI.SUPERVISOR.FGRCerrarTareo;
import com.example.app_tareos.GUI.SUPERVISOR.FGRFiltroReporteTareoSU;
import com.example.app_tareos.GUI.SUPERVISOR.FGRGenerarPermiso;
import com.example.app_tareos.GUI.SUPERVISOR.FGRRegistrarTareo;
import com.example.app_tareos.GUI.OPERARIO.FGRFiltroReporteTareoOP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class HomeFragment extends Fragment implements  ADTROpciones.OnItemClickListener , IResultVolley {

    private HomeViewModel homeViewModel;
    private FgtHomeBinding binding;

    private RecyclerView rvwPv_Opciones;
    private LinearLayoutManager objPv_vista;
    private ADTROpciones ADTRPv_Opciones;

    public ArrayList<Opciones> aPBobjPb_opciones;

    private int intPv_tema=1;
    /** 1 = lista
     *  2 = gridd
     */

    private Menu objPv_menuDerececha;

    public Preference_PerfilUsuario userProfile;

    // progress
    ProgressDialog pgdCambioContrasenia;

    AppCompatActivity objL_appCompat;
    // voley
    VolleyService mVolleyService;
    ErrorToastyServer errorToastyServer;
    private String TAG = "MainActivity";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FgtHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        objL_appCompat = ((AppCompatActivity) getActivity());

        // Cambio de titulo
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bienvenido");

        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bienvenido");
        }


        /// ERROR SERVER
        errorToastyServer = new ErrorToastyServer(getActivity());

        // INTANCE SERVICE
        mVolleyService = new VolleyService(this, getActivity());

        pgdCambioContrasenia = new ProgressDialog(getContext());

        pgdCambioContrasenia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pgdCambioContrasenia.setTitle("Cambiando...");

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                rvwPv_Opciones = root.findViewById(R.id.rvwOpciones);

                userProfile = Preference_PerfilUsuario.getInstance(getContext());

                aPBobjPb_opciones = new ArrayList<>();

                Opciones objL_ListOpciones = null;

                int intL_TipoUsuario = userProfile.getUsuarioInfo().getId_tpusuario();

                if(intL_TipoUsuario == 3){

                    objL_ListOpciones = new Opciones();
                    objL_ListOpciones.setId_Opcion(0);
                    objL_ListOpciones.setStrtitulo("Tareos");
                    objL_ListOpciones.setStrdescripcion("Modulo para visualizar los tareos");
                    objL_ListOpciones.setStrcolor("#0E99DA");
                    objL_ListOpciones.setStricono("ico_calendario");
                    aPBobjPb_opciones.add(objL_ListOpciones);

                    objL_ListOpciones = new Opciones();
                    objL_ListOpciones.setId_Opcion(10);
                    objL_ListOpciones.setStrtitulo("Sueldos");
                    objL_ListOpciones.setStrdescripcion("Modulo para visualizar los sueldos");
                    objL_ListOpciones.setStrcolor("#5B61D9");
                    objL_ListOpciones.setStricono("ico_money");
                    aPBobjPb_opciones.add(objL_ListOpciones);

                }else{

                    /*objL_ListOpciones = new Opciones();
                    objL_ListOpciones.setId_Opcion(3);
                    objL_ListOpciones.setStrtitulo("Cerrar Tareo");
                    objL_ListOpciones.setStrdescripcion("Ventana para cerrar tareo");
                    objL_ListOpciones.setStrcolor("#3EB595");
                    objL_ListOpciones.setStricono("ico_setting");
                    aPBobjPb_opciones.add(objL_ListOpciones);*/

                    /*objL_ListOpciones = new Opciones();
                    objL_ListOpciones.setId_Opcion(6);
                    objL_ListOpciones.setStrtitulo("Programar descanso");
                    objL_ListOpciones.setStrdescripcion("Modulo para programar los descansos de los empleados");
                    objL_ListOpciones.setStrcolor("#c56cf0");
                    objL_ListOpciones.setStricono("ic_descansos");
                    aPBobjPb_opciones.add(objL_ListOpciones);*/

                    /*
                    * DES
                    * */

                    objL_ListOpciones = new Opciones();
                    objL_ListOpciones.setId_Opcion(1);
                    objL_ListOpciones.setStrtitulo("Empleados");
                    objL_ListOpciones.setStrdescripcion("Modulo para registrar a los empleados");
                    objL_ListOpciones.setStrcolor("#0E99DA");
                    objL_ListOpciones.setStricono("ico_add");
                    aPBobjPb_opciones.add(objL_ListOpciones);


                    objL_ListOpciones = new Opciones();
                    objL_ListOpciones.setId_Opcion(2);
                    objL_ListOpciones.setStrtitulo("Tareo");
                    objL_ListOpciones.setStrdescripcion("Modulo para registrar y cerrar los tareo");
                    objL_ListOpciones.setStrcolor("#93C700");
                    objL_ListOpciones.setStricono("ico_barcode");
                    aPBobjPb_opciones.add(objL_ListOpciones);

                    objL_ListOpciones = new Opciones();
                    objL_ListOpciones.setId_Opcion(4);
                    objL_ListOpciones.setStrtitulo("Registrar permisos");
                    objL_ListOpciones.setStrdescripcion("Modulo para registrar los permisos");
                    objL_ListOpciones.setStrcolor("#0d63ff");
                    objL_ListOpciones.setStricono("ico_permitido");
                    aPBobjPb_opciones.add(objL_ListOpciones);

                    objL_ListOpciones = new Opciones();
                    objL_ListOpciones.setId_Opcion(7);
                    objL_ListOpciones.setStrtitulo("Faltas/Descanso");
                    objL_ListOpciones.setStrdescripcion("Modulo para tarear faltas y descansos");
                    objL_ListOpciones.setStrcolor("#535c68");
                    objL_ListOpciones.setStricono("ic_faltas");
                    aPBobjPb_opciones.add(objL_ListOpciones);

                    objL_ListOpciones = new Opciones();
                    objL_ListOpciones.setId_Opcion(5);
                    objL_ListOpciones.setStrtitulo("Reportes");
                    objL_ListOpciones.setStrdescripcion("Modulo de reportes de la aplicación");
                    objL_ListOpciones.setStrcolor("#FF530D");
                    objL_ListOpciones.setStricono("ico_reports");
                    aPBobjPb_opciones.add(objL_ListOpciones);

                }

                ADTRPv_Opciones = new ADTROpciones(getContext(), aPBobjPb_opciones, intPv_tema);

                objPv_vista = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvwPv_Opciones.setLayoutManager(objPv_vista);
                ADTRPv_Opciones.setOnItemClickListener(HomeFragment.this::onItemClick);

                //recyclerView.setHasFixedSize(true);
                rvwPv_Opciones.setAdapter(ADTRPv_Opciones);

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        System.out.println("menuuuuuuuuuuuuuu");
        super.onPrepareOptionsMenu(menu);

        this.objPv_menuDerececha = menu;
        if(userProfile.getVista() == 1){
            vista_lista();
        }else{
            vista_gridd();
        }
        return;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:
                vista_lista();
                // do something
                return true;
            case R.id.menu_gridd:
                // do something
                vista_gridd();
                return true;
            case R.id.menu_chg_sede:
                    mtd_dialgoSede();
                return true;
            case R.id.menu_chg_contrasenia:
                mtd_dialgoCambioContrasenia();
                return true;
            case R.id.menu_salir:
                userProfile.putSession(false);
                SweetAlertDialog alertDialog = new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE);
                alertDialog.setTitleText("Salir de la aplicación")
                        .setContentText("¿Está seguro que desea salir de la aplicación?")
                        .setConfirmText("Salir!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent login = new Intent(getActivity(), ACTLogin.class);
                                getActivity().startActivity(login);
                                getActivity().finish();
                            }
                        })
                        .setCancelButton("Cancelar", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                Button btn =  alertDialog.findViewById(R.id.confirm_button);
                //btn.setBackgroundColor(Color.GREEN);

                return true;
            default:
                return(super.onOptionsItemSelected(item));
        }
    }

    public void mtd_dialgoSede(){
        Dialog dlgCambioSede = new Dialog(getActivity());
        dlgCambioSede.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlgCambioSede.setCancelable(false);
        dlgCambioSede.setContentView(R.layout.dg_cambiar_sede);

        /*TAMAÑO*/
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dlgCambioSede.getWindow().setLayout(width, (3 * height)/5);
        /*END*/

        // STAR
        List<Sede> lsSede = new ArrayList<>();
        lsSede.addAll(fn_GenerarSedesPermitidos());
        ArrayAdapter<CharSequence> adaptadorSede;
        Spinner spSedesUser = dlgCambioSede.findViewById(R.id.spSedesPermitidos);
        adaptadorSede = new ArrayAdapter(getActivity(), R.layout.spinner_item_opt, lsSede);
        spSedesUser.setAdapter(adaptadorSede);
        adaptadorSede.notifyDataSetChanged();

        // SEDE ACTUAL
        EditText edSedeActual = dlgCambioSede.findViewById(R.id.edSedeActual);
        edSedeActual.setText(userProfile.getSede());

        // BUTTON
        Button btnCambioSede = dlgCambioSede.findViewById(R.id.btnCambioSede);
        btnCambioSede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sede sede = (Sede) spSedesUser.getSelectedItem();
                edSedeActual.setText(sede.getDatos());
                userProfile.putSede(edSedeActual.getText().toString());
                userProfile.putId_sede(sede.getId_sede());
                Toasty.success(getContext(),"Sede cambiada satisfactoriamente!",Toasty.LENGTH_SHORT).show();
            }
        });

        ImageView dialogButton = dlgCambioSede.findViewById(R.id.imgCloseDi);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgCambioSede.dismiss();
            }
        });
        dlgCambioSede.show();
    }

    String strP_Contraseña;
    public void mtd_dialgoCambioContrasenia(){
        Dialog dlgCambioContrasenia = new Dialog(getActivity());
        dlgCambioContrasenia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlgCambioContrasenia.setCancelable(false);
        dlgCambioContrasenia.setContentView(R.layout.dg_cambiar_contrasenia);

        /*TAMAÑO*/
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dlgCambioContrasenia.getWindow().setLayout(width, (3 * height)/5);
        /*END*/

        // SEDE ACTUAL
        EditText edContraseniaActual = dlgCambioContrasenia.findViewById(R.id.edContraseniaActual);
        EditText edNuevaContrasenia = dlgCambioContrasenia.findViewById(R.id.edNuevaContrasenia);

        // BUTTON
        Button btnCambioContrasenia = dlgCambioContrasenia.findViewById(R.id.btnCambioContrasenia);
        btnCambioContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userProfile.getUsuarioInfo().getUs_contrasenia().isEmpty() || userProfile.getUsuarioInfo().getUs_contrasenia() == null){
                    Toasty.error(getContext(),"¡Error desconocido!",Toasty.LENGTH_LONG).show();
                    return;
                }

                if(!edContraseniaActual.getText().toString().equals(userProfile.getUsuarioInfo().getUs_contrasenia())){
                    Toasty.error(getContext(),"¡La contrasela actual no es correcta!",Toasty.LENGTH_LONG).show();
                    return;
                }

                if(edContraseniaActual.getText().toString().isEmpty()){
                    Toasty.error(getContext(),"Ingrese una contraseña!",Toasty.LENGTH_LONG).show();
                    return;
                }

                if(edNuevaContrasenia.getText().toString().isEmpty()){
                    Toasty.error(getContext(),"Ingrese su nueva contraseña!!",Toasty.LENGTH_LONG).show();
                    return;
                }

                if(edContraseniaActual.getText().toString().equalsIgnoreCase(edNuevaContrasenia.getText().toString())){
                    Toasty.error(getContext(),"La contraseña no puede ser igual a la actual!",Toasty.LENGTH_LONG).show();
                    return;
                }

                String strL_ContraseniaNeuva = edNuevaContrasenia.getText().toString();
                int intL_IdPersona = userProfile.getUsuarioInfo().getId_usuario();

                Map<String, Object> data = new HashMap<String, Object>();
                data.put("contrasenia", strL_ContraseniaNeuva);
                data.put("id_persona", intL_IdPersona);
                strP_Contraseña = strL_ContraseniaNeuva;

                JSONObject json =  new JSONObject(data);
                System.out.println(json);
                mVolleyService.mtd_PostObjectVolley("ACTUALIZAR_CONTRASENIA", String.format(UrlServer.URL_SERVER + "usuario/cambio/contrasenia"),json);
                pgdCambioContrasenia.setMessage("Porfavor espere...");
                pgdCambioContrasenia.setIndeterminate(true);
                pgdCambioContrasenia.setCanceledOnTouchOutside(false);
            }
        });

        ImageView dialogButton = dlgCambioContrasenia.findViewById(R.id.imgCloseDi);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgCambioContrasenia.dismiss();
            }
        });
        dlgCambioContrasenia.show();
    }


    public List<Sede> fn_GenerarSedesPermitidos(){
        List<Sede> tempSede = new ArrayList<>();
        String sedes = userProfile.getSedePermitidas();
        String[] sedesSeparadas = sedes.split("\\[\\@\\]");
        for (String sedes1 : sedesSeparadas) {
            String[] objSede  = sedes1.split("\\(\\@\\*\\)");
            Sede sede = new Sede();
            sede.setId_sede(Integer.parseInt(objSede[0].trim()));
            sede.setDatos(objSede[1].trim());
            tempSede.add(sede);
        }
        return tempSede;
    }

    public void vista_lista(){

        ADTRPv_Opciones.setVIEW_TYPE(1);
        rvwPv_Opciones.setLayoutManager(objPv_vista);
        rvwPv_Opciones.setAdapter(ADTRPv_Opciones);

        MenuItem mnuTextGroup = objPv_menuDerececha.findItem(R.id.menu_gridd);
        mnuTextGroup.setVisible(true);

        MenuItem menuList1 = objPv_menuDerececha.findItem(R.id.menu_list);
        menuList1.setVisible(false);

        userProfile.putVista(1);
    }

    public void vista_gridd(){
        ADTRPv_Opciones.setVIEW_TYPE(2);
        rvwPv_Opciones.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvwPv_Opciones.setAdapter(ADTRPv_Opciones);

        MenuItem menuList = objPv_menuDerececha.findItem(R.id.menu_list);
        menuList.setVisible(true);

        MenuItem menuGridd = objPv_menuDerececha.findItem(R.id.menu_gridd);
        menuGridd.setVisible(false);

        userProfile.putVista(2);
    }

    @Override
    public void onItemClick(Opciones opciones) {
        switch (opciones.getId_Opcion()){
            case 0:
                Utilidades.mdt_ChangeFragment(getActivity(), new FGRFiltroReporteTareoOP());
                break;
            case 1:
                Utilidades.mdt_ChangeFragment(getActivity(), new FGRAgregarPersona());
                break;
            case 2:
                Utilidades.mdt_ChangeFragment(getActivity(), new FGRRegistrarTareo());
                break;
            case 3:
                Utilidades.mdt_ChangeFragment(getActivity(), new FGRCerrarTareo());
                break;
            case 4:
                Utilidades.mdt_ChangeFragment(getActivity(), new FGRGenerarPermiso());
                break;
            case 5:
                Utilidades.mdt_ChangeFragment(getActivity(), new FGRFiltroReporteTareoSU());
                break;
            case 6:
                Utilidades.mdt_ChangeFragment(getActivity(), new FRGRegistrarDescanso());
                break;
            case 7:
                Utilidades.mdt_ChangeFragment(getActivity(), new FGRRegistrarFaltas());
                break;
            case 10:
                Utilidades.mdt_ChangeFragment(getActivity(), new FGRSueldos());
                break;
        }
    }

    @Override
    public void notifySuccessOject(String requestType, JSONObject response) {
        switch (requestType){
            case "ACTUALIZAR_CONTRASENIA":
                pgdCambioContrasenia.dismiss();
                Toasty.success(getActivity(),"Contraseña actualizada!",Toasty.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void notifySuccessArray(String requestType, JSONArray response) {

    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        switch (requestType){
            case "ACTUALIZAR_CONTRASENIA":
                Usuario usuario = userProfile.getUsuarioInfo();
                usuario.setUs_contrasenia(strP_Contraseña);
                userProfile.putUsuarioInfo(usuario);
                pgdCambioContrasenia.dismiss();
                errorToastyServer.showErrorServer(error);
                break;
        }
    }
}