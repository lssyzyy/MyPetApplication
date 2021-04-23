package com.example.mypetapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypetapplication.dataHelper.MyDatabaseHelper;

import static com.example.mypetapplication.MainActivity.convertStringToIcon;

public class PetDetailinfoActivity extends AppCompatActivity {
    private ImageView pet_detail_img;
    private TextView pet_detail_title,pet_detail_topic,pet_detail_content,pet_detail_price,pet_detail_yimiao;
    private Button pet_back,pet_adopt;
    boolean isChanged=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail_info);

        pet_detail_img=findViewById(R.id.pet_detail_img);
        pet_detail_title=findViewById(R.id.pet_detail_title);
        pet_detail_topic=findViewById(R.id.pet_detail_topic);
        pet_detail_price=findViewById(R.id.pet_detail_price);
        pet_detail_content=findViewById(R.id.pet_detail_content);
        pet_detail_yimiao=findViewById(R.id.pet_detail_yimiao);
        Intent intent = this.getIntent();
        String pet_img = intent.getStringExtra(MainActivity.PET_IMG);
        final String pet_title = intent.getStringExtra(MainActivity.PET_TITLE);
        String pet_topic = intent.getStringExtra(MainActivity.PET_TOPIC);
        String pet_price = intent.getStringExtra(MainActivity.PET_PRICE);
        String pet_content = intent.getStringExtra(MainActivity.PET_CONTENT);
        String pet_yimiao = intent.getStringExtra(MainActivity.PET_YIMIAO);
        Bitmap bitmap=convertStringToIcon(pet_img);
        pet_detail_img.setImageBitmap(bitmap);
        pet_detail_title.setText(pet_title);
        pet_detail_topic.setText(pet_topic);
        pet_detail_price.setText(pet_price);
        pet_detail_content.setText(pet_content);
        pet_detail_yimiao.setText(pet_yimiao);

        pet_back=findViewById(R.id.pet_detail_back);
        pet_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PetDetailinfoActivity.this.finish();
            }
        });

        pet_adopt=findViewById(R.id.pet_adopt);
        pet_adopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build = new AlertDialog.Builder(PetDetailinfoActivity.this);
                build.setTitle("注意")
                        .setMessage("确定要领养该宠物吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                //添加一个领养信息
                                petadopt(pet_title);
                                dialog.dismiss();
                                Toast.makeText(PetDetailinfoActivity.this,"领养成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PetDetailinfoActivity.this, MainActivity.class);
                                startActivity(intent);
                                PetDetailinfoActivity.this.finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

    }
    private void petadopt(String pettitle){
        MyDatabaseHelper helper = new MyDatabaseHelper(this, "petdata", null,1);
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete("petsdb","pettitle=?",new String[]{pettitle});
        ContentValues contentValues=new ContentValues();
        contentValues.put("pettitle",pet_detail_title.getText().toString());
        contentValues.put("pettopic",pet_detail_topic.getText().toString());
        contentValues.put("petprice",pet_detail_price.getText().toString());
        contentValues.put("petcontent",pet_detail_content.getText().toString());
        contentValues.put("petyimiao",pet_detail_yimiao.getText().toString());
        db.insert("petsadopt",null,contentValues);
        db.close();
    }
}