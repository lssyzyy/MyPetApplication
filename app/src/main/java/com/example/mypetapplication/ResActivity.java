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

import com.example.mypetapplication.Bean.BeanUser;
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
        ContentValues contentValues2=new ContentValues();
        contentValues2.put("username",editText1.getText().toString());
        contentValues2.put("userimg","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");
        contentValues2.put("nickname","阿狗");
        contentValues2.put("sign","我是一只快乐的阿猫");
        contentValues2.put("address","二仙桥");
        db.insert("userinfo",null,contentValues2);
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
            int userid = cursor.getInt(cursor.getColumnIndex("userid"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String userpassword = cursor.getString(cursor.getColumnIndex("pwd"));
            data = new BeanUser(userid, username, userpassword);
            datas.add(data);
        }
        cursor.close();
        return datas;
    }
}