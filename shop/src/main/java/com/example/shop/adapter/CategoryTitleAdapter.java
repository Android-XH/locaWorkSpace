package com.example.shop.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.bean.Category;
import com.example.shop.bean.MCategory;
import com.example.worktools.adapter.ListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryTitleAdapter extends ListAdapter<MCategory.Data> {
    public CategoryTitleAdapter(Activity context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_category_layout;
    }

    @Override
    protected View getHolderView(int position, View convertView, MCategory.Data data) {
        ViewHolder holder= (ViewHolder) convertView.getTag();
        if(holder==null){
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.tvTitle.setText(data.getName());
        holder.tvTitle.setSelected(data.isSelect());
        return convertView;
    }

    public void setSelectItem(int position){
        for(MCategory.Data data:getList()){
            data.setSelect(false);
        }
        getItem(position).setSelect(true);
        this.notifyDataSetChanged();
    }
    static
    class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
