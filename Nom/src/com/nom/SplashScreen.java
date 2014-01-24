package com.nom;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class SplashScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;            //set your time here......

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread welcomeThread = new Thread() {

            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(SPLASH_DISPLAY_LENGHT);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(SplashScreen.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
    
    
}