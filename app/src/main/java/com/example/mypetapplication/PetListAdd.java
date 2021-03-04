package com.example.mypetapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PetListAdd extends AppCompatActivity {
    private SQLiteDatabase db;
    private PetAdapter petadapter;
    MyDatabaseHelper helper;
    private EditText title,name,money,content;
    private ImageView imageview;
    private RadioGroup rg;
    private Button btn_pet_add_ok,btn_re;
    private List<BeanPet> petlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petinfoadd);


        helper=new MyDatabaseHelper(this,"petdata",null,1);
        db=helper.getWritableDatabase();

        title=(EditText)findViewById(R.id.pet_title);
        name=(EditText)findViewById(R.id.pet_name);
        money=(EditText)findViewById(R.id.pet_money);
        content=(EditText)findViewById(R.id.pet_content);
        rg=(RadioGroup)findViewById(R.id.pet_gp);

        imageview =(ImageView)findViewById(R.id.pet_picture);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(PetListAdd.this, R.layout.popwindow, null);
                Button btn_album = view.findViewById(R.id.pop_album);
                Button btn_camera = view.findViewById(R.id.pop_camera);
                Button btn_cancle = view.findViewById(R.id.pop_cancel);
                final PopupWindow popupwindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                popupwindow.setFocusable(true);
                btn_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,1);
                        popupwindow.dismiss();
                    }
                });
                btn_album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK,null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 0x1);
                        popupwindow.dismiss();
                    }
                });
                btn_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupwindow.dismiss();

                    }
                });
                //popupWindow消失屏幕变为不透明
                popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1.0f;
                        getWindow().setAttributes(lp);
                    }
                });
                //popupWindow出现屏幕变为半透明
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;
                getWindow().setAttributes(lp);
                popupwindow.showAtLocation(view, Gravity.BOTTOM,0,50);
            }

        });

        btn_pet_add_ok=findViewById(R.id.pet_add_ok);
        btn_pet_add_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money.getText().toString().length()==0||title.getText().toString().length()==0||content.getText().toString().length()==0){
                    Toast.makeText(PetListAdd.this,"文本框不得有空",Toast.LENGTH_SHORT).show();
                }else if(money.getText().toString().length()!=0&&title.getText().toString().length()!=0&&content.getText().toString().length()!=0){
                    Insertdata();
                    Toast.makeText(PetListAdd.this,"添加成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(PetListAdd.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        btn_re=findViewById(R.id.pet_add_back);
        btn_re.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
                PetListAdd.this.finish();
            }
        });
    }
    private void Insertdata(){
        ContentValues contentValues=new ContentValues();
        contentValues.put("pettitle",title.getText().toString());
        contentValues.put("pettopic",name.getText().toString());
        contentValues.put("petprice",money.getText().toString()+"元");
        contentValues.put("petcontent",content.getText().toString());
        db.insert("petsdb",null,contentValues);
    }

    //获取本地图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                imageview.setImageURI(data.getData());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
