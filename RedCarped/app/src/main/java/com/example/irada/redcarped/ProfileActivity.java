package com.example.irada.redcarped;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

public class ProfileActivity extends AppCompatActivity {

    TextView NameT;
    TextView NumberT;

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
        setContentView(R.layout.profile);

        user_data = getSharedPreferences(SharedPreferencesname, MODE_PRIVATE);
        number = user_data.getString(spnumber, "");
        name = user_data.getString(spname, "");
        id = user_data.getInt(spid, 0);

        NameT=(TextView) findViewById(R.id.editText);
        NumberT=(TextView) findViewById(R.id.textView3);

        NameT.setText(name);
        NumberT.setText(number);

    }

}
