package com.example.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shop.R;
import com.example.shop.bean.CategoryItem;
import com.example.worktools.adapter.BaseListRecyclerAdapter;
import com.example.worktools.adapter.holder.RecyclerHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryItemAdapter extends BaseListRecyclerAdapter<CategoryItem.Data, CategoryItemAdapter.ViewHolder> {
    public CategoryItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewLayoutId(int viewType) {
        return R.layout.item_category_item_name_layout;
    }

    @Override
    public ViewHolder createViewHolder(View itemView, int viewType) {
        ViewHolder holder=new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void convert(CategoryItemAdapter.ViewHolder holder, CategoryItem.Data data, int position) {
        holder.tvName.setText(data.getCategory_name());
    }

    static
    class ViewHolder extends RecyclerHolder{
        @BindView(R.id.imv_icon)
        ImageView imvIcon;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
