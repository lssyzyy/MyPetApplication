package com.example.mypetapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypetapplication.Adapter.PetAdapter;
import com.example.mypetapplication.Bean.BeanPet;
import com.example.mypetapplication.dataHelper.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.mypetapplication.MainActivity.PET_CONTENT;
import static com.example.mypetapplication.MainActivity.PET_IMG;
import static com.example.mypetapplication.MainActivity.PET_PRICE;
import static com.example.mypetapplication.MainActivity.PET_TITLE;
import static com.example.mypetapplication.MainActivity.PET_TOPIC;
import static com.example.mypetapplication.MainActivity.PET_YIMIAO;

public class PetReleaseActivity  extends AppCompatActivity {

    private SQLiteDatabase db;
    MyDatabaseHelper helper;
    private ListView listView;
    private PetAdapter petadapter;
    private List<BeanPet> petlist = new ArrayList<>();
    ArrayList<String> petimg = new ArrayList<String>();
    ArrayList<String> pettitle = new ArrayList<String>();
    ArrayList<String> pettopic = new ArrayList<String>();
    ArrayList<String> petprice = new ArrayList<String>();
    ArrayList<String> petcontent = new ArrayList<String>();
    ArrayList<String> petyimiao = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);

        helper = new MyDatabaseHelper(this, "petdata", null, 1);
        db = helper.getWritableDatabase();
        initDatas();
        Button btn2 = findViewById(R.id.release_back);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetReleaseActivity.this.finish();
            }
        });

        //显示宠物列表信息
        petadapter = new PetAdapter(this, R.layout.pets_list, petlist);
        listView = findViewById(R.id.releaselist);
        listView.setAdapter(petadapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeanPet petl = petlist.get(position);
                Intent intent = new Intent(PetReleaseActivity.this, PetReleaseDetailinfoActivity.class);
                String petimg = petl.getPetimg();
                String pettitle = petl.getPettitle();
                String pettopic = petl.getPettopic();
                String petcontent = petl.getPetcontent();
                String petprice = petl.getPetprice();
                String petyimiao = petl.getPetyimiao();
                intent.putExtra(PET_IMG, petimg);
                intent.putExtra(PET_TITLE, pettitle);
                intent.putExtra(PET_TOPIC, pettopic);
                intent.putExtra(PET_PRICE, petprice);
                intent.putExtra(PET_CONTENT, petcontent);
                intent.putExtra(PET_YIMIAO, petyimiao);
                startActivity(intent);
                PetReleaseActivity.this.finish();
            }
        });
    }

    //初始化数据
    private void initDatas() {
        petlist = queryAllContent();
        for (BeanPet d : petlist) {
            if (d != null) {
                petimg.add(d.getPetimg());
                pettitle.add(d.getPettitle());
                pettopic.add(d.getPettopic());
                petprice.add(d.getPetprice());
                petcontent.add(d.getPetcontent());
                petyimiao.add(d.getPetyimiao());
            }
        }
    }
    public ArrayList<BeanPet> queryAllContent() {
        ArrayList<BeanPet> datas = new ArrayList<>();
        Cursor cursor = db.query("petsdb", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BeanPet data = null;
            int petid = cursor.getColumnIndex("id");
            String petimg = cursor.getString(cursor.getColumnIndex("petimg"));
            String pettitle = cursor.getString(cursor.getColumnIndex("pettitle"));
            String pettopic = cursor.getString(cursor.getColumnIndex("pettopic"));
            String petprice = cursor.getString(cursor.getColumnIndex("petprice"));
            String petcontent = cursor.getString(cursor.getColumnIndex("petcontent"));
            String petyimiao = cursor.getString(cursor.getColumnIndex("petyimiao"));
            data = new BeanPet(petid,petimg,pettitle, pettopic, petprice, petcontent,petyimiao);
            datas.add(data);
        }
        cursor.close();
        return datas;
    }
}
