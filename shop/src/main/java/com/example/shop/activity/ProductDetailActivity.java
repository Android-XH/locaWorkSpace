package com.example.shop.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.worktools.dialog.TextDialog;
import com.example.worktools.dialog.dialoginstener.DialogYesClick;
import com.example.worktools.recycle.SpacesItemDecoration;
import com.example.worktools.util.CheckApkExist;
import com.example.worktools.util.DpUtil;
import com.example.worktools.view.ImageTextViewPager;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.worktools.util.CheckApkExist.taobaoPkgName;
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
    private TextDialog textDialog,shareDialog;
    private String couponUrl,title;
    private boolean isShare;

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
        verticalAdapter = new ProductVerticalAdapter(this);
        verticalAdapter.setOnRecycleItemClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
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
        couponUrl = data.getCoupon_share_url();
        title=data.getTitle();
        tvShopTitle.setText(data.getShop_title());
        tvProductTitle.setText(data.getTitle());
        if (StringUtil.isNoEmpty(data.getCoupon_info())) {
            tvCouponInfo.setText(data.getCoupon_info());
        } else {
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
    public void onLoadKey(String key) {
        if (isShare) {
            if (shareDialog == null) {
                shareDialog = TextDialog.createDialog(this)
                        .setMessage("口令已复制，分享给好友？")
                        .setRightButton(R.string.confirm)
                        .setRightClick(new DialogYesClick() {
                            @Override
                            public void onYseClick(String msg) {
                                String message = "我在"+getString(R.string.app_name)+"发现了一个很实惠的["+title+"]，复制该口令[" + key + "]打开淘宝APP即可查看优惠详情！";
                                shareMessage(title,message);
                            }
                        });
            }
            shareDialog.show();
        } else {
            if (textDialog == null) {
                textDialog = TextDialog.createDialog(this)
                        .setMessage("前往淘宝客户端查看优惠详情？")
                        .setRightButton(R.string.confirm)
                        .setRightClick(new DialogYesClick() {
                            @Override
                            public void onYseClick(String msg) {
                                setParimaryClip(key);
                                Intent intent = getPackageManager().getLaunchIntentForPackage(taobaoPkgName);
                                startActivity(intent);
                            }
                        });
            }
            textDialog.show();
        }
    }

    private void setParimaryClip(String message) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("simple text", message);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
    private void shareMessage(String title,String message){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain"); // 纯文本
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }
    @Override
    public void onItemClick(Product.Data data, int position) {
        StartUtil.getInstance().startProductDetail(this, data);
    }

    @OnClick({R.id.tv_share, R.id.tv_get_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_share:
                isShare = true;
                getPresenter().getTaoKey();
                break;
            case R.id.tv_get_coupon:
                isShare = false;
                if (CheckApkExist.checkTaoBao(this)) {
                    getPresenter().getTaoKey();
                } else {
                    Uri uri = Uri.parse(couponUrl);
                    Intent intent = new Intent();
                    intent.setData(uri);//Url 就是你要打开的网址
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(Intent.createChooser(intent, "请选择浏览器"));
                }
                break;
        }
    }

}
