package com.example.mypetapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypetapplication.dataHelper.MyUserdataHelper;

public class ResActivity extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Button button;
    private SQLiteDatabase db;
    private TextView res_log;
    MyUserdataHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);


        editText1 = findViewById(R.id.Ed1);
        editText2 = findViewById(R.id.ED2);
        editText3 = findViewById(R.id.ED3);
        button = findViewById(R.id.BT3);
        helper=new MyUserdataHelper(this,"userdata",null,1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=helper.getWritableDatabase();
                if(editText2.getText().toString().equals(editText3.getText().toString())&&editText1.getText().toString().length()!=0&&editText2.getText().toString().length()!=0&&editText3.getText().toString().length()!=0){
                    Instert();
                    Toast.makeText(ResActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ResActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else if(editText1.getText().toString().length()==0||editText2.getText().toString().length()==0||editText3.getText().toString().length()==0){
                    Toast.makeText(ResActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResActivity.this,"密码不一致",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //添加下划线
        res_log=findViewById(R.id.res_log);
        res_log.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        res_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void Instert(){
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",editText1.getText().toString());
        contentValues.put("pwd",editText2.getText().toString());
        db.insert("user",null,contentValues);
    }
}