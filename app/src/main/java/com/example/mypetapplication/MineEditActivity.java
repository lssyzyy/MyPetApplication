package com.example.mypetapplication;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypetapplication.dataHelper.MyFrienddataHelper;
import com.example.mypetapplication.dataHelper.MyUserdataHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.mypetapplication.MainActivity.convertIconToString;
import static com.example.mypetapplication.MainActivity.convertStringToIcon;

public class MineEditActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    MyUserdataHelper helper;
    MyFrienddataHelper friendhelper;
    EditText nickname,sign,address;
    String username,userimg2,nickname2,address2,sign2;
    private ImageView imageview;
    private Bitmap photo;
    private String picPath;
    private String userimg;
    public static MineEditActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useredit);
        helper = new MyUserdataHelper(this, "userdata", null, 1);
        db = helper.getWritableDatabase();
        Button ok=findViewById(R.id.user_info_ok);
        nickname=findViewById(R.id.user_nickname);
        sign=findViewById(R.id.user_sign);
        address=findViewById(R.id.user_address);
        imageview =(ImageView)findViewById(R.id.user_photo);
        Intent intent = this.getIntent();
        username = intent.getStringExtra(MainActivity.USER_NAME2);
        userimg2 = intent.getStringExtra(MainActivity.USER_IMG);
        nickname2 = intent.getStringExtra(MainActivity.NICK_NAME);
        address2 = intent.getStringExtra(MainActivity.ADDRESS);
        sign2 = intent.getStringExtra(MainActivity.SIGN);

        if(userimg2.length()!=0){
            imageview.setImageBitmap(convertStringToIcon(userimg2));
        }
        nickname.setText(nickname2);
        address.setText(address2);
        sign.setText(sign2);
        Toast.makeText(MineEditActivity.this, username, Toast.LENGTH_SHORT).show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nickname.getText().toString().length()==0||sign.getText().toString().length()==0||address.getText().toString().length()==0){
                    Toast.makeText(MineEditActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
                }else if(nickname.getText().toString().length()>10){
                    Toast.makeText(MineEditActivity.this,"昵称长度不得超过10个字",Toast.LENGTH_SHORT).show();
                }else{
                    editinfo();
                    updatefriend();
                    Toast.makeText(MineEditActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MineEditActivity.this, MainActivity.class);
                    startActivity(intent);
                    MainActivity.instance.finish();
                    finish();
                }
            }
        });

        Button back=findViewById(R.id.edit_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MineEditActivity.this.finish();
            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                View view = View.inflate(MineEditActivity.this, R.layout.popwindow, null);
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
                            Toast.makeText(getApplicationContext(), userimg,Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MineEditActivity.this, "内存不可用", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //相册
                btn_album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 2);
                        Toast.makeText(getApplicationContext(), userimg,Toast.LENGTH_SHORT).show();
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

    }
    private void editinfo(){
        Bitmap bitmap = ((BitmapDrawable)imageview.getDrawable()).getBitmap();
        userimg=convertIconToString(bitmap);
        ContentValues contentValues=new ContentValues();
        contentValues.put("userimg",userimg);
        contentValues.put("nickname",nickname.getText().toString());
        contentValues.put("sign",sign.getText().toString());
        contentValues.put("address",address.getText().toString());
        db.update("userinfo",contentValues,"username=?",new String[]{username});
    }
    private void updatefriend(){
        friendhelper=new MyFrienddataHelper(this,"frienddata",null,1);
        db=friendhelper.getWritableDatabase();
        Bitmap bitmap = ((BitmapDrawable)imageview.getDrawable()).getBitmap();
        userimg=convertIconToString(bitmap);
        ContentValues contentValues=new ContentValues();
        contentValues.put("friendimg",userimg);
        contentValues.put("friendnickname",nickname.getText().toString());
        db.update("friend",contentValues,"friendname=?",new String[]{username});
    }
    //处理图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1://打开相机
                    if (data.getData() != null || data.getExtras() != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            this.photo = BitmapFactory.decodeFile(uri.getPath());
                        }
                        if (photo == null) {
                            Bundle bundle = data.getExtras();
                            if (bundle != null) {
                                photo = (Bitmap) bundle.get("data");
                                FileOutputStream fileOutputStream = null;
                                try {
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
                                    userimg=convertIconToString(this.photo);
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
                case 2: {//打开相册并选择照片
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };// 获取选择照片的数据视图
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();// 从数据视图中获取已选择图片的路径
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    userimg=convertIconToString(BitmapFactory.decodeFile(picturePath));
                    imageview.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                }
                default:
                    break;
            }
        }
    }
}
