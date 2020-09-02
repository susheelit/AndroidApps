package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.common.SharedPref;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    Context context;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void getIp(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try(final DatagramSocket socket = new DatagramSocket()){
                    socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                    System.out.println("System IP Address 00 : " +socket.getLocalAddress().getHostAddress());
               String ip = socket.getLocalAddress().getHostAddress();
                    Log.e("Exception 1 ", ""+ip);

                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("Exception 1 ", ""+e.toString());
                }
            }
        }).start();
    }

    private void initView() {
        context = SplashActivity.this;
        progressBar = findViewById(R.id.progressBar);
       // attemptLogin();
        openDasboard();
    }

    private void openDasboard() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(SplashActivity.this, DasboardNewActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, 1000);

     //  LoginActivity.attemptLogin(context, SharedPref.getEmailId(context), SharedPref.getPassword(context));


    }
}
