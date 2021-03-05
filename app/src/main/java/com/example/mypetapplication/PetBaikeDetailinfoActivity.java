package com.example.mypetapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PetBaikeDetailinfoActivity extends AppCompatActivity {
    private  final int SHOW_PETDATA = 0;
    private TextView pet_baike_detail_name,pet_baike_detail_engname,pet_baike_detail_characters,pet_baike_detail_nation,
            pet_baike_detail_easyOfDisease,pet_baike_detail_life,pet_baike_detail_price,pet_baike_detail_feature,
            pet_baike_detail_characterFeature,pet_baike_detail_careKnowledge,pet_baike_detail_feedPoints;
    private ImageView pet_baike_detail_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_baike_detail_info);

        pet_baike_detail_img=findViewById(R.id.pet_baike_detail_img);
        pet_baike_detail_name=findViewById(R.id.pet_baike_detail_name);
        pet_baike_detail_engname=findViewById(R.id.pet_baike_detail_engname);
        pet_baike_detail_characters=findViewById(R.id.pet_baike_detail_characters);
        pet_baike_detail_nation=findViewById(R.id.pet_baike_detail_nation);
        pet_baike_detail_easyOfDisease=findViewById(R.id.pet_baike_detail_easyOfDisease);
        pet_baike_detail_life=findViewById(R.id.pet_baike_detail_life);
        pet_baike_detail_price=findViewById(R.id.pet_baike_detail_price);
        pet_baike_detail_feature=findViewById(R.id.pet_baike_detail_feature);
        pet_baike_detail_characterFeature=findViewById(R.id.pet_baike_detail_characterFeature);
        pet_baike_detail_careKnowledge=findViewById(R.id.pet_baike_detail_careKnowledge);
        pet_baike_detail_feedPoints=findViewById(R.id.pet_baike_detail_feedPoints);

        Button btn2 = findViewById(R.id.pet_baike_detail_back);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetBaikeDetailinfoActivity.this.finish();
            }
        });

        Intent intent = this.getIntent();
        String pet_img = intent.getStringExtra(PetBaikeActivity.PET_IMG);
        String pet_name = intent.getStringExtra(PetBaikeActivity.PET_NAME);
        String pet_engname = intent.getStringExtra(PetBaikeActivity.PET_ENGNAME);
        String pet_characters = intent.getStringExtra(PetBaikeActivity.PET_CHARACTERS);
        String pet_nation = intent.getStringExtra(PetBaikeActivity.PET_NATION);
        String pet_easyOfDisease = intent.getStringExtra(PetBaikeActivity.PET_EASYOFDISEASE);
        String pet_life = intent.getStringExtra(PetBaikeActivity.PET_LIFE);
        String pet_price = intent.getStringExtra(PetBaikeActivity.PET_PRICE);
        String pet_feature = intent.getStringExtra(PetBaikeActivity.PET_FEATURE);
        String pet_characterFeature = intent.getStringExtra(PetBaikeActivity.PET_CHARACTERFEATURE);
        String pet_careKnowledge = intent.getStringExtra(PetBaikeActivity.PET_CAREKNOWLEDGE);
        String pet_feedPoints = intent.getStringExtra(PetBaikeActivity.PET_FEEDPOINTS);

        Glide.with(this)
                .load(pet_img)
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(pet_baike_detail_img);
        pet_baike_detail_name.setText(pet_name);
        pet_baike_detail_engname.setText(pet_engname);
        pet_baike_detail_characters.setText(pet_characters);
        pet_baike_detail_nation.setText(pet_nation);
        pet_baike_detail_easyOfDisease.setText(pet_easyOfDisease);
        pet_baike_detail_life.setText(pet_life);
        pet_baike_detail_price.setText(pet_price);
        pet_baike_detail_feature.setText(pet_feature);
        pet_baike_detail_characterFeature.setText(pet_characterFeature);
        pet_baike_detail_careKnowledge.setText(pet_careKnowledge);
        pet_baike_detail_feedPoints.setText(pet_feedPoints);

    }

}
