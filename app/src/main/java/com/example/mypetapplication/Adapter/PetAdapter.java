package com.example.mypetapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypetapplication.Bean.BeanPet;
import com.example.mypetapplication.R;

import java.util.List;

import static com.example.mypetapplication.MainActivity.convertStringToIcon;

public class PetAdapter extends ArrayAdapter {
    private Context context;
    private List<BeanPet> objects;
    public PetAdapter(Context context, int id, List<BeanPet> objects) {
        super(context, id,objects);
        this.context = context;
        this.objects = objects;
    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder2 viewHolder = null;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.pets_list,null);
            viewHolder = new PetAdapter.ViewHolder2();
            viewHolder.img = convertView.findViewById(R.id.pets_img);
            viewHolder.title = convertView.findViewById(R.id.pets_title);
            viewHolder.name = convertView.findViewById(R.id.pets_name);
            viewHolder.price = convertView.findViewById(R.id.pets_price);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder2) convertView.getTag();
        }
        BeanPet bean = objects.get(position);
        viewHolder.name.setText(bean.getPettopic());
        viewHolder.title.setText(bean.getPettitle());
        viewHolder.price.setText(bean.getPetprice());
        Bitmap bitmap=convertStringToIcon(bean.getPetimg());
        viewHolder.img.setImageBitmap(bitmap);
        return convertView;
    }
    public static class ViewHolder2{
        ImageView img;
        TextView title;
        TextView name;
        TextView price;
    }
}
