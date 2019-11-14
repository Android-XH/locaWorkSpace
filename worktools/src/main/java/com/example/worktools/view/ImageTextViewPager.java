package com.example.worktools.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.worktools.R;
import com.example.worktools.adapter.ImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 自定义带文字指示的ViewPage
 */
public class ImageTextViewPager extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private TextView tvPosition;
    private TextView tvCount;
    OnPageChangeListener onPageChangeListener;

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
            }
        });
    }

    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable
     */
    public void setScrollable(boolean enable) {
        scrollable = enable;
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
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(onPageChangeListener!=null){
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }
    interface OnPageChangeListener{
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }

}