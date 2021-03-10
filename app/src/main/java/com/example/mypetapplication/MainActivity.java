package com.example.mypetapplication;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DrawerLayout mDrawerLayout;
    private ListView listView;
    private PetAdapter petadapter;
    private SearchView pet_search;
    MyDatabaseHelper helper;
    Handler myhandler=new Handler();
    private List<BeanPet> petlist=new ArrayList<>();
    ArrayList<String> petimg=new ArrayList<String>();
    ArrayList<String> pettitle=new ArrayList<String>();
    ArrayList<String> pettopic=new ArrayList<String>();
    ArrayList<String> petprice=new ArrayList<String>();
    ArrayList<String> petcontent=new ArrayList<String>();
    public static final String PET_IMG = "pet_img";
    public static final String PET_TITLE = "pet_title";
    public static final String PET_TOPIC = "pet_topic";
    public static final String PET_PRICE = "pet_price";
    public static final String PET_CONTENT = "pet_content";
    private RadioButton checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabhost();

        helper=new MyDatabaseHelper(this,"petdata",null,1);
        db=helper.getWritableDatabase();
        initDatas();

        FloatingActionButton button_add=findViewById(R.id.pets_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PetListAdd.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navView=findViewById(R.id.nav_view);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_about);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_pet:
                        Intent intent=new Intent(MainActivity.this,PetBaikeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_set:
                        Intent intent2=new Intent(MainActivity.this,Setting.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_about:
                        break;
                }
                return true;
            }
        });

        //显示宠物列表信息
        petadapter=new PetAdapter(this,R.layout.pets_list,petlist);
        listView = findViewById(R.id.pet_list);
        listView.setAdapter(petadapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeanPet petl = petlist.get(position);
                Toast.makeText(MainActivity.this,"选择"+ petl.getPettitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, PetDetailinfoActivity.class);
                String petimg = petl.getPetimg();
                String pettitle = petl.getPettitle();
                String pettopic = petl.getPettopic();
                String petcontent = petl.getPetcontent();
                String petprice = petl.getPetprice();
                intent.putExtra(PET_IMG, petimg);
                intent.putExtra(PET_TITLE, pettitle);
                intent.putExtra(PET_TOPIC, pettopic);
                intent.putExtra(PET_PRICE, petprice);
                intent.putExtra(PET_CONTENT, petcontent);
                startActivity(intent);
            }
        });


        LinearLayout btn_set=findViewById(R.id.mine_set);
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Setting.class);
                startActivity(intent);
            }
        });

        //搜索功能
        pet_search=findViewById(R.id.pet_search);
        pet_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                myhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String data = newText;
                        petlist.clear();
                        DataSearch(petlist, data);
                        petadapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
            default:
                break;
        }
        return true;
    }


    //初始化数据
    private void initDatas() {
        petlist=queryAllContent();
        for(BeanPet d:petlist){
            if(d!=null){
                pettitle.add(d.getPettitle());
                pettopic.add(d.getPettopic());
                petprice.add(d.getPetprice());
                petcontent.add(d.getPetcontent());
            }
        }
    }
    public ArrayList<BeanPet> queryAllContent(){
        ArrayList<BeanPet> datas=new ArrayList<>();
        Cursor cursor=db.query("petsdb",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            BeanPet data=null;
            String pettitle = cursor.getString(cursor.getColumnIndex("pettitle"));
            String pettopic = cursor.getString(cursor.getColumnIndex("pettopic"));
            String petprice = cursor.getString(cursor.getColumnIndex("petprice"));
            String petcontent = cursor.getString(cursor.getColumnIndex("petcontent"));
            data=new BeanPet(pettitle,pettopic,petprice,petcontent);
            datas.add(data);
        }
        cursor.close();
        return datas;
    }

    private  void  initTabhost(){
        final TabHost tabHost = findViewById(R.id.main);
        tabHost.setup();

        final TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("tab1").setIndicator("首页").setContent(R.id.tab01);
        final TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2").setIndicator("我的").setContent(R.id.tab02);

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case "tab1":
                        Toast.makeText(MainActivity.this,"首页",Toast.LENGTH_SHORT).show();
                        break;
                    case "tab2":
                        Toast.makeText(MainActivity.this,"我的",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    public void logout(View v){
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    //搜索功能
    private void DataSearch(List<BeanPet> datas,String data){
        int length=pettitle.size();
        for(int i=0;i<length;i++){
            if(pettitle.get(i).contains(data)||pettopic.get(i).contains(data)||petprice.get(i).contains(data)){
                BeanPet item=new BeanPet();
                item.setPettitle(pettitle.get(i));
                item.setPettopic(pettopic.get(i));
                item.setPetprice(petprice.get(i));
                datas.add(item);
            }
        }
    }

    //弹出退出对话框
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder build=new AlertDialog.Builder(this);
                build.setTitle("注意")
                        .setMessage("确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                android.os.Process.killProcess(android.os.Process.myPid());  //获取PID
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
        return false;
    }
}