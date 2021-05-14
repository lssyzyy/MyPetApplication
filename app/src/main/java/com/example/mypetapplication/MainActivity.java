package com.example.mypetapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import com.example.mypetapplication.Adapter.FriendAdapter;
import com.example.mypetapplication.Adapter.PetAdapter;
import com.example.mypetapplication.Bean.BeanFriend;
import com.example.mypetapplication.Bean.BeanPet;
import com.example.mypetapplication.Bean.BeanUserInfo;
import com.example.mypetapplication.dataHelper.MyDatabaseHelper;
import com.example.mypetapplication.dataHelper.MyFrienddataHelper;
import com.example.mypetapplication.dataHelper.MyUserdataHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    private SQLiteDatabase db;
    private DrawerLayout mDrawerLayout;
    private ListView listView;
    private PetAdapter petadapter;
    private SearchView pet_search;
    MyDatabaseHelper helper;
    MyUserdataHelper userhelper;
    MyFrienddataHelper socialhelper;
    private TabHost tabHost;
    TextView nickname,address,sign,nav_headernickname,social_nickname;
    ImageView userphoto,nav_headerimg,social_img;
    String username1;
    private Bitmap bitmap;
    private String nickname3;
    Handler myhandler = new Handler();
    private List<BeanPet> petlist = new ArrayList<>();
    private List<BeanUserInfo> userlist = new ArrayList<>();
    ArrayList<Integer> petid = new ArrayList<Integer>();
    ArrayList<String> petimg = new ArrayList<String>();
    ArrayList<String> pettitle = new ArrayList<String>();
    ArrayList<String> pettopic = new ArrayList<String>();
    ArrayList<String> petprice = new ArrayList<String>();
    ArrayList<String> petcontent = new ArrayList<String>();
    ArrayList<String> petyimiao = new ArrayList<String>();
    ArrayList<String> petusername = new ArrayList<String>();
    ArrayList<String> username = new ArrayList<String>();
    ArrayList<String> userimg = new ArrayList<String>();
    ArrayList<String> usernickname = new ArrayList<String>();
    ArrayList<String> useradress = new ArrayList<String>();
    ArrayList<String> usersign = new ArrayList<String>();
    public static final String PET_ID = "pet_id";
    public static final String PET_IMG = "pet_img";
    public static final String PET_TITLE = "pet_title";
    public static final String PET_TOPIC = "pet_topic";
    public static final String PET_PRICE = "pet_price";
    public static final String PET_CONTENT = "pet_content";
    public static final String PET_YIMIAO = "pet_yimiao";
    public static final String USER_NAME2 = "username";
    public static final String USER_IMG = "user_img";
    public static final String NICK_NAME = "nick_name";
    public static final String ADDRESS = "address";
    public static final String SIGN = "sign";

    private List<BeanFriend> beanFriendList = new ArrayList<>();
    private FriendAdapter adapter;
    ArrayList<Integer> id = new ArrayList<Integer>();
    ArrayList<String> friendname = new ArrayList<String>();
    ArrayList<String> friendnickname = new ArrayList<String>();
    ArrayList<String> friendimg = new ArrayList<String>();
    ArrayList<String> friendcontent = new ArrayList<String>();
    ArrayList<String> friendcontentimg = new ArrayList<String>();
    ArrayList<String> frienddate = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开启Service
        SQLiteStudioService.instance().start(this);
        instance = this;
        //取消严格模式  FileProvider(防止启动相机闪退)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy( builder.build() );
        }

        initTabhost();
        helper = new MyDatabaseHelper(this, "petdata", null, 1);
        db = helper.getWritableDatabase();
        initDatas();

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        username1 = settings.getString("Username", "").toString();;

        FloatingActionButton button_add = findViewById(R.id.pets_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PetListAdd.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_about);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_pet:
                        Intent intent = new Intent(MainActivity.this, PetBaikeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_set:
                        Intent intent2 = new Intent(MainActivity.this, Setting.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_about:
                        break;
                }
                return true;
            }
        });

        //显示宠物列表信息
        petadapter = new PetAdapter(this, R.layout.pets_list, petlist);
        listView = findViewById(R.id.pet_list);
        listView.setAdapter(petadapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeanPet petl = petlist.get(position);
                Intent intent = new Intent(MainActivity.this, PetDetailinfoActivity.class);
                String petid = String.valueOf(petl.getPetid());
                String petimg = petl.getPetimg();
                String pettitle = petl.getPettitle();
                String pettopic = petl.getPettopic();
                String petcontent = petl.getPetcontent();
                String petprice = petl.getPetprice();
                String petyimiao = petl.getPetyimiao();
                intent.putExtra(PET_ID, petid);
                intent.putExtra(PET_IMG, petimg);
                intent.putExtra(PET_TITLE, pettitle);
                intent.putExtra(PET_TOPIC, pettopic);
                intent.putExtra(PET_PRICE, petprice);
                intent.putExtra(PET_CONTENT, petcontent);
                intent.putExtra(PET_YIMIAO, petyimiao);
                startActivity(intent);
            }
        });

        View headerView = navView.getHeaderView(0);
        nav_headerimg=headerView.findViewById(R.id.nav_header_pic);
        nav_headernickname=headerView.findViewById(R.id.header_nickname);
        social_nickname=findViewById(R.id.social_nickname);
        social_img=findViewById(R.id.social_img);
        social_nickname.setTextColor(Color.WHITE);

        //"我的"下的三个选项
        //系统设置
        LinearLayout btn_set = findViewById(R.id.mine_set);
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
            }
        });

        userhelper = new MyUserdataHelper(this, "userdata", null, 1);
        db = userhelper.getWritableDatabase();
        initUserDatas();
        userphoto=findViewById(R.id.mine_img);
        nickname=findViewById(R.id.nickname);
        address=findViewById(R.id.address);
        sign=findViewById(R.id.sign);
        for(int i=0;i<userlist.size();i++){
            if(userlist.get(i).getUsername().equals(username1)){
                userphoto.setImageBitmap(convertStringToIcon(userlist.get(i).getUserimg()));
                nav_headerimg.setImageBitmap(convertStringToIcon(userlist.get(i).getUserimg()));
                social_img.setImageBitmap(convertStringToIcon(userlist.get(i).getUserimg()));
                nickname.setText(userlist.get(i).getNickname());
                nav_headernickname.setText(userlist.get(i).getNickname());
                social_nickname.setText(userlist.get(i).getNickname());
                address.setText(userlist.get(i).getAddress());
                sign.setText(userlist.get(i).getSign());
                break;
            }
        }
        bitmap = ((BitmapDrawable)userphoto.getDrawable()).getBitmap();
        //修改信息
        final String username3 = settings.getString("Username", "").toString();
        LinearLayout btn_edit = findViewById(R.id.mine_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MineEditActivity.class);
                intent.putExtra(USER_NAME2,username3);
                intent.putExtra(USER_IMG,convertIconToString(bitmap));
                intent.putExtra(NICK_NAME,nickname.getText().toString());
                intent.putExtra(ADDRESS,address.getText().toString());
                intent.putExtra(SIGN,sign.getText().toString());
                startActivity(intent);
            }
        });

        //我的发布
        LinearLayout btn_release = findViewById(R.id.mine_publish);
        btn_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PetReleaseActivity.class);
                startActivity(intent);
            }
        });
        //我的领养
        LinearLayout btn_adopt = findViewById(R.id.mine_adopt);
        btn_adopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PetAdoptActivity.class);
                startActivity(intent);
            }
        });


        //搜索功能
        pet_search = findViewById(R.id.pet_search);
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


        //宠圈界面
        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
                int color = Color.argb(0,0,0,0);
                collapsingToolbar.setCollapsedTitleTextColor(color);
                ImageView imageView = findViewById(R.id.social_img);
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) { // 折叠状态
                    collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);
                    imageView.setVisibility(View.GONE);
                } else { // 非折叠状态
                    collapsingToolbar.setTitle("");
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });


        socialhelper = new MyFrienddataHelper(this, "frienddata", null, 1);
        db = socialhelper.getWritableDatabase();
        try {
            initFriends();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ListView social_list = findViewById(R.id.social_list);
        Collections.reverse(beanFriendList);
        adapter = new FriendAdapter(this, R.layout.social_item, beanFriendList);
        social_list.setAdapter(adapter);
        social_list.setTextFilterEnabled(true);
        adapter.notifyDataSetChanged();
        for(int i=0;i<userlist.size();i++){
            if(username3.equals(userlist.get(i).getUsername())){
                nickname3=userlist.get(i).getNickname();
            }
        }



        Button socialadd=findViewById(R.id.social_add);
        socialadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SocialAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
            default:
                break;
        }
        return true;
    }


    //初始化宠物数据
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
                petusername.add(d.getPetusername());
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
            String petusername = cursor.getString(cursor.getColumnIndex("petusername"));
            data = new BeanPet(petid,petimg,pettitle, pettopic, petprice, petcontent,petyimiao,petusername);
            datas.add(data);
        }
        cursor.close();
        return datas;
    }

    //初始化用户数据
    private void initUserDatas() {
        userlist = queryUserAllContent();
        for (BeanUserInfo d : userlist) {
            if (d != null) {
                username.add(d.getUsername());
                userimg.add(d.getUserimg());
                usernickname.add(d.getNickname());
                useradress.add(d.getAddress());
                usersign.add(d.getSign());
            }
        }
    }
    public ArrayList<BeanUserInfo> queryUserAllContent() {
        ArrayList<BeanUserInfo> datas = new ArrayList<>();
        Cursor cursor = db.query("userinfo", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BeanUserInfo data = null;
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String userimg = cursor.getString(cursor.getColumnIndex("userimg"));
            String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String sign = cursor.getString(cursor.getColumnIndex("sign"));
            data = new BeanUserInfo(username,userimg,nickname,address,sign);
            datas.add(data);
        }
        cursor.close();
        return datas;
    }

    //初始化宠圈数据
    private void initFriends() throws ParseException {
        beanFriendList = queryAllsocialContent();
        for (BeanFriend d : beanFriendList) {
            if (d != null) {
                id.add(d.getId());
                friendname.add(d.getFriendname());
                friendnickname.add(d.getFriendnickname());
                friendimg.add(d.getFriendimg());
                friendcontent.add(d.getFriendcontent());
                friendcontentimg.add(d.getFriendcontentimg());
                frienddate.add(d.getFrienddate());
            }
        }
    }
    public ArrayList<BeanFriend> queryAllsocialContent() throws ParseException {
        ArrayList<BeanFriend> datas = new ArrayList<>();
        Cursor cursor = db.query("friend", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BeanFriend data = null;
            int id = cursor.getColumnIndex("id");
            String friendname = cursor.getString(cursor.getColumnIndex("friendname"));
            String friendnickname = cursor.getString(cursor.getColumnIndex("friendnickname"));
            String friendimg = cursor.getString(cursor.getColumnIndex("friendimg"));
            String friendcontent = cursor.getString(cursor.getColumnIndex("friendcontent"));
            String friendcontentimg = cursor.getString(cursor.getColumnIndex("friendcontentimg"));
            String frienddate = cursor.getString(cursor.getColumnIndex("frienddate"));
            data = new BeanFriend(id,friendname,friendnickname,friendimg, friendcontent,friendcontentimg,frienddate);
            datas.add(data);
        }
        cursor.close();
        return datas;
    }
    //初始化宠物列表数据
    private void initTabhost() {
        tabHost = findViewById(R.id.main);
        tabHost.setup();

        final TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("tab1").setIndicator("首页").setContent(R.id.tab01);
        final TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2").setIndicator("宠圈").setContent(R.id.tab02);
        final TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("tab3").setIndicator("我的").setContent(R.id.tab03);

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "tab1":
                        Toast.makeText(MainActivity.this, "首页", Toast.LENGTH_SHORT).show();
                        break;
                    case "tab2":
                        Toast.makeText(MainActivity.this, "宠圈", Toast.LENGTH_SHORT).show();
                        break;
                    case "tab3":
                        Toast.makeText(MainActivity.this, "我的", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    public void logout(View v) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //搜索功能
    private void DataSearch(List<BeanPet> datas, String data) {
        int length = pettitle.size();
        for (int i = 0; i < length; i++) {
            if (pettitle.get(i).contains(data) || pettopic.get(i).contains(data) || petprice.get(i).contains(data)) {
                BeanPet item = new BeanPet();
                item.setPetimg(petimg.get(i));
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
                AlertDialog.Builder build = new AlertDialog.Builder(this);
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
                        }).show();
                break;
            default:
                break;
        }
        return false;
    }
    //bitmap转成string
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        //压缩图片
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        byte[] appicon = baos.toByteArray();// 转为byte数组 return
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }
    //String转成bitmap
    public static Bitmap convertStringToIcon(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public void refresh() {
        onCreate(null);
    }
}
