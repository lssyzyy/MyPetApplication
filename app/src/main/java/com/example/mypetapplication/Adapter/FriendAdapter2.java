package com.example.mypetapplication.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypetapplication.Bean.BeanFriend;
import com.example.mypetapplication.Bean.BeanFriendComment;
import com.example.mypetapplication.Other.TimeUtil;
import com.example.mypetapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mypetapplication.MainActivity.convertStringToIcon;

public class FriendAdapter2 extends BaseExpandableListAdapter {
    private List<BeanFriend> commentBeanList;
    private List<BeanFriendComment> replyBeanList;
    private Context context;
    private Bitmap bitmap2;

    public FriendAdapter2(Context context, List<BeanFriend> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(commentBeanList.get(i).getReplyList() == null){
            return 0;
        }else {
            return commentBeanList.get(i).getReplyList().size()>0 ? commentBeanList.get(i).getReplyList().size():0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i).getReplyList().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.social_item, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        Bitmap bitmap=convertStringToIcon(commentBeanList.get(groupPosition).getFriendimg());
        groupHolder.img.setImageBitmap(bitmap);
        groupHolder.name.setText(commentBeanList.get(groupPosition).getFriendnickname());
        groupHolder.content.setText(commentBeanList.get(groupPosition).getFriendcontent());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(commentBeanList.get(groupPosition).getFrienddate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        groupHolder.time.setText(TimeUtil.getTimeFormatText(date));
        if(commentBeanList.get(groupPosition).getFriendcontentimg()==null){
            groupHolder.contentimg.setVisibility(View.GONE);
        }else{
            bitmap2=convertStringToIcon(commentBeanList.get(groupPosition).getFriendcontentimg());
            groupHolder.contentimg.setImageBitmap(bitmap2);
        }
        groupHolder.contentimg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bigImageLoader(bitmap2);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.friend_comment_item,viewGroup,false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        String replyUser = commentBeanList.get(groupPosition).getReplyList().get(childPosition).getFriendcommentnickname();
        if(!TextUtils.isEmpty(replyUser)){
            childHolder.re_name.setText(replyUser);
        }else {
            childHolder.re_name.setText("无名"+":");
        }

        childHolder.re_content.setText(commentBeanList.get(groupPosition).getReplyList().get(childPosition).getFriendcommentcontent());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder{
        private TextView name, content, time;
        private ImageView img,contentimg;
        public GroupHolder(View view) {
            img = view.findViewById(R.id.friend_image);
            name = view.findViewById(R.id.friend_name);
            content = view.findViewById(R.id.friend_content);
            contentimg = view.findViewById(R.id.friend_image2);
            time = view.findViewById(R.id.time_text);
        }
    }

    private class ChildHolder{
        private TextView re_name, re_content;
        public ChildHolder(View view) {
            re_name = (TextView) view.findViewById(R.id.friend_comment_name);
            re_content = (TextView) view.findViewById(R.id.friend_comment_content);
        }
    }


    public void addTheReplyData(BeanFriendComment replyDetailBean, int groupPosition){
        if(replyDetailBean!=null){
            if(commentBeanList.get(groupPosition).getReplyList() != null ){
                commentBeanList.get(groupPosition).getReplyList().add(replyDetailBean);
            }else {
                List<BeanFriendComment> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentBeanList.get(groupPosition).setReplyList(replyList);
            }
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

    private void bigImageLoader(Bitmap bitmap){
        final Dialog dialog = new Dialog(context);
        ImageView image = new ImageView(context);
        image.setImageBitmap(bitmap);
        dialog.setContentView(image);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.cancel();
            }
        });
    }
}