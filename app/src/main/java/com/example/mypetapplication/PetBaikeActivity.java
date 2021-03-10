package com.example.mypetapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PetBaikeActivity extends AppCompatActivity {

    private  final int SHOW_PETDATA = 0;
    private PetBaikeAdapter adapter;
    private PetBaikeDetailAdapter adapterbaike;
    private List<BeanPetBaike> list=new ArrayList<>();
    private List<BeanPetBaikeDetail> list2=new ArrayList<>();
    private ListView reFlashListView;
    private Button pet_baike_dog,pet_baike_cat,pet_baike_paxing,pet_baike_little,pet_baike_watter;
    private SearchView pet_baike_search;
    String type="0";
    public static final String PET_IMG = "pet_baike_detail_img";
    public static final String PET_NAME = "pet_baike_detail_name";
    public static final String PET_ENGNAME = "pet_baike_detail_engname";
    public static final String PET_CHARACTERS = "pet_baike_detail_characters";
    public static final String PET_NATION = "pet_baike_detail_nation";
    public static final String PET_EASYOFDISEASE = "pet_baike_detail_easyOfDisease";
    public static final String PET_LIFE = "pet_baike_detail_life";
    public static final String PET_PRICE = "pet_baike_detail_price";
    public static final String PET_FEATURE = "pet_baike_detail_feature";
    public static final String PET_CHARACTERFEATURE = "pet_baike_detail_characterFeature";
    public static final String PET_CAREKNOWLEDGE = "pet_baike_detail_careKnowledge";
    public static final String PET_FEEDPOINTS = "pet_baike_detail_feedPoints";
    ArrayList<String> petbaikename=new ArrayList<String>();
    ArrayList<String> petbaikeengname=new ArrayList<String>();
    ArrayList<String> petbaikeimg=new ArrayList<String>();
    ArrayList<String> petbaikeprice=new ArrayList<String>();
    Handler myhandler=new Handler();
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case  SHOW_PETDATA:
                    showList();
                    showList2();
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
                    Request request = new Request.Builder().url("http://api.tianapi.com/txapi/pet/index?key=3c66023c430ded049c9a35ec27aeaa56&type="+type+"&page=1&num=15").build();
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
        Button btn2 = findViewById(R.id.petbaike_back);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetBaikeActivity.this.finish();
            }
        });
        adapter=new PetBaikeAdapter(this,R.layout.activity_pet_baike_item,list);
        reFlashListView = findViewById(R.id.listv);
        reFlashListView.setAdapter(adapter);
        reFlashListView.setTextFilterEnabled(true);
        reFlashListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BeanPetBaikeDetail petl = list2.get(i);
                Toast.makeText(PetBaikeActivity.this,"选择"+petl.getName()+petl.getEngname(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PetBaikeActivity.this, PetBaikeDetailinfoActivity.class);
                String pet_img = petl.getImg();
                String pet_name = petl.getName();
                String pet_engname = petl.getEngname();
                String pet_characters = petl.getCharacters();
                String pet_nation = petl.getNation();
                String pet_easyOfDisease = petl.getEasyOfDisease();
                String pet_life = petl.getLife();
                String pet_price = petl.getPrice();
                String pet_feature = petl.getFeature();
                String pet_characterFeature = petl.getCharacterFeature();
                String pet_careKnowledge = petl.getCareKnowledge();
                String pet_feedPoints = petl.getFeedPoints();
                intent.putExtra(PET_IMG, pet_img);
                intent.putExtra(PET_NAME, pet_name);
                intent.putExtra(PET_ENGNAME, pet_engname);
                intent.putExtra(PET_CHARACTERS, pet_characters);
                intent.putExtra(PET_NATION, pet_nation);
                intent.putExtra(PET_EASYOFDISEASE, pet_easyOfDisease);
                intent.putExtra(PET_LIFE, pet_life);
                intent.putExtra(PET_PRICE, pet_price);
                intent.putExtra(PET_FEATURE, pet_feature);
                intent.putExtra(PET_CHARACTERFEATURE, pet_characterFeature);
                intent.putExtra(PET_CAREKNOWLEDGE, pet_careKnowledge);
                intent.putExtra(PET_FEEDPOINTS, pet_feedPoints);
                startActivity(intent);
            }
        });
        //下拉菜单
        spinner=findViewById(R.id.baikespinner);
        data_list = new ArrayList<String>();
        data_list.add("猫科");
        data_list.add("犬科");
        data_list.add("爬行类");
        data_list.add("小宠物类");
        data_list.add("水族类");
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                cleanlist();
                type=String.valueOf(arg2);
                loadPetData();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        //搜索功能
        pet_baike_search=findViewById(R.id.pet_baike_search);
        pet_baike_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                        list.clear();
                        PetSearch(list, data);
                        adapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });
    }

    private void showList() {
        if (adapter==null){
            adapter = new PetBaikeAdapter(PetBaikeActivity.this,0,list);

            reFlashListView.setAdapter(adapter);
        }else {
            adapter.onDateChange(list);
        }
    }
    private void showList2() {
        if (adapterbaike==null){
            adapterbaike = new PetBaikeDetailAdapter(PetBaikeActivity.this,0,list2);

        }else {
            adapterbaike.onDateChange(list2);
        }
    }
    private void getjson(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("newslist");

            for(int i = 0; i<jsonArray.length();i++){
                JSONObject jsonObject3 = (JSONObject) jsonArray.get(i);
                String pic = jsonObject3.getString("coverURL");
                String name = jsonObject3.getString("name");
                String engname = jsonObject3.getString("engName");
                String characters = jsonObject3.getString("characters");
                String nation = jsonObject3.getString("nation");
                String easyOfDisease = jsonObject3.getString("easyOfDisease");
                String life = jsonObject3.getString("life");
                String price = jsonObject3.getString("price");
                String feature = jsonObject3.getString("feature");
                String characterFeature = jsonObject3.getString("characterFeature");
                String careKnowledge = jsonObject3.getString("careKnowledge");
                String feedPoints = jsonObject3.getString("feedPoints");
                BeanPetBaike bean = new BeanPetBaike(pic,name,engname,price);
                BeanPetBaikeDetail bean2 = new BeanPetBaikeDetail(pic,name,engname,characters,nation,easyOfDisease,life,price,feature,characterFeature,careKnowledge,feedPoints);
                list.add(bean);
                list2.add(bean2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //清除处理
    private void cleanlist(){
        int size=list.size();
        int size2=list2.size();
        if(size>0){
            System.out.println(size);
            list.removeAll(list);
            adapter.notifyDataSetChanged();
            reFlashListView.setAdapter(adapter);
        }
        if(size2>0){
            System.out.println(size);
            list2.removeAll(list2);
            adapterbaike.notifyDataSetChanged();
        }
    }
    //搜索功能
    private void PetSearch(List<BeanPetBaike> datas,String data){
        int length=petbaikename.size();
        for(int i=0;i<length;i++){
            if(petbaikename.get(i).contains(data)||petbaikeengname.get(i).contains(data)){
                BeanPetBaike item=new BeanPetBaike();
                item.setName(petbaikename.get(i));
                item.setEngname(petbaikeengname.get(i));
                item.setImageView(petbaikeimg.get(i));
                item.setPrice(petbaikeprice.get(i));
                datas.add(item);
            }
        }
    }
}