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

public class PetReleaseActivity  extends AppCompatActivity {

    private SQLiteDatabase db;
    MyDatabaseHelper helper;
    private ListView listView;
    private PetAdapter petadapter;
    private List<BeanPet> petlist = new ArrayList<>();
    ArrayList<Integer> petid = new ArrayList<Integer>();
    ArrayList<String> petimg = new ArrayList<String>();
    ArrayList<String> pettitle = new ArrayList<String>();
    ArrayList<String> pettopic = new ArrayList<String>();
    ArrayList<String> petprice = new ArrayList<String>();
    ArrayList<String> petcontent = new ArrayList<String>();
    ArrayList<String> petyimiao = new ArrayList<String>();
    public static final String PET_ID2 = "pet_id";
    public static final String PET_IMG2 = "pet_img";
    public static final String PET_TITLE2 = "pet_title";
    public static final String PET_TOPIC2 = "pet_topic";
    public static final String PET_PRICE2 = "pet_price";
    public static final String PET_CONTENT2 = "pet_content";
    public static final String PET_YIMIAO2 = "pet_yimiao";

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
                String petid = String.valueOf(petl.getPetid());
                String petimg = petl.getPetimg();
                String pettitle = petl.getPettitle();
                String pettopic = petl.getPettopic();
                String petcontent = petl.getPetcontent();
                String petprice = petl.getPetprice();
                String petyimiao = petl.getPetyimiao();
                intent.putExtra(PET_ID2, petid);
                intent.putExtra(PET_IMG2, petimg);
                intent.putExtra(PET_TITLE2, pettitle);
                intent.putExtra(PET_TOPIC2, pettopic);
                intent.putExtra(PET_PRICE2, petprice);
                intent.putExtra(PET_CONTENT2, petcontent);
                intent.putExtra(PET_YIMIAO2, petyimiao);
                startActivity(intent);
            }
        });
    }

    //初始化数据
    private void initDatas() {
        petlist = queryAllContent();
        for (BeanPet d : petlist) {
            if (d != null) {
                petid.add(d.getPetid());
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
            int petid = cursor.getInt(cursor.getColumnIndex("id"));
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
