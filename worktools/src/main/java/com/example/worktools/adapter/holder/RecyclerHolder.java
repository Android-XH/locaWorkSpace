package com.example.worktools.adapter.holder;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.worktools.adapter.listener.OnHolderItemClickListener;


public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_VIEW_INSTANCE = 20;

    private final SparseArray<View> mViews = new SparseArray<>(MAX_VIEW_INSTANCE);
    private OnHolderItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnHolderItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    public <T extends View> T getView(int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            mViews.put(resId, view);
        }
        return (T) view;
    }

    public void setText(int resId, String text) {
        TextView textView = getView(resId);
        textView.setText(text);
    }

    public void setText(int resId, int txtResId) {
        TextView textView = getView(resId);
        textView.setText(txtResId);
    }

    public void setImageResources(int resId, int imgResId) {
        ImageView imageView = getView(resId);
        imageView.setImageResource(imgResId);
    }

    public void setImageBitmap(int resId, Bitmap bitmap) {
        ImageView imageView = getView(resId);
        imageView.setImageBitmap(bitmap);
    }

    public void setBackgroundResources(int resId, int bgResId) {
        getView(resId).setBackgroundResource(bgResId);
    }

    public void setVisibility(int resId, int visibility) {
        getView(resId).setVisibility(visibility);
    }

    public void setViewVisible(int resId) {
        getView(resId).setVisibility(View.VISIBLE);
    }

    public void setViewInVisible(int resId) {
        getView(resId).setVisibility(View.INVISIBLE);
    }

    public void setViewGone(int resId) {
        getView(resId).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(this, view, getAdapterPosition());
        }
    }
}
