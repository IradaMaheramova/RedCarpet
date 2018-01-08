package com.example.irada.redcarped;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity {

    EditText usernumber;
    String number;
    int verif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        usernumber= (EditText) findViewById(R.id.editText);
        verif=(int) ((Math.random()*19900)+10000);
    }
    public void verification (View view)
    {
        number=usernumber.getText().toString();
        new LogInActivity.loginat().execute(new ApiConnector());
    }


    private class loginat extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].login(number);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            SetUserInformation(jsonArray);

        }
    }

    private void sendSMS(String phoneNumber, int message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getBaseContext(), "SMS RECEIVED",
                        Toast.LENGTH_SHORT).show();

                Bundle extras = intent.getExtras();
                if (extras == null)
                    return;

                Object[] pdus = (Object[]) extras.get("pdus");
                SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdus[0]);
                String origNumber = msg.getOriginatingAddress();
                String msgBody = msg.getMessageBody();
                // Now one can just match the msgBody with the expected
                // confirmation code for example.
            }
        }, intentFilter);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, String.valueOf(message), sentPI, deliveredPI);
    }
    public void SetUserInformation(JSONArray jsonArray)
    {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            Intent intent = new Intent(this,Verification.class);
            intent.putExtra("id",jsonObject.getString("id"));
            intent.putExtra("name",jsonObject.getString("name"));
            intent.putExtra("number",number);

            sendSMS(number,verif);
            intent.putExtra("verif",verif);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
