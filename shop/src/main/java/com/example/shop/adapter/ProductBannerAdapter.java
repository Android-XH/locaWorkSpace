package com.example.shop.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.bean.Product;
import com.example.worktools.adapter.ImageViewAdapter;
import com.example.worktools.util.GlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductBannerAdapter extends ImageViewAdapter<Product.Data> {

    public ProductBannerAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getLayout() {
        return R.layout.item_product_banner_view;
    }

    @Override
    protected View getContentView(int position, View view, Product.Data data) {
        ViewHolder holder= (ViewHolder) view.getTag();
        if(holder==null){
            holder=new ViewHolder(view);
        }
        GlideUtil.getInstance().loadImg(data.getImg_url(),holder.imvProductDetail);
        holder.tvTitle.setText(data.getShort_title()==null?data.getTitle():data.getShort_title());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.imv_product_detail)
        ImageView imvProductDetail;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
