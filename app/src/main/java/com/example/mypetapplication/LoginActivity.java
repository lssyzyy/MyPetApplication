package com.example.mypetapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypetapplication.dataHelper.MyUserdataHelper;
import com.facebook.stetho.Stetho;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText pwd;
    private Button login;
    private Button res;
    private SQLiteDatabase db;
    private CheckBox rem_pwd;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public static final String USER_NAME = "username";

    MyUserdataHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Stetho.initializeWithDefaults(this);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        username=findViewById(R.id.Ed1);
        pwd=findViewById(R.id.ED2);
        login=findViewById(R.id.BT1);
        res=findViewById(R.id.BT2);
        rem_pwd=(CheckBox)findViewById(R.id.remember_pass);
        helper=new MyUserdataHelper(this,"userdata",null,1);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = username.getText().toString();
                String regEx = "[^a-zA-Z0-9]";  //只能输入字母或数字
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(editable);
                String str = m.replaceAll("").trim();    //删掉不是字母或数字的字符
                if(!editable.equals(str)){
                    username.setText(str);  //设置EditText的字符
                    username.setSelection(str.length()); //因为删除了字符，要重写设置新的光标所在位置
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String pass=pwd.getText().toString();
                editor=pref.edit();
                if(username.equals("")||pwd.equals("")){
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_LONG).show();
                }else {
                    if (login(name,pass)){
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        Intent user=new Intent(LoginActivity.this,MainActivity.class);
                        user.putExtra(USER_NAME,username.getText().toString());
                        startActivity(user);
                        finish();
                        if(rem_pwd.isChecked()){
                            editor.putBoolean("remember_pwd",true);
                            editor.putString("username",name);
                            editor.putString("password",pass);
                        }else {
                            editor.clear();
                        }
                        editor.commit();
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ResActivity.class);
                startActivity(intent);
                finish();
            }
        });
        boolean isRemeber=pref.getBoolean("remember_pwd",false);
        if(isRemeber){
            String name=pref.getString("username","");
            String password=pref.getString("password","");
            username.setText(name);
            pwd.setText(password);
            rem_pwd.setChecked(true);
        }
    }
    public boolean login(String username,String pwd){
        db=helper.getReadableDatabase();
        String sql="select * from user where username=? and pwd=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username,pwd});
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        return false;
    }
}