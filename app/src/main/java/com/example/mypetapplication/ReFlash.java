package com.example.mypetapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReFlash extends ListView implements AbsListView.OnScrollListener{
    View header;//顶部布局文件
    int headerHeight;//顶部布局文件的高
    int firstVisibleItem;//当前第一个可见item的位置
    boolean isRemark; //标记当前在listview最顶端按下的
    int startY;//按下时的Y的值
    int state;//当前状态
    int scrollState;//获取listview当前滚动状态
    final int NONE=0;//正常状态
    final int PULL=1;//提示下拉刷新状态
    final int RELESE=2;//提示释放状态
    final int REFLASHING =3;//正在刷新的状态
    ReflashListener reflashListener;//刷新数据接口
    public ReFlash(Context context) {
        super(context);
        initView(context);
    }
    public ReFlash(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public ReFlash(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    //初始化界面，添加顶部布局文件
    private void initView(Context context){
        //下拉刷新
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.header, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        topPadding(-headerHeight);
        this.addHeaderView(header);
        //设置滚动监听事件
        this.setOnScrollListener(this);
    }
    //根据当前状态，改变页面提示
    private void reflashViewByState(){
        TextView tip = header.findViewById(R.id.tip);
        ProgressBar progress = header.findViewById(R.id.progess);
        switch (state){
            case NONE:
                topPadding(-headerHeight);
                break;
            case PULL:
                progress.setVisibility(View.GONE);
                tip.setText("下拉刷新");
                break;
            case RELESE:
                progress.setVisibility(View.GONE);
                tip.setText("松开刷新");
                break;
            case REFLASHING:
                topPadding(50);
                progress.setVisibility(View.VISIBLE);
                tip.setText("正在刷新...");
                break;
        }
    }

    //获取完数据
    public void reflashComplete(){
        state = NONE;
        isRemark = false;
        reflashViewByState();
        TextView lasttime = header.findViewById(R.id.lasttime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        lasttime.setText(time);
    }

    public void setInterface(ReflashListener reflashListener){
        this.reflashListener = reflashListener;
    };

    //刷新数据接口
    public interface ReflashListener{
        public void onReflash();
    }

    //下拉刷新
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0){
                    isRemark = true;
                    startY = (int) event.getY();
                }
                Log.d("zyy", String.valueOf(firstVisibleItem));
                break;
            case  MotionEvent.ACTION_MOVE:
                onMove(event);
                break;
            case  MotionEvent.ACTION_UP:
                if (state == RELESE){
                    state = REFLASHING;
                    //加载最新数据
                    reflashViewByState();
                    reflashListener.onReflash();
                } else if (state == PULL){
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    //判断移动过程中的操作
    private void onMove(MotionEvent event){
        if (!isRemark){
            return;
        }
        int tempY = (int) event.getY();//获取当前移动的位置
        int space = tempY - startY;//获取移动的距离
        int topPadding = space - headerHeight;
        switch (state){
            case NONE:
                if (space > 0){
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL){
                    state = RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space < headerHeight + 30 ){
                    state = PULL;
                    reflashViewByState();
                } else if (space <= 0){
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
    }

    //设置header的上边距
    private void topPadding(int topPadding){
        header.setPadding(header.getPaddingLeft(),topPadding, header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    //通知父布局，所占用的宽高;
    private void measureView(View view){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null){
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, params.width);
        int height;
        int tempHeight = params.height;
        if (tempHeight > 0){
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width,height);
    }
}