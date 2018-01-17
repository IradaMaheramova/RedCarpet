package com.example.irada.redcarped;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chat extends AppCompatActivity {

    private Button btn_send_msg;
    private EditText input_msg;
    private TextView chat_conversation;
    private DatabaseReference root ;
    private String temp_key;
    SharedPreferences user_data;
    String SharedPreferencesname="user_data";
    String number;
    String name;
    int id;
    String spnumber="number";
    String spname="name";
    String spid="id";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user_data = getSharedPreferences(SharedPreferencesname, MODE_PRIVATE);
        id = user_data.getInt(spid, 0);
        number = user_data.getString(spnumber, "");
        name = user_data.getString(spname, "");
        btn_send_msg = (Button) findViewById(R.id.button8);
        input_msg = (EditText) findViewById(R.id.editText7);
        chat_conversation = (TextView) findViewById(R.id.textView11);
        if ((id==0) || (number.length()==0) ||(name.length()==0))
        {
            gotoHome();
        }

        root = FirebaseDatabase.getInstance().getReference().child("admin");

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",name);
                map2.put("msg",input_msg.getText().toString());

                message_root.updateChildren(map2);
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void gotoHome() {
        Intent intent = new Intent(this,NewUserActivity.class);
        startActivity(intent);
    }

    private String chat_msg,chat_user_name;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            Log.e("chat_msg",chat_msg);

            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();
            Log.e("chat_user_name",chat_user_name);

            chat_conversation.append(chat_user_name +" : "+chat_msg +" \n");
        }


    }
}

