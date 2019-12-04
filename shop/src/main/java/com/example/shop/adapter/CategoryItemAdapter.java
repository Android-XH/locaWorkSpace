package com.example.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shop.R;
import com.example.shop.bean.CategoryItem;
import com.example.worktools.adapter.BaseListRecyclerAdapter;
import com.example.worktools.adapter.holder.RecyclerHolder;
import com.example.worktools.util.GlideUtil;

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
//        GlideUtil.getInstance().loadImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575282259181&di=1bdb36548d2ea5a3e007e2356bcaab67&imgtype=0&src=http%3A%2F%2Fimg.jianke.com%2Fupload%2Fprodimage%2F201704%2F201741910169810.jpg",holder.imvIcon);
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
