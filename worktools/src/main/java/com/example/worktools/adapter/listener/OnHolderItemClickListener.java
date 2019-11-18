package com.example.worktools.adapter.listener;

import android.view.View;

import com.example.worktools.adapter.holder.RecyclerHolder;

public interface OnHolderItemClickListener {
    void onItemClick(RecyclerHolder holder, View view, int position);
}
