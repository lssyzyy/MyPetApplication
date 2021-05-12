package com.example.mypetapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mypetapplication.Bean.BeanFriend;
import com.example.mypetapplication.R;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private Context mContext;
    private List<BeanFriend> mBeanFriendList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image1;
        TextView name;
        TextView content;
        ImageView image2;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.friend_name);
            content = (TextView) view.findViewById(R.id.friend_content);
            image1 = (ImageView) view.findViewById(R.id.friend_image);
            image2 = (ImageView) view.findViewById(R.id.friend_image2);
        }
    }
    public FriendAdapter(List<BeanFriend> beanFriendList) {
        mBeanFriendList = beanFriendList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.social_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        BeanFriend bean = mBeanFriendList.get(position);
        holder.name.setText(bean.getFriendname());
        holder.content.setText(bean.getFriendcontent());
        holder.image2.setImageResource(bean.getFriendimg());
        holder.image1.setImageResource(bean.getFriendimg());
    }
    @Override
    public int getItemCount() {
        return mBeanFriendList.size();
    }
}