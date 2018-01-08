package com.example.irada.redcarped;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences user_data;
    String SharedPreferencesname="user_data";
    String number;
    String name;
    int id;
    String spnumber="number";
    String spname="name";
    String spid="id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user_data=getSharedPreferences(SharedPreferencesname,MODE_PRIVATE);
        number=user_data.getString(spname,"");
        name=user_data.getString(spname,"");
        id=user_data.getInt(spid,0);
        if (number.length()==0) {
            Intent party = new Intent(this,NewUserActivity.class);
            startActivity(party);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_profile) {
            Intent profile = new Intent(this,ProfileActivity.class);
            startActivity(profile);

            // Handle the camera action
        } else if (id == R.id.parties) {
            Intent party = new Intent(this,PartiesActivity.class);
            startActivity(party);

        } else if (id == R.id.contacts) {
            Intent contacts = new Intent(this,PartiesActivity.class);
            startActivity(contacts);

        } else if (id == R.id.log_out) {

            SharedPreferences.Editor user_datasp = user_data.edit();
            user_datasp.putString(spname,"");
            user_datasp.putInt(spid,0);
            user_datasp.putString(spnumber,"");
            user_datasp.commit();


        } else if (id == R.id.info) {
            Intent info = new Intent(this,PartiesActivity.class);
            startActivity(info);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
