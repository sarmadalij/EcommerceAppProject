package com.sarmadali.ecommerceappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread thread = new Thread(){
            public void run(){

                try {
                    sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
            }

        };
        thread.start();
    }
}