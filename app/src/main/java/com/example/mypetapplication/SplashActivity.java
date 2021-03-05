package com.example.mypetapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private TextView tg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.page1);
        final Thread myThread=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5000);
                    Intent it=new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(it);
                    finish();//关闭当前活动
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

        tg=findViewById(R.id.tg);
        tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(it);
                myThread.interrupt();
                finish();
            }
        });
    }

}