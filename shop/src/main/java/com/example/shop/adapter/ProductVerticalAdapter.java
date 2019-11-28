package com.example.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.shop.R;
import com.example.shop.bean.Product;
import com.example.worktools.util.StringUtil;
import com.example.shop.view.LineTextView;
import com.example.worktools.adapter.BaseListRecyclerAdapter;
import com.example.worktools.adapter.holder.RecyclerHolder;
import com.example.worktools.util.GlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductVerticalAdapter extends BaseListRecyclerAdapter<Product.Data, ProductVerticalAdapter.ViewHolder> {
    private int viewType;
    public ProductVerticalAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewLayoutId(int viewType) {
        if(viewType==0){
            return R.layout.item_product_vertical_layout;
        }else{
            return R.layout.item_product_layout;
        }

    }

    @Override
    public ViewHolder createViewHolder(View itemView, int viewType) {
        ViewHolder holder=new ViewHolder(itemView);
        return holder;
    }

    public void setViewType(int viewType){
        this.viewType=viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @Override
    public void convert(ViewHolder holder, Product.Data product, int position) {
        GlideUtil.getInstance().loadImg(product.getImg_url(),holder.imvProductImage);
        holder.tvTitle.setText(product.getShort_title());
        if(StringUtil.isNoEmpty(product.getCoupon_info())){
            holder.tvCouponInfo.setVisibility(View.VISIBLE);
            holder.tvCouponInfo.setText(product.getCoupon_info());
        }else{
            holder.tvCouponInfo.setVisibility(View.INVISIBLE);
        }
        holder.tvPrice.setText(String.format(getContext().getString(R.string.priceFormat),product.getPrice()));
        holder.tvNewPrice.setText(String.format(getContext().getString(R.string.priceFormat),product.getPrice()-product.getCoupon_amount()));
        if(getItemViewType(position)==1){
            holder.tvProductDesc.setText(product.getDescription());
        }else{
            holder.tvVolume.setText(String.format("已售：%s",product.getSell_count()));
        }

    }


    static class ViewHolder extends RecyclerHolder {
        @BindView(R.id.imv_product_image)
        ImageView imvProductImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @Nullable
        @BindView(R.id.tv_product_desc)
        TextView tvProductDesc;
        @BindView(R.id.tv_coupon_info)
        TextView tvCouponInfo;
        @BindView(R.id.tv_price)
        LineTextView tvPrice;
        @BindView(R.id.tv_new_price)
        TextView tvNewPrice;
        @Nullable
        @BindView(R.id.tv_volume)
        TextView tvVolume;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
