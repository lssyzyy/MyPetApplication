package com.example.mypetapplication;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypetapplication.Adapter.PetAdapter;
import com.example.mypetapplication.Bean.BeanPet;
import com.example.mypetapplication.dataHelper.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class PetAdoptActivity extends AppCompatActivity {
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
    ArrayList<String> petusername = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt);

        helper = new MyDatabaseHelper(this, "petdata", null, 1);
        db = helper.getWritableDatabase();
        initDatas();
        Button btn2 = findViewById(R.id.adopt_back);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetAdoptActivity.this.finish();
            }
        });
        //显示宠物列表信息
        petadapter = new PetAdapter(this, R.layout.pets_list, petlist);
        listView = findViewById(R.id.adoptlist);
        listView.setAdapter(petadapter);
        listView.setTextFilterEnabled(true);
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
                petusername.add(d.getPetusername());
            }
        }
    }
    public ArrayList<BeanPet> queryAllContent() {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String petusername1 = settings.getString("Username", "").toString();
        ArrayList<BeanPet> datas = new ArrayList<>();
        Cursor cursor = db.query("petsadopt", null, "petusername=?", new String[]{petusername1}, null, null, null);
        while (cursor.moveToNext()) {
            BeanPet data = null;
            int petid = cursor.getColumnIndex("id");
            String petimg = cursor.getString(cursor.getColumnIndex("petimg"));
            String pettitle = cursor.getString(cursor.getColumnIndex("pettitle"));
            String pettopic = cursor.getString(cursor.getColumnIndex("pettopic"));
            String petprice = cursor.getString(cursor.getColumnIndex("petprice"));
            String petcontent = cursor.getString(cursor.getColumnIndex("petcontent"));
            String petyimiao = cursor.getString(cursor.getColumnIndex("petyimiao"));
            String petusername = cursor.getString(cursor.getColumnIndex("petusername"));
            data = new BeanPet(petid,petimg,pettitle, pettopic, petprice, petcontent,petyimiao,petusername);
            datas.add(data);
        }
        cursor.close();
        return datas;
    }
}
