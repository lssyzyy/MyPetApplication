package com.example.mypetapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PetDetailinfoActivity extends AppCompatActivity {
    private ImageView pet_detail_img;
    private TextView pet_detail_title,pet_detail_topic,pet_detail_content,pet_detail_price;
    private Button pet_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail_info);


        pet_detail_img=findViewById(R.id.pet_detail_img);
        pet_detail_title=findViewById(R.id.pet_detail_title);
        pet_detail_topic=findViewById(R.id.pet_detail_topic);
        pet_detail_price=findViewById(R.id.pet_detail_price);
        pet_detail_content=findViewById(R.id.pet_detail_content);
        Intent intent = this.getIntent();
        String pet_title = intent.getStringExtra(MainActivity.PET_TITLE);
        String pet_topic = intent.getStringExtra(MainActivity.PET_TOPIC);
        String pet_price = intent.getStringExtra(MainActivity.PET_PRICE);
        String pet_content = intent.getStringExtra(MainActivity.PET_CONTENT);
        pet_detail_title.setText(pet_title);
        pet_detail_topic.setText(pet_topic);
        pet_detail_price.setText(pet_price);
        pet_detail_content.setText(pet_content);

        pet_back=findViewById(R.id.pet_detail_back);
        pet_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PetDetailinfoActivity.this.finish();
            }
        });
    }
}