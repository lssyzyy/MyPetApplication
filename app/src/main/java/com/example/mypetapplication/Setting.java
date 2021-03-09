package com.example.mypetapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Setting extends AppCompatActivity {
    Button btn_re;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        init();
        Button switchComponent = findViewById(R.id.night);
        switchComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if(mode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
            }
        });



    }
    private void init(){
        btn_re=findViewById(R.id.set_back);
        btn_re.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
                Setting.this.finish();
            }
        });
    }
    public void clear(View v){
        Toast.makeText(Setting.this, "清理成功", Toast.LENGTH_SHORT).show();
    }

}