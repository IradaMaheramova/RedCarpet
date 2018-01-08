package com.example.irada.redcarped;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Verification extends AppCompatActivity {
    EditText editText;
    SharedPreferences user_data;
    String SharedPreferencesname="user_data";
    String spnumber="number";
    String spname="name";
    String spid="id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        editText = (EditText) findViewById(R.id.editText3);
        user_data=getSharedPreferences(SharedPreferencesname,MODE_PRIVATE);
    }




    public void verify(View view)
    {
        String vernumber= editText.getText().toString();
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String verif = intent.getStringExtra("verif");
        String number = intent.getStringExtra("number");
        if(vernumber.equals(verif)) {
            SharedPreferences.Editor user_datasp = user_data.edit();
            user_datasp.putString(spname, name);
            user_datasp.putInt(spid, Integer.valueOf(id));
            user_datasp.putString(spnumber, number);
            if(!user_datasp.commit())
            {
                Toast.makeText(getApplicationContext(),"Something goes wrong : Please try again",Toast.LENGTH_SHORT).show();

            }
        }

        Intent go = new Intent(this,MainActivity.class);
        startActivity(go);
        // TODO: 1/8/2018  nael inchi chi qcum ps-i mej u qcel psi mej, hima inq@ uxarki texapoxum a main u prc
    }
}
