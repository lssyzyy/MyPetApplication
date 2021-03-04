package com.example.mypetapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class PetBaikeAdapter extends ArrayAdapter {
    private Context context;
    private List<BeanPetBaike> objects;
    public PetBaikeAdapter(Context context, int resource,List<BeanPetBaike> objects) {
        super(context, resource, objects);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_pet_baike_item,null);
            viewHolder = new ViewHolder();
            viewHolder.img = convertView.findViewById(R.id.imgv);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.engname = convertView.findViewById(R.id.engname);
            viewHolder.price = convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BeanPetBaike bean = objects.get(position);
        viewHolder.name.setText(bean.getName());
        viewHolder.engname.setText(bean.getEngname());
        viewHolder.price.setText(bean.getPrice());
        Glide.with(context)
                .load(objects.get(position).getImageView())
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(viewHolder.img);

        return convertView;
    }

    public void onDateChange(List<BeanPetBaike> list) {
        this.objects = list;
        this.notifyDataSetChanged();
    }

    public static class ViewHolder{
        TextView name;
        ImageView img;
        TextView engname;
        TextView price;
    }
}
