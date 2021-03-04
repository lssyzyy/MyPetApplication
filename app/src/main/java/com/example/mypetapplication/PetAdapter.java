package com.example.mypetapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PetAdapter extends ArrayAdapter<BeanPet> {
    private int resourceid;
    public PetAdapter(Context context, int id, List<BeanPet> objects) {
        super(context, id,objects);
        resourceid=id;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        BeanPet data=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceid,null);
        }
        else{
            view=convertView;
        }
        TextView pettitle=view.findViewById(R.id.pets_title);
        TextView pettopic=view.findViewById(R.id.pets_name);
        TextView petprice=view.findViewById(R.id.pets_price);
        pettitle.setText(data.getPettitle());
        pettopic.setText(data.getPettopic());
        petprice.setText(data.getPetprice());
        return view;
    }
}
