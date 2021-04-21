package com.example.mypetapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.mypetapplication.Adapter.PetAdapter;
import com.example.mypetapplication.Bean.BeanPet;
import com.example.mypetapplication.dataHelper.MyDatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PetListAdd extends AppCompatActivity {
    private SQLiteDatabase db;
    private PetAdapter petadapter;
    MyDatabaseHelper helper;
    private EditText title,name,money,content;
    private ImageView imageview;
    private Button btn_pet_add_ok,btn_re;
    private List<BeanPet> petlist=new ArrayList<>();
    private String petdetailimg;
    private String yimiao;
    private RadioButton checked;
    private Bitmap photo;
    private String picPath;
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
                //相机
                btn_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String state = Environment.getExternalStorageState();// 获取内存卡可用状态
                        if (state.equals(Environment.MEDIA_MOUNTED)) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            startActivityForResult(intent, 1);
                        } else {
                            Toast.makeText(PetListAdd.this, "内存不可用", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //相册
                btn_album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 2);
                        popupwindow.dismiss();
                    }
                });
                //取消按钮
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

        final RadioGroup rg=(RadioGroup)findViewById(R.id.pet_gp);
        btn_pet_add_ok=findViewById(R.id.pet_add_ok);
        btn_pet_add_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(money.getText().toString().length()==0||title.getText().toString().length()==0||content.getText().toString().length()==0){
                    Toast.makeText(PetListAdd.this,"文本框不得有空",Toast.LENGTH_SHORT).show();
                }else if(money.getText().toString().length()!=0&&title.getText().toString().length()!=0&&content.getText().toString().length()!=0){
                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            RadioButton radioButton=group.findViewById(checkedId);
                        }
                    });
                    checked=(RadioButton)rg.findViewById(rg.getCheckedRadioButtonId());
                    if(checked.getText().equals("是")==true){
                        yimiao="是";
                    }
                    else {
                        yimiao="否";
                    }
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
                Intent intent = new Intent(PetListAdd.this, MainActivity.class);
                startActivity(intent);
                PetListAdd.this.finish();
            }
        });
    }
    private void Insertdata(){
        ContentValues contentValues=new ContentValues();
        contentValues.put("petimg",petdetailimg);
        contentValues.put("pettitle",title.getText().toString());
        contentValues.put("pettopic",name.getText().toString());
        contentValues.put("petprice",money.getText().toString()+"元");
        contentValues.put("petcontent",content.getText().toString());
        contentValues.put("petyimiao",yimiao);
        db.insert("petsdb",null,contentValues);
    }
    //处理图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:// 两种方式 获取拍好的图片
                    if (data.getData() != null || data.getExtras() != null) { // 防止没有返回结果
                        Uri uri = data.getData();
                        if (uri != null) {
                            this.photo = BitmapFactory.decodeFile(uri.getPath()); // 拿到图片
                        }
                        if (photo == null) {
                            Bundle bundle = data.getExtras();
                            if (bundle != null) {
                                photo = (Bitmap) bundle.get("data");
                                FileOutputStream fileOutputStream = null;
                                try {// 获取 SD 卡根目录 生成图片并
                                    String saveDir = Environment
                                            .getExternalStorageDirectory()
                                            + "/zyy_Photos";// 新建目录
                                    File dir = new File(saveDir);
                                    if (!dir.exists())
                                        dir.mkdir();// 生成文件名
                                    SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
                                    String filename = "MT" + (t.format(new Date()))
                                            + ".jpg";// 新建文件
                                    File file = new File(saveDir, filename);// 打开文件输出流
                                    fileOutputStream = new FileOutputStream(file);// 生成图片文件
                                    this.photo.compress(Bitmap.CompressFormat.JPEG,
                                            100, fileOutputStream);// 相片的完整路径
                                    this.picPath = file.getPath();
                                    imageview.setImageBitmap(this.photo);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "获取到了",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "找不到图片",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    break;
                case 2: {//打开相册并选择照片，这个方式选择单张
                    // 获取返回的数据，这里是android自定义的Uri地址
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };// 获取选择照片的数据视图
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();// 从数据视图中获取已选择图片的路径
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    imageview.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                }
                default:
                    break;
            }
        }
    }
}
