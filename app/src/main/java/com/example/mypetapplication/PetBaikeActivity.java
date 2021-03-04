package com.example.mypetapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PetBaikeActivity extends AppCompatActivity implements ReFlash.ReflashListener {

    private  final int SHOW_PETDATA = 0;
    private PetBaikeAdapter adpter;
    private List<BeanPetBaike> list;
    private ReFlash reFlashListView;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case  SHOW_PETDATA:
                    showList();
                    break;
                default:
                    break;
            }
        }
    };
    private void loadPetData(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://api.tianapi.com/txapi/pet/index?key=3c66023c430ded049c9a35ec27aeaa56&type=1&page=1&num=14").build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    getjson(responseData);

                    Message msg = new Message();
                    msg.what = SHOW_PETDATA;
                    msg.obj = responseData;
                    mHandler.sendMessage(msg);


                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void find(){
        reFlashListView = findViewById(R.id.listv);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_baike);

        find();
        loadPetData();
        Toast.makeText(PetBaikeActivity.this,"宠物百科",Toast.LENGTH_SHORT).show();
        final ProgressDialog wait = new ProgressDialog(PetBaikeActivity.this);
        wait.setMessage("加载中....");
        wait.setIndeterminate(true);
        wait.setCancelable(true);
        wait.show();
        wait.setCanceledOnTouchOutside(false);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wait.dismiss();
            }
        },1000);

        Button btn2 = findViewById(R.id.petbaike_back);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetBaikeActivity.this.finish();
            }
        });
    }

    @Override
    public void onReflash() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadPetData();
                reFlashListView.reflashComplete();
            }
        },2000);
    }
    private void showList() {
        if (adpter==null){
            adpter = new PetBaikeAdapter(PetBaikeActivity.this,0,list);

            reFlashListView.setInterface(PetBaikeActivity.this);
            reFlashListView.setAdapter(adpter);
        }else {
            adpter.onDateChange(list);
        }
    }
    private void getjson(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("newslist");
            list = new ArrayList<BeanPetBaike>();

            for(int i = 0; i<jsonArray.length();i++){
                JSONObject jsonObject3 = (JSONObject) jsonArray.get(i);
                String pic = jsonObject3.getString("coverURL");
                String name = jsonObject3.getString("name");
                String engname = jsonObject3.getString("engName");
                String price = jsonObject3.getString("price");
                BeanPetBaike bean = new BeanPetBaike(pic,name,engname,price);
                list.add(bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}