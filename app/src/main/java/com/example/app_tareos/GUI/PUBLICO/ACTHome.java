package com.example.app_tareos.GUI.PUBLICO;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.app_tareos.R;
import com.example.app_tareos.ROOMS.Preference_PerfilUsuario;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_tareos.databinding.*;

import es.dmoral.toasty.Toasty;


public class ACTHome extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActHomeBinding binding;
    //private ActivityActHomeBinding binding;

    public TextView tvwPb_usuario_nombre,tvwPb_usuario_cargo;

    public Preference_PerfilUsuario userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // instanciado el room
        userProfile = Preference_PerfilUsuario.getInstance(this);

        binding = ActHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarActHome.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_personas
        ).setDrawerLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_act_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        getSupportActionBar().setTitle("Bienvenido");

        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.nav_home:
                         setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        break;
                    case R.id.nav_personas:
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        break;
                }
                return true;
            }
        });*/

        //Quitando el filtro de color al menu
        navigationView.setItemIconTintList(null);

        tvwPb_usuario_nombre = navigationView.getHeaderView(0).findViewById(R.id.tvw_usuario_nombre);
        tvwPb_usuario_cargo = navigationView.getHeaderView(0).findViewById(R.id.tvw_usuario_cargo);

        tvwPb_usuario_nombre.setText(userProfile.getUsuarioInfo().getDatos());
        tvwPb_usuario_cargo.setText(userProfile.getUsuarioInfo().getTpu_descripcion());

        int intL_TipoUsuario = userProfile.getUsuarioInfo().getId_tpusuario();
        if(intL_TipoUsuario == 3){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_personas).setVisible(false);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.acthome, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_act_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

}