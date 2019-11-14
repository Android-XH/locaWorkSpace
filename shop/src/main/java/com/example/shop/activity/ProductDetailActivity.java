package com.example.shop.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.ProductImageAdapter;
import com.example.shop.bean.Product;
import com.example.shop.common.IntentKey;
import com.example.shop.presenter.ProductDetailPresenter;
import com.example.shop.viewImpl.IProductDetailView;
import com.example.worktools.util.DpUtil;
import com.example.worktools.util.LogUtil;
import com.example.worktools.view.ImageTextViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppBaseActivity<ProductDetailPresenter> implements IProductDetailView {
    @BindView(R.id.img_text_view_page)
    ImageTextViewPager imgTextViewPage;
    private Product.Data data;
    private ProductImageAdapter adapter;
    @Override
    protected int setContentView() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initAppData(Bundle bundle){
        data = bundle.getParcelable(IntentKey.PRODUCT_DATA);
        adapter=new ProductImageAdapter(this);
        imgTextViewPage.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        showBackImb();
        ViewGroup.LayoutParams layoutParams=imgTextViewPage.getLayoutParams();
        int width=DpUtil.getWidthPixels(this);
        layoutParams.width= width;
        layoutParams.height=width;
        imgTextViewPage.setLayoutParams(layoutParams);
    }

    @Override
    protected ProductDetailPresenter initPresenter() {
        return new ProductDetailPresenter(data.getId());
    }

    @Override
    public void onLoadProduct(Product.Data data) {
        LogUtil.e(data.getSmall_images().toString());
        List<String>imageList=data.getSmall_images();
        imageList.add(0,data.getImg_url());
        adapter.setData(imageList);
    }

}
