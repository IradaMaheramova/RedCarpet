package com.example.irada.redcarped;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FriendAdapter extends BaseAdapter {
    private static final String  startImageUrl="https://magaramovai.000webhostapp.com/images/";
    private JSONArray dataArray;
    private Activity activity;
    public static LayoutInflater inflater = null;
    public FriendAdapter(JSONArray jsonArray, Activity a)
    {
        this.dataArray=jsonArray;
        this.activity=a;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        try{
            return this.dataArray.length();
        }
        catch(Exception e){

            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListFriend party;

        if(convertView == null)
        {
            convertView=inflater.inflate(R.layout.friendadapter,null);
            party = new ListFriend();
            party.name=(TextView) convertView.findViewById(R.id.name);
            party.number=(TextView) convertView.findViewById(R.id.number);

            convertView.setTag(party);

        }
        else
        {
            party= (ListFriend) convertView.getTag();

        }
        try {
            JSONObject jsonObject = this.dataArray.getJSONObject(position);
            Log.e("jsonObject Adapter",jsonObject.toString());
            party.name.setText(jsonObject.getString("name"));
            party.number.setText(jsonObject.getString("number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ListFriend
    {

        private TextView name;
        private TextView number;

    }















}