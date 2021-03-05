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

public class PetBaikeDetailAdapter extends ArrayAdapter {
    private Context context;
    private List<BeanPetBaikeDetail> objects;
    public PetBaikeDetailAdapter(Context context, int resource, List<BeanPetBaikeDetail> objects) {
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
            convertView = inflater.inflate(R.layout.activity_pet_baike_detail_info,null);
            viewHolder = new ViewHolder();
            viewHolder.img = convertView.findViewById(R.id.pet_baike_detail_img);
            viewHolder.name = convertView.findViewById(R.id.pet_baike_detail_name);
            viewHolder.engname = convertView.findViewById(R.id.pet_baike_detail_engname);
            viewHolder.characters = convertView.findViewById(R.id.pet_baike_detail_characters);
            viewHolder.nation = convertView.findViewById(R.id.pet_baike_detail_nation);
            viewHolder.easyOfDisease = convertView.findViewById(R.id.pet_baike_detail_easyOfDisease);
            viewHolder.life = convertView.findViewById(R.id.pet_baike_detail_life);
            viewHolder.price = convertView.findViewById(R.id.pet_baike_detail_price);
            viewHolder.feature = convertView.findViewById(R.id.pet_baike_detail_feature);
            viewHolder.characterFeature = convertView.findViewById(R.id.pet_baike_detail_characterFeature);
            viewHolder.careKnowledge = convertView.findViewById(R.id.pet_baike_detail_careKnowledge);
            viewHolder.feedPoints = convertView.findViewById(R.id.pet_baike_detail_feedPoints);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BeanPetBaikeDetail bean = objects.get(position);
        viewHolder.name.setText(bean.getName());
        viewHolder.engname.setText(bean.getEngname());
        viewHolder.characters.setText(bean.getCharacters());
        viewHolder.nation.setText(bean.getNation());
        viewHolder.easyOfDisease.setText(bean.getEasyOfDisease());
        viewHolder.life.setText(bean.getLife());
        viewHolder.price.setText(bean.getPrice());
        viewHolder.feature.setText(bean.getFeature());
        viewHolder.characterFeature.setText(bean.getCharacterFeature());
        viewHolder.careKnowledge.setText(bean.getCareKnowledge());
        viewHolder.feedPoints.setText(bean.getFeedPoints());
        Glide.with(context)
                .load(objects.get(position).getImg())
                .placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(viewHolder.img);

        return convertView;
    }

    public void onDateChange(List<BeanPetBaikeDetail> list) {
        this.objects = list;
        this.notifyDataSetChanged();
    }

    public static class ViewHolder{
        ImageView img;
        TextView name;
        TextView engname;
        TextView characters;
        TextView nation;
        TextView easyOfDisease;
        TextView life;
        TextView price;
        TextView feature;
        TextView characterFeature;
        TextView careKnowledge;
        TextView feedPoints;

    }
}
