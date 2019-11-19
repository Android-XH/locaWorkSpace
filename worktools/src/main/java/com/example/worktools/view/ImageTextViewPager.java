package com.example.worktools.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.worktools.R;
import com.example.worktools.adapter.ImageViewAdapter;
import com.example.worktools.handler.ViewPageHandler;
import com.example.worktools.util.LogUtil;


/**
 * 自定义带文字指示的ViewPage
 */
public class ImageTextViewPager extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private TextView tvPosition;
    private TextView tvCount;
    private OnPageChangeListener onPageChangeListener;
    private boolean isScrolling = false; // 滚动框是否滚动着
    private boolean isCycle = false; // 是否循环
    private boolean isWheel = false; // 是否轮播
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private int WHEEL = 100; // 转动
    private int WHEEL_WAIT = 101; // 等待
    private int currentPosition = 0; // 轮播当前位置
    private int size;
    private long time=5000;//默认轮播时间
    private boolean isDestroy;
    private boolean isPause;
    @SuppressLint("HandlerLeak")
    private ViewPageHandler handler= new ViewPageHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(isDestroy||isPause){
                return;
            }
            LogUtil.e("循环》》》》"+msg.what+"====="+getSize());
            if (msg.what == WHEEL && getSize() != 0) {
                if (!isScrolling) {
                    int max = getSize() + 1;
                    int position = (currentPosition + 1) % getSize();
                    viewPager.setCurrentItem(position, true);
                    if (position == max) { // 最后一页时回到第一页
                        viewPager.setCurrentItem(1, false);
                    }
                }
                releaseTime = System.currentTimeMillis();
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, time);
            }else if (msg.what == WHEEL_WAIT && getSize() != 0) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, time);
            }else{
                handler.postDelayed(runnable, time);
            }
        }
    };

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCycle(boolean cycle) {
        isCycle = cycle;
    }

    public void setTime(long time) {
        this.time = time;
    }


    /**
     * 设置是否轮播，默认不轮播,轮播一定是循环的
     *
     * @param isWheel
     */
    public void setWheel(boolean isWheel) {
        this.isWheel = isWheel;
        isCycle = true;
    }
    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable
     */
    public void setScrollable(boolean enable) {
        scrollable = enable;
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    /**
     * 是否处于轮播状态
     *
     * @return
     */
    public boolean isWheel() {
        return isWheel;
    }

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isDestroy&& isWheel) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - releaseTime > time - 500) {
                    handler.sendEmptyMessage(WHEEL);
                } else {
                    handler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    public OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    private boolean scrollable = true;

    public ImageTextViewPager(Context context) {
        super(context);
        initView(context);
    }

    public ImageTextViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ImageTextViewPager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.image_text_view_page, this);
        viewPager=findViewById(R.id.viewPager);
        tvPosition=findViewById(R.id.tv_position);
        tvCount=findViewById(R.id.tv_count);
        viewPager.addOnPageChangeListener(this);
    }
    public void setAdapter(final ImageViewAdapter adapter){
        if(viewPager!=null){
            viewPager.setAdapter(adapter);
        }
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if(tvCount!=null){
                    tvCount.setText(String.valueOf(adapter.getCount()));
                }
                if(tvPosition!=null){
                    tvPosition.setText(String.valueOf((adapter.getCount()>0?1:0)));
                }
                setSize(adapter.getCount());
                if(isWheel()){
                    handler.postDelayed(runnable, time);
                }
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(onPageChangeListener!=null){
            onPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if(onPageChangeListener!=null){
            onPageChangeListener.onPageSelected(position);
        }
        if(tvPosition!=null){
            tvPosition.setText(String.valueOf(position+1));
        }
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(onPageChangeListener!=null){
            onPageChangeListener.onPageScrollStateChanged(state);
        }
        if (state == 1) { // viewPager在滚动
            isScrolling = true;
            return;
        } else if (state == 0) { // viewPager滚动结束
            releaseTime = System.currentTimeMillis();
            viewPager.setCurrentItem(currentPosition, false);

        }
        isScrolling = false;
    }
    interface OnPageChangeListener{
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }

}