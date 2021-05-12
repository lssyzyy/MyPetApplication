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

import com.example.mypetapplication.dataHelper.MyDatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.mypetapplication.MainActivity.convertIconToString;
import static com.example.mypetapplication.MainActivity.convertStringToIcon;

public class PetReleaseDetailInfoEditActivity extends AppCompatActivity {

    private EditText pet_detail_title_edit,pet_detail_price_edit,pet_detail_topic_edit,pet_detail_content_edit,pet_detail_yimiao_edit;
    private ImageView pet_detail_img_edit;
    private String pet_id;
    private String pet_img;
    private Bitmap photo;
    private String picPath;
    private SQLiteDatabase db;
    private Button back;
    MyDatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_release_detail_info_edit);

        helper = new MyDatabaseHelper(this, "petdata", null, 1);
        db = helper.getWritableDatabase();
        pet_detail_img_edit=findViewById(R.id.pet_detail_img_edit);
        pet_detail_title_edit=findViewById(R.id.pet_detail_title_edit);
        pet_detail_price_edit=findViewById(R.id.pet_detail_price_edit);
        pet_detail_topic_edit=findViewById(R.id.pet_detail_topic_edit);
        pet_detail_content_edit=findViewById(R.id.pet_detail_content_edit);
        pet_detail_yimiao_edit=findViewById(R.id.pet_detail_yimiao_edit);
        Intent intent = this.getIntent();
        pet_id=intent.getStringExtra(PetReleaseDetailinfoActivity.PET_DETAIL_ID);

        Toast.makeText(PetReleaseDetailInfoEditActivity.this,pet_id,Toast.LENGTH_SHORT).show();

        pet_img=intent.getStringExtra(PetReleaseDetailinfoActivity.PET_DETAIL_IMG);
        String pet_title=intent.getStringExtra(PetReleaseDetailinfoActivity.PET_DETAIL_TITLE);
        String pet_price=intent.getStringExtra(PetReleaseDetailinfoActivity.PET_DETAIL_PRICE);
        String pet_topic=intent.getStringExtra(PetReleaseDetailinfoActivity.PET_DETAIL_TOPIC);
        String pet_content=intent.getStringExtra(PetReleaseDetailinfoActivity.PET_DETAIL_CONTENT);
        String pet_yimiao=intent.getStringExtra(PetReleaseDetailinfoActivity.PET_DETAIL_YIMIAO);
        Bitmap bitmap=convertStringToIcon(pet_img);
        pet_detail_img_edit.setImageBitmap(bitmap);
        pet_detail_title_edit.setText(pet_title);
        pet_detail_price_edit.setText(pet_price);
        pet_detail_topic_edit.setText(pet_topic);
        pet_detail_content_edit.setText(pet_content);
        pet_detail_yimiao_edit.setText(pet_yimiao);

        Button pet_detail_ok=findViewById(R.id.pet_detail_ok);
        pet_detail_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editpetdetailinfo();
                Toast.makeText(PetReleaseDetailInfoEditActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        back=findViewById(R.id.pet_detail_edit_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pet_detail_img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                View view = View.inflate(PetReleaseDetailInfoEditActivity.this, R.layout.popwindow, null);
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
                            Toast.makeText(getApplicationContext(), pet_img,Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PetReleaseDetailInfoEditActivity.this, "内存不可用", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //相册
                btn_album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, 2);
                        Toast.makeText(getApplicationContext(), pet_img,Toast.LENGTH_SHORT).show();
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

    private void editpetdetailinfo(){
        Bitmap bitmap = ((BitmapDrawable)pet_detail_img_edit.getDrawable()).getBitmap();
        pet_img=convertIconToString(bitmap);
        ContentValues contentValues=new ContentValues();
        contentValues.put("petimg",pet_img);
        contentValues.put("pettitle",pet_detail_title_edit.getText().toString());
        contentValues.put("pettopic",pet_detail_topic_edit.getText().toString());
        contentValues.put("petprice",pet_detail_price_edit.getText().toString());
        contentValues.put("petcontent",pet_detail_content_edit.getText().toString());
        contentValues.put("petyimiao",pet_detail_yimiao_edit.getText().toString());
        db.update("petsdb",contentValues,"id=?",new String[]{pet_id});
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
                                    pet_img=convertIconToString(this.photo);
                                    pet_detail_img_edit.setImageBitmap(this.photo);
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
                    pet_img=convertIconToString(BitmapFactory.decodeFile(picturePath));
                    pet_detail_img_edit.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                }
                default:
                    break;
            }
        }
    }
}
