package com.example.shop.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shop.R;
import com.example.shop.bean.Product;
import com.example.shop.view.LineTextView;
import com.example.worktools.adapter.ListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends ListAdapter<Product.Data> {
    public ProductAdapter(Activity context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_product_layout;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected View getHolderView(int position, View convertView, Product.Data data) {
        ViewHolder holder= (ViewHolder) convertView.getTag();
        if(holder==null){
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.tvTitle.setText(data.getTitle());
        holder.tvPrice.setText(String.format("%.2f",data.getPrice()));
        holder.tvNewPrice.setText(String.format("%.2f",data.getPrice()-data.getCoupon_amount()));
        holder.tvCouponInfo.setText(data.getDescription());
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.imv_src)
        ImageView imvSrc;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_price)
        LineTextView tvPrice;
        @BindView(R.id.tv_new_price)
        TextView tvNewPrice;
        @BindView(R.id.tv_coupon_info)
        TextView tvCouponInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
