package com.example.irada.redcarped;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.Manifest;

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


    TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        checkPermissions();

        user_data=getSharedPreferences(SharedPreferencesname,MODE_PRIVATE);
        number=user_data.getString(spnumber,"");
        name=user_data.getString(spname,"");
        id=user_data.getInt(spid,0);
        if (number.length()==0) {
            Intent party = new Intent(this,NewUserActivity.class);
            startActivity(party);
        }

        hello = (TextView)  findViewById(R.id.textView10) ;
        hello.setText("Hello, "+name+"!");
    }

    public boolean checkPermissions(){

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission"," SEND_SMS Permission is granted");
            } else {
                Log.v("Permission","SEND_SMS Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
                return false;
            }
            if (checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission"," RECEIVE_SMS Permission is granted");
            } else {
                Log.v("Permission","RECEIVE_SMS Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
                return false;
            }
            if (checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission"," READ_PHONE_STATE Permission is granted");
            } else {
                Log.v("Permission","READ_PHONE_STATE Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                return false;
            }
            if (checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission"," INTERNET Permission is granted");
            } else {
                Log.v("Permission","INTERNET Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
                return false;
            }
            if (checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission"," READ_CONTACTS Permission is granted");
            } else {
                Log.v("Permission","READ_CONTACTS Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
                return false;
            }
            if (checkSelfPermission(android.Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission"," WAKE_LOCK Permission is granted");
            } else {
                Log.v("Permission","WAKE_LOCK Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WAKE_LOCK}, 1);
                return false;
            }

        }
        else {
            Log.v("Permission","Permissions are granted");
        }


        return true;
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

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
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);


        } else if (id == R.id.chat) {
            Intent info = new Intent(this,Chat.class);
            startActivity(info);

        }   else if (id == R.id.newparty) {
            Intent info = new Intent(this,NewParty.class);
            startActivity(info);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
