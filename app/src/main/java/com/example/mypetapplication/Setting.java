package com.example.mypetapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {
    Button btn_re;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        init();
        Switch switchComponent = (Switch)findViewById(R.id.night);
        switchComponent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(Setting.this, "夜间模式开", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Setting.this, "夜间模式关", Toast.LENGTH_SHORT).show();
                }
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