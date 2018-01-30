package com.example.irada.redcarped;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    TextView number;
    EditText nameEdit;
    String name;
    String Number;
    int id;
    SharedPreferences user_data;
    String SharedPreferencesname="user_data";
    String spnumber="number";
    String spname="name";
    String spid="id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        number =(TextView) findViewById(R.id.textView5);
        nameEdit= (EditText) findViewById(R.id.editText2);
        user_data=getSharedPreferences(SharedPreferencesname,MODE_PRIVATE);




            TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                return;
            }
            TelephonyManager tMgri = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgri.getLine1Number();
            Number = tMgri.getLine1Number();
            Log.e("NUmber","your number is "+Number);
            Log.e("NUmber","your number is "+getApplicationContext());
            number.setText(Number);
    }



    public void save(View v){
        name=nameEdit.getText().toString();
        Log.e("this","this is sparta");
        if(name.length()>0)
        {
            Log.e("this","this is sparta");
            new AddUser().execute(new ApiConnector());
        }

    }

    public boolean success(String Result)
    {
        if(Result.length()>4)
        {
            return false;
        }
        id=Integer.parseInt(Result);
        SharedPreferences.Editor user_dataed = user_data.edit();
        user_dataed.putString(spnumber,Number);
        user_dataed.putInt(spid,id);
        user_dataed.putString(spname,name);
        user_dataed.commit();
        return true;
    }

    public void gotohome()
    {
        Intent go = new Intent(this,MainActivity.class);
        startActivity(go);
    }
    private class AddUser extends AsyncTask<ApiConnector, Long, String>
    {
       String  name=nameEdit.getText().toString();

        @Override
        //protected JSONArray doInBackground(ApiConnector... params)
        protected String doInBackground(ApiConnector... params)
        {
            return params[0].AddUser(Number,name);
        }

        @Override
        //protected void onPostExecute(JSONArray jsonArray)
        protected void onPostExecute(String Result)
        {
            if(success(Result)) {
                    gotohome();
            }

        }



    }
}
