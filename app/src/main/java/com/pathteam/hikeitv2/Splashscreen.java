package com.pathteam.hikeitv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by alexhughes on 11/21/16.
 */

public class Splashscreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // im not sure we need to save this, but leave it for now.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
// This runns for 3 sec then sends you to the main activity. 
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(Splashscreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // lets us click out of the screen and pause the app
        super.onPause();
        finish();
    }

}