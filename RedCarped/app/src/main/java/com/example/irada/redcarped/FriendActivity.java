package com.example.irada.redcarped;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;

public class FriendActivity extends AppCompatActivity {
    String Fiends="";
    SharedPreferences user_data;
    String SharedPreferencesname="user_data";
    String number;
    String name;
    int id;
    String spnumber="number";
    String spname="name";
    String spid="id";

    private ListView Parties;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);
        user_data = getSharedPreferences(SharedPreferencesname, MODE_PRIVATE);
        id = user_data.getInt(spid, 0);
        number = user_data.getString(spnumber, "");
        name = user_data.getString(spname, "");
        if ((id==0) || (number.length()==0) ||(name.length()==0))
        {
            gotoHome();
        }
        getContactList();



        Parties = (ListView) findViewById(R.id.friendList);
        new FriendActivity.SetFriend().execute(new ApiConnector());
    }


    private class SetFriend extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].Froends(Fiends);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setListAdapter(jsonArray);


        }
    }


    public void setListAdapter(JSONArray jsonArray)
    {
        Log.d("JSON_object","jsonArray_setListAdapter= "+jsonArray);
        this.jsonArray=jsonArray;
        this.Parties.setAdapter(new FriendAdapter(jsonArray,this));

    }


    private void gotoHome() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Fiends+="'"+phoneNo.toString().replaceAll(" ","")+"',";
                        Log.i("tag", "Name: " + name);
                        Log.i("tag", "Phone Number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        Log.i("end", Fiends);
        Fiends=Fiends.substring(0,Fiends.length()-1);
        Fiends="("+Fiends+")";
        Log.i("end", Fiends);

    }
}
