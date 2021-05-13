package com.example.mypetapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypetapplication.Bean.BeanFriend;
import com.example.mypetapplication.R;

import java.util.List;

import static com.example.mypetapplication.MainActivity.convertStringToIcon;

public class FriendAdapter extends ArrayAdapter {
    private Context context;
    private List<BeanFriend> objects;
    public FriendAdapter(Context context, int resource,List<BeanFriend> objects) {
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
        FriendAdapter.ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.social_item,null);
            viewHolder = new FriendAdapter.ViewHolder();
            viewHolder.img = convertView.findViewById(R.id.friend_image);
            viewHolder.name = convertView.findViewById(R.id.friend_name);
            viewHolder.content = convertView.findViewById(R.id.friend_content);
            viewHolder.contentimg = convertView.findViewById(R.id.friend_image2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (FriendAdapter.ViewHolder) convertView.getTag();
        }
        BeanFriend bean = objects.get(position);
        viewHolder.name.setText(bean.getFriendnickname());
        viewHolder.content.setText(bean.getFriendcontent());
        Bitmap bitmap=convertStringToIcon(bean.getFriendimg());
        viewHolder.img.setImageBitmap(bitmap);
        Bitmap bitmap2=convertStringToIcon(bean.getFriendcontentimg());
        viewHolder.contentimg.setImageBitmap(bitmap2);

        return convertView;
    }

    public void onDateChange(List<BeanFriend> list) {
        this.objects = list;
        this.notifyDataSetChanged();
    }

    public static class ViewHolder{
        TextView name;
        ImageView img;
        ImageView contentimg;
        TextView content;
    }
}