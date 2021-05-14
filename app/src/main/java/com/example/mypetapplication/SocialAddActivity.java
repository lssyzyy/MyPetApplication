package com.example.mypetapplication;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.mypetapplication.Bean.BeanUserInfo;
import com.example.mypetapplication.dataHelper.MyFrienddataHelper;
import com.example.mypetapplication.dataHelper.MyUserdataHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mypetapplication.MainActivity.convertIconToString;

public class SocialAddActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    MyFrienddataHelper helper;
    MyUserdataHelper userhelper;
    private ImageView social_img;
    private Button social_add_ok;
    private EditText social_think;
    private Bitmap photo;
    private String picPath;
    private String friend_img;
    private String friend_name;
    private String friend_content_img;
    private String username1;
    private List<BeanUserInfo> userlist = new ArrayList<>();
    ArrayList<String> username = new ArrayList<String>();
    ArrayList<String> userimg = new ArrayList<String>();
    ArrayList<String> usernickname = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_add);

        userhelper = new MyUserdataHelper(this, "userdata", null, 1);
        db=userhelper.getWritableDatabase();
        initUserDatas();
        Button back=findViewById(R.id.social_add_back);
        back.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v){
                SocialAddActivity.this.finish();
            }
        });


        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        username1 = settings.getString("Username", "").toString();;
        for(int i=0;i<userlist.size();i++){
            if(userlist.get(i).getUsername().equals(username1)){
                friend_name=userlist.get(i).getNickname();
                friend_img=userlist.get(i).getUserimg();
            }
        }

        social_img=findViewById(R.id.social_img);
        social_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                View view = View.inflate(SocialAddActivity.this, R.layout.popwindow, null);
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
                            Toast.makeText(getApplicationContext(), friend_content_img,Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SocialAddActivity.this, "内存不可用", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //相册
                btn_album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 2);
                        Toast.makeText(getApplicationContext(), friend_content_img,Toast.LENGTH_SHORT).show();
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

        social_add_ok=findViewById(R.id.social_add_ok);
        social_add_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(social_think.getText().toString().length()==0){
                    Toast.makeText(SocialAddActivity.this,"发表内容不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    insertsocial();
                    Toast.makeText(SocialAddActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SocialAddActivity.this, MainActivity.class);
                    startActivity(intent);
                    MainActivity.instance.finish();
                    finish();
                }
            }
        });
        social_think=findViewById(R.id.social_think);
    }

    private void insertsocial(){
        helper=new MyFrienddataHelper(this,"frienddata",null,1);
        db=helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("friendimg",friend_img);
        contentValues.put("friendnickname",friend_name);
        contentValues.put("friendname",username1);
        contentValues.put("friendcontent",social_think.getText().toString());
        contentValues.put("friendcontentimg",friend_content_img);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        contentValues.put("frienddate",simpleDateFormat.format(date));
        db.insert("friend",null,contentValues);
    }
    //初始化用户数据
    private void initUserDatas() {
        userlist = queryUserAllContent();
        for (BeanUserInfo d : userlist) {
            if (d != null) {
                username.add(d.getUsername());
                userimg.add(d.getUserimg());
                usernickname.add(d.getNickname());
            }
        }
    }
    public ArrayList<BeanUserInfo> queryUserAllContent() {
        ArrayList<BeanUserInfo> datas = new ArrayList<>();
        Cursor cursor = db.query("userinfo", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BeanUserInfo data = null;
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String userimg = cursor.getString(cursor.getColumnIndex("userimg"));
            String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String sign = cursor.getString(cursor.getColumnIndex("sign"));
            data = new BeanUserInfo(username,userimg,nickname,address,sign);
            datas.add(data);
        }
        cursor.close();
        return datas;
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
                                    friend_content_img=convertIconToString(this.photo);
                                    social_img.setImageBitmap(this.photo);
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
                    friend_content_img=convertIconToString(BitmapFactory.decodeFile(picturePath));
                    social_img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                }
                default:
                    break;
            }
        }
    }
}
