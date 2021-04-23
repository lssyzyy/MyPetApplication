package com.example.mypetapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypetapplication.dataHelper.MyDatabaseHelper;


public class PetReleaseDetailinfoActivity extends AppCompatActivity {
    private ImageView pet_detail_img;
    private TextView pet_detail_title,pet_detail_topic,pet_detail_content,pet_detail_price,pet_detail_yimiao;
    private Button pet_back,pet_delete;
    boolean isChanged=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_release_detail_info);

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
        pet_detail_title.setText(pet_title);
        pet_detail_topic.setText(pet_topic);
        pet_detail_price.setText(pet_price);
        pet_detail_content.setText(pet_content);
        pet_detail_yimiao.setText(pet_yimiao);

        pet_back=findViewById(R.id.pet_detail_back);
        pet_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PetReleaseDetailinfoActivity.this.finish();
            }
        });

        pet_delete=findViewById(R.id.pet_delete);
        pet_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petdelete(pet_title);
                Toast.makeText(PetReleaseDetailinfoActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PetReleaseDetailinfoActivity.this, PetReleaseActivity.class);
                startActivity(intent);
                PetReleaseDetailinfoActivity.this.finish();
            }
        });

    }
    private void petdelete(String pettitle){
        MyDatabaseHelper helper = new MyDatabaseHelper(this, "petdata", null,1);
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete("petsdb","pettitle=?",new String[]{pettitle});
        db.close();
    }
}