package com.example.shop.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.ProductImageAdapter;
import com.example.shop.adapter.ProductVerticalAdapter;
import com.example.shop.bean.Product;
import com.example.shop.common.IntentKey;
import com.example.shop.presenter.ProductDetailPresenter;
import com.example.shop.util.StartUtil;
import com.example.shop.util.StringUtil;
import com.example.shop.view.LineTextView;
import com.example.shop.viewImpl.IProductDetailView;
import com.example.worktools.adapter.listener.OnRecycleItemClickListener;
import com.example.worktools.recycle.SpacesItemDecoration;
import com.example.worktools.util.DpUtil;
import com.example.worktools.view.ImageTextViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.worktools.util.DpUtil.dip2px;

public class ProductDetailActivity extends AppBaseActivity<ProductDetailPresenter> implements IProductDetailView, OnRecycleItemClickListener<Product.Data> {
    @BindView(R.id.img_text_view_page)
    ImageTextViewPager imgTextViewPage;
    @BindView(R.id.tv_shop_title)
    TextView tvShopTitle;
    @BindView(R.id.tv_new_price)
    TextView tvNewPrice;
    @BindView(R.id.tv_coupon_info)
    TextView tvCouponInfo;
    @BindView(R.id.tv_volume)
    TextView tvVolume;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_product_title)
    TextView tvProductTitle;
    @BindView(R.id.tv_price)
    LineTextView tvPrice;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private Product.Data data;
    private ProductImageAdapter adapter;
    private ProductVerticalAdapter verticalAdapter;
    @Override
    protected int setContentView() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initAppData(Bundle bundle) {
        data = bundle.getParcelable(IntentKey.PRODUCT_DATA);
    }

    @Override
    protected void initView() {
        showBackImb();
        ViewGroup.LayoutParams layoutParams = imgTextViewPage.getLayoutParams();
        int width = DpUtil.getWidthPixels(this);
        layoutParams.width = width;
        layoutParams.height = width;
        imgTextViewPage.setLayoutParams(layoutParams);
        adapter = new ProductImageAdapter(this);
        imgTextViewPage.setAdapter(adapter);
        verticalAdapter=new ProductVerticalAdapter(this);
        verticalAdapter.setOnRecycleItemClickListener(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recycleView.addItemDecoration(new SpacesItemDecoration(dip2px(5), dip2px(5), getResources().getColor(R.color.line)));
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.setAdapter(verticalAdapter);
    }

    @Override
    protected ProductDetailPresenter initPresenter() {
        return new ProductDetailPresenter(data.getId());
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onLoadProduct(Product.Data data) {
        tvShopTitle.setText(data.getShop_title());
        tvProductTitle.setText(data.getTitle());
        if(StringUtil.isNoEmpty(data.getCoupon_info())){
            tvCouponInfo.setText(data.getCoupon_info());
        }else{
            tvCouponInfo.setVisibility(View.INVISIBLE);
        }
        tvPrice.setText(String.format("%.1f", data.getPrice()));
        tvNewPrice.setText(String.format("%.1f", data.getPrice() - data.getCoupon_amount()));
        tvLocation.setText(data.getShop_city());
        tvVolume.setText("已售：" + data.getSell_count());
        List<String> imageList = data.getSmall_images();
        imageList.add(0, data.getImg_url());
        adapter.setData(imageList);
    }

    @Override
    public void onLoadProductList(List<Product.Data> dataList) {
        verticalAdapter.setDataList(dataList);
    }

    @Override
    public void onItemClick(Product.Data data, int position) {
        StartUtil.getInstance().startProductDetail(this,data);
    }
}
