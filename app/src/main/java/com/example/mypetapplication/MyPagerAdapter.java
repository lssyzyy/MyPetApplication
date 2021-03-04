package com.example.mypetapplication;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

class MyPagerAdapter extends PagerAdapter {
    private ArrayList<View> viewLists;

    public MyPagerAdapter(ArrayList<View> viewLists) {
        this.viewLists = viewLists;
    }


    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int index=position%viewLists.size();
        View pageView=viewLists.get(index);
        ViewGroup parent = (ViewGroup) pageView.getParent();
        if(parent!=null){
            parent.removeAllViews();
        }
        container.addView(pageView);
        return pageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position%viewLists.size()));
    }
}