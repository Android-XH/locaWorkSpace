package com.example.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;
import com.example.shop.bean.Product;
import com.example.shop.view.LineTextView;
import com.example.worktools.adapter.BaseListRecyclerAdapter;
import com.example.worktools.adapter.holder.RecyclerHolder;
import com.example.worktools.util.GlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductVerticalAdapter extends BaseListRecyclerAdapter<Product.Data, ProductVerticalAdapter.ViewHolder> {
    public ProductVerticalAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewLayoutId(int viewType) {
        return R.layout.item_product_vertical_layout;
    }

    @Override
    public ViewHolder createViewHolder(View itemView, int viewType) {
        ViewHolder holder=new ViewHolder(itemView);

        return holder;
    }


    @Override
    public void convert(ViewHolder holder, Product.Data product, int position) {
        GlideUtil.getInstance().loadImg(product.getImg_url(),holder.imvProductImage);
        holder.tvTitle.setText(product.getShop_title());
        holder.tvCouponInfo.setText(product.getCoupon_info());
        holder.tvPrice.setText(String.format(getContext().getString(R.string.priceFormat),product.getPrice()));
        holder.tvNewPrice.setText(String.format(getContext().getString(R.string.priceFormat),product.getPrice()-product.getCoupon_amount()));
    }


    static
    class ViewHolder extends RecyclerHolder {
        @BindView(R.id.imv_product_image)
        ImageView imvProductImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_coupon_info)
        TextView tvCouponInfo;
        @BindView(R.id.tv_price)
        LineTextView tvPrice;
        @BindView(R.id.tv_new_price)
        TextView tvNewPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
