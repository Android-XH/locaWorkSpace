package com.example.worktools.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.worktools.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class ImageViewAdapter<T> extends PagerAdapter {
    private List<T> data;
    private List<View>viewList;
    private Context mContext;
    private onItemClickListener<T> onItemClickListener;

    public ImageViewAdapter.onItemClickListener<T> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(ImageViewAdapter.onItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ImageViewAdapter(Activity activity) {
        this.mContext=activity;
    }
    public void setData(List<T>data){
        this.data=data;
        if(viewList==null){
            viewList=new ArrayList<>(data.size());
        }else{
            viewList.clear();
        }
        for(int i=0;i<getCount();i++){
            viewList.add(null);
        }
        notifyDataSetChanged();
    }
    public abstract int getLayout();
    protected abstract View getContentView(int position, View view, T data);
    @NonNull
    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        View view;
        if(viewList.get(position)==null){
            view= LayoutInflater.from(mContext).inflate(getLayout(),null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getOnItemClickListener()!=null){
                        getOnItemClickListener().onItemClick(data.get(position));
                    }
                }
            });
            viewList.add(position,view);
        }else{
            view=viewList.get(position);
        }
        container.addView(view);
        return getContentView(position,view,data.get(position));
    }
    @Override
    public void destroyItem(ViewGroup container, int position,  @NonNull Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    public interface onItemClickListener<T>{
        void onItemClick(T t);
    }
}