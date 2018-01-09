package com.example.irada.redcarped;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;

public class PartiesActivity extends AppCompatActivity {
    private ListView Parties;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parties);
        Parties = (ListView) findViewById(R.id.partylist);
        new PartiesActivity.GetAllParties().execute(new ApiConnector());
    }


    private class GetAllParties extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].Parties();
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
        this.Parties.setAdapter(new PartiesAdapter(jsonArray,this));

    }
}
