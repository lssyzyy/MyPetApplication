package com.example.mypetapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypetapplication.service.DBHelper;

public class SplashActivity extends AppCompatActivity {
    private TextView daojishi;
    private LinearLayout tg;
    private int time=5;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            daojishi.setText(time+"s");
            if (time==0){
                handler.removeMessages(0);
            }
            handler.sendEmptyMessageDelayed(0,1000);
        }
    };

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
        daojishi=findViewById(R.id.djstime);
        handler.sendEmptyMessageDelayed(0,1000);

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