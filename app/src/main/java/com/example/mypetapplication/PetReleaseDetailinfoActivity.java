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

import static com.example.mypetapplication.MainActivity.convertStringToIcon;


public class PetReleaseDetailinfoActivity extends AppCompatActivity {
    private ImageView pet_detail_img;
    private TextView pet_detail_title,pet_detail_topic,pet_detail_content,pet_detail_price,pet_detail_yimiao;
    private Button pet_back,pet_delete,pet_edit;
    boolean isChanged=false;
    public static final String PET_DETAIL_ID = "pet_detail_id";
    public static final String PET_DETAIL_IMG = "pet_detail_img";
    public static final String PET_DETAIL_TITLE = "pet_detail_title";
    public static final String PET_DETAIL_TOPIC = "pet_detail_topic";
    public static final String PET_DETAIL_PRICE = "pet_detail_price";
    public static final String PET_DETAIL_CONTENT = "pet_detail_content";
    public static final String PET_DETAIL_YIMIAO = "pet_detail_yimiao";
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
        final String pet_id = intent.getStringExtra(PetReleaseActivity.PET_ID2);
        final String pet_img = intent.getStringExtra(PetReleaseActivity.PET_IMG2);
        final String pet_title = intent.getStringExtra(PetReleaseActivity.PET_TITLE2);
        final String pet_topic = intent.getStringExtra(PetReleaseActivity.PET_TOPIC2);
        final String pet_price = intent.getStringExtra(PetReleaseActivity.PET_PRICE2);
        final String pet_content = intent.getStringExtra(PetReleaseActivity.PET_CONTENT2);
        final String pet_yimiao = intent.getStringExtra(PetReleaseActivity.PET_YIMIAO2);
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
                PetReleaseDetailinfoActivity.this.finish();
            }
        });

        pet_edit=findViewById(R.id.release_edit);
        pet_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PetReleaseDetailinfoActivity.this,PetReleaseDetailInfoEditActivity.class);
                intent.putExtra(PET_DETAIL_ID, pet_id);
                intent.putExtra(PET_DETAIL_IMG, pet_img);
                intent.putExtra(PET_DETAIL_TITLE, pet_detail_title.getText().toString());
                intent.putExtra(PET_DETAIL_TOPIC, pet_detail_topic.getText().toString());
                intent.putExtra(PET_DETAIL_PRICE, pet_detail_price.getText().toString());
                intent.putExtra(PET_DETAIL_CONTENT, pet_detail_content.getText().toString());
                intent.putExtra(PET_DETAIL_YIMIAO, pet_detail_yimiao.getText().toString());
                startActivity(intent);
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
                PetReleaseActivity.instance1.finish();
                finish();
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