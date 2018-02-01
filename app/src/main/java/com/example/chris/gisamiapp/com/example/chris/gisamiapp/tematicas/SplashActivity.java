package com.example.chris.gisamiapp.com.example.chris.gisamiapp.tematicas;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.example.chris.gisamiapp.R;

import activity.LoginActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                Intent intent= new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        },4000);
    }
}