package com.example.shop.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.example.shop.R;
import com.example.worktools.adapter.ImageViewAdapter;
import com.example.worktools.util.GlideUtil;
import com.example.worktools.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductImageAdapter extends ImageViewAdapter<String> {

    public ProductImageAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public int getLayout() {
        return R.layout.item_product_image_view;
    }

    @Override
    protected View getContentView(int position, View view, String s) {
        ViewHolder holder= (ViewHolder) view.getTag();
        if(holder==null){
            holder=new ViewHolder(view);
        }
        GlideUtil.getInstance().loadImg(s,holder.imvProductDetail);
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.imv_product_detail)
        ImageView imvProductDetail;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
