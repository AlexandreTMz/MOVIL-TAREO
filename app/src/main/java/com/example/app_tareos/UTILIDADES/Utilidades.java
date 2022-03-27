package com.example.app_tareos.UTILIDADES;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.app_tareos.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utilidades {

    public static void mdt_ChangeFragment(Activity activity, Fragment fragment){
        // Create new fragment and transaction
        FragmentActivity myContext =(FragmentActivity) activity;
        FragmentManager fragmentManager = myContext.getSupportFragmentManager();
        FragmentTransaction fragTrans = fragmentManager.beginTransaction();
        fragTrans.replace(R.id.nav_host_fragment_content_act_home, fragment);
        fragTrans.addToBackStack(null);
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTrans.commit();

    }

    public static <T> void clearListObject(List<T> lista){
        if (lista.size()>0){
            lista.clear();
        }
    }

    public static String fn_NombreDia(String fecha) throws Exception{
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        // Then get the day of week from the Date based on specific locale.
        return new SimpleDateFormat("EEEE", new Locale("es", "ES")).format(date);
    }


    public  static  String fn_GetDate(){
        String pattern = "yyyy-MM-dd";
        String dateInString =new SimpleDateFormat(pattern).format(new Date());
        return dateInString;
    }

    public static boolean fn_ValidarFechaIngreso(String fchRegistro) throws ParseException {
        /*
        * Generar la fecha ultimo
        * */
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        cal.add(Calendar.MONTH, 1);
        Date lastDayOfMonth = cal.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date myDateRegister = format.parse(fchRegistro);

        if(myDateRegister.after(lastDayOfMonth)){
            return false;
        }
        return true;
    }

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }


}
