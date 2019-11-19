package com.example.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.bean.Search;
import com.example.worktools.adapter.TagAdapter;
import com.example.worktools.view.flowlayout.FlowLayout;

import java.util.List;

public class SearchTagAdapter extends TagAdapter<Search.Data> {
    private LayoutInflater inflate;
    public SearchTagAdapter(Context context){
        inflate=LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, Search.Data data) {
        TextView tv = (TextView) inflate.inflate(R.layout.item_tag_layout,parent, false);
        tv.setText(data.getKeyword());
        return tv;
    }
}
