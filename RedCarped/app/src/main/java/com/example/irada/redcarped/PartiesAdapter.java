package com.example.irada.redcarped;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class PartiesAdapter extends BaseAdapter {
    private static final String  startImageUrl="https://magaramovai.000webhostapp.com/images/";
    private JSONArray dataArray;
    private Activity activity;
    public static LayoutInflater inflater = null;
    public PartiesAdapter(JSONArray jsonArray, Activity a)
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
        final ListParty party;

        if(convertView == null)
        {
            convertView=inflater.inflate(R.layout.party,null);
            party = new ListParty();
            party.name=(TextView) convertView.findViewById(R.id.textView7);
            party.adress=(TextView) convertView.findViewById(R.id.textView8);
            party.type=(TextView) convertView.findViewById(R.id.textView9);

            party.party_img=(ImageView) convertView.findViewById(R.id.imageView5);
            convertView.setTag(party);

        }
        else
        {
            party= (ListParty) convertView.getTag();

        }
        try {
            JSONObject jsonObject = this.dataArray.getJSONObject(position);
            party.name.setText(jsonObject.getString("Name"));
            party.adress.setText(jsonObject.getString("Address"));
            party.type.setText(jsonObject.getString("Type"));
            String imageName=jsonObject.getString("Name")+"-"+jsonObject.getString("userID");
            String fullUrlForimg=startImageUrl+imageName+".jpg";
            Log.e("image url",fullUrlForimg);
            Picasso.with(activity).load(fullUrlForimg)
                    .placeholder(R.drawable.icon)
                    .error(R.drawable.icon)
                    .into(party.party_img);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ListParty
    {

        private TextView name;
        private TextView adress;
        private TextView type;
        private ImageView party_img;
    }















}