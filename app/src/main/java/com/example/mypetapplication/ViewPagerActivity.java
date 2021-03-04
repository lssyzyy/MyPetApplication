package com.example.mypetapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;


public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager myPagerAdapter = null;
    private MyPagerAdapter mAdapter = null;
    private ArrayList<View> viewArrayList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        myPagerAdapter=findViewById(R.id.in_viewpager);

        viewArrayList = new ArrayList<View>();
        LayoutInflater layoutInflater = LayoutInflater.from(ViewPagerActivity.this);
        viewArrayList.add(layoutInflater.inflate(R.layout.page1, null, false));
        viewArrayList.add(layoutInflater.inflate(R.layout.page2, null, false));
        viewArrayList.add(layoutInflater.inflate(R.layout.page3, null, false));
        mAdapter = new MyPagerAdapter(viewArrayList);
        myPagerAdapter.setAdapter(mAdapter);

    }

    public void in(View v){
        Intent intent=new Intent(ViewPagerActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        Toast.makeText(this, "第" + (i + 1) + "页", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}