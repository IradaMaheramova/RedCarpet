package com.example.irada.redcarped;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
    }



    public void LogIn(View view)
    {
        Intent login = new Intent(this,LogInActivity.class);
        startActivity(login);

    }
    public void SignIn(View view)
    {
        Intent signin = new Intent(this,SignInActivity.class);
        startActivity(signin);

    }

}
