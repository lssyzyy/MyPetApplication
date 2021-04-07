package com.example.mypetapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypetapplication.Bean.BeanPet;
import com.example.mypetapplication.Bean.BeanUser;
import com.example.mypetapplication.dataHelper.MyDatabaseHelper;
import com.example.mypetapplication.dataHelper.MyUserdataHelper;
import com.example.mypetapplication.service.SendDateToServer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResActivity extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Button button;
    private SQLiteDatabase db;
    private TextView res_log;
    MyUserdataHelper helper;
    private List<BeanUser> userlist = new ArrayList<>();
    ArrayList<Integer> userid = new ArrayList<Integer>();
    ArrayList<String> username = new ArrayList<String>();
    ArrayList<String> userpassword = new ArrayList<String>();

    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SendDateToServer.SEND_SUCCESS:
                    Toast.makeText(ResActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    break;
                case SendDateToServer.SEND_FAIL:
                    Toast.makeText(ResActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);

        helper = new MyUserdataHelper(this, "userdata", null, 1);
        db = helper.getWritableDatabase();
        initDatas();

        editText1 = findViewById(R.id.Ed1);
        editText2 = findViewById(R.id.ED2);
        editText3 = findViewById(R.id.ED3);
        button = findViewById(R.id.BT3);
        helper=new MyUserdataHelper(this,"userdata",null,1);

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = editText1.getText().toString();
                String regEx = "[^a-zA-Z0-9]";  //只能输入字母或数字
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(editable);
                String str = m.replaceAll("").trim();    //删掉不是字母或数字的字符
                if(!editable.equals(str)){
                    editText1.setText(str);  //设置EditText的字符
                    editText1.setSelection(str.length()); //因为删除了字符，要重写设置新的光标所在位置
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=helper.getWritableDatabase();
                if(editText2.getText().toString().equals(editText3.getText().toString())&&editText1.getText().toString().length()!=0&&editText2.getText().toString().length()!=0&&editText3.getText().toString().length()!=0){
                    int flag=0;
                    for(int i=0;i<userlist.size();i++){
                        if(username.get(i).equals(editText1.getText().toString())){
                            Toast.makeText(ResActivity.this,"该用户名已存在",Toast.LENGTH_SHORT).show();
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0){
                        Instert();
                        Toast.makeText(ResActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        new SendDateToServer(handler).SendDataToServer(editText1.getText().toString(),editText2.getText().toString());
                        Intent intent=new Intent(ResActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
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

    //初始化数据
    private void initDatas() {
        userlist = queryAllContent();
        for (BeanUser d : userlist) {
            if (d != null) {
                userid.add(d.getUserid());
                username.add(d.getUsername());
                userpassword.add(d.getUserpassword());
            }
        }
    }

    public ArrayList<BeanUser> queryAllContent() {
        ArrayList<BeanUser> datas = new ArrayList<>();
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BeanUser data = null;
            int userid = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String userpassword = cursor.getString(cursor.getColumnIndex("pwd"));
            data = new BeanUser(userid, username, userpassword);
            datas.add(data);
        }
        cursor.close();
        return datas;
    }
}