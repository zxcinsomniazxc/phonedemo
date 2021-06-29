package com.example.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        Timer timer = new Timer();
        TimerTask ts = new TimerTask() {
            @Override
            public void run() {
                boolean firstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstRun", true);

                if(firstRun){
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstRun", false).apply();

                    startActivity(new Intent(LoadingScreen.this, SignUp.class));
                }
                else{
                    startActivity(new Intent(LoadingScreen.this, SignIn.class));
                }
            }
        };

        timer.schedule(ts, 3000L);
    }
}