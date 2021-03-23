package com.example.mypetapplication;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.mypetapplication.Other.DataCleanManager;

public class Setting extends AppCompatActivity {
    Button btn_re;
    private TextView tvData;
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
        //清除缓存
        LinearLayout llCleanData = (LinearLayout) findViewById(R.id.data_clean);
        tvData = (TextView) findViewById(R.id.tv_data);
        try {
            String data = DataCleanManager.getTotalCacheSize(Setting.this);
            tvData.setText(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        llCleanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(Setting.this);
                build.setTitle("清除缓存提醒")
                        .setMessage("确定要清除缓存吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                DataCleanManager.clearAllCache(Setting.this);
                                tvData.setText("0.00k");
                                Toast.makeText(Setting.this, "缓存已清理", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        })
                        .show();
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