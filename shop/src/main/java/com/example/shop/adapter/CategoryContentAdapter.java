package com.example.shop.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;
import com.example.shop.bean.Category;
import com.example.shop.bean.CategoryItem;
import com.example.worktools.adapter.ListAdapter;
import com.example.worktools.adapter.listener.OnRecycleItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryContentAdapter extends ListAdapter<Category.Data> {
    private onItemClickListener onItemClickListener;
    public CategoryContentAdapter(Activity context) {
        super(context);
    }

    public CategoryContentAdapter.onItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(CategoryContentAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected int getLayout() {
        return R.layout.item_category_item_layout;
    }

    @Override
    protected View getHolderView(int position, View convertView, Category.Data data) {
        ViewHolder holder= (ViewHolder) convertView.getTag();
        if(holder==null){
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.recycleView.setLayoutManager( new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        holder.tvCategoryTitle.setText(data.getName());
        CategoryItemAdapter adapter=new CategoryItemAdapter(getContext());
        holder.recycleView.setAdapter(adapter);
        adapter.setDataList(data.getCategoryItems());
        adapter.setOnRecycleItemClickListener(new OnRecycleItemClickListener<CategoryItem.Data>() {
            @Override
            public void onItemClick(CategoryItem.Data data, int position) {
                if(getOnItemClickListener()!=null){
                    getOnItemClickListener().onItemClick(data);
                }
            }
        });
        return convertView;
    }
    static
    class ViewHolder {
        @BindView(R.id.tv_category_title)
        TextView tvCategoryTitle;
        @BindView(R.id.recycleView)
        RecyclerView recycleView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public interface onItemClickListener{
        void onItemClick(CategoryItem.Data data);
    }
}
