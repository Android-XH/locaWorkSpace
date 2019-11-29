package com.example.shop.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.ProductVerticalAdapter;
import com.example.shop.bean.Category;
import com.example.shop.bean.CategoryItem;
import com.example.shop.bean.Product;
import com.example.shop.common.IntentKey;
import com.example.shop.popup.ProductPopupWindow;
import com.example.shop.presenter.SearchDataPresenter;
import com.example.shop.util.StartUtil;
import com.example.worktools.util.StringUtil;
import com.example.shop.viewImpl.ISearchDataView;
import com.example.worktools.adapter.listener.OnRecycleItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchDataActivity extends AppBaseActivity<SearchDataPresenter> implements ISearchDataView, OnRecycleItemClickListener<Product.Data>, OnLoadMoreListener, OnRefreshListener, ProductPopupWindow.onDownClick, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.sort_radio_group)
    RadioGroup sortRadioGroup;
    @BindView(R.id.sort_radio_price)
    RadioButton sortRadioPrice;
    @BindView(R.id.btn_select)
    TextView btnSelect;
    private int menuID;
    private String keyWord;
    private ProductVerticalAdapter adapter;
    int spanCount = 2;
    private String title;
    private Category.Data category;
    private CategoryItem.Data categoryItem;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private ProductPopupWindow popupWindow;

    @Override
    protected int setContentView() {
        return R.layout.activity_search_data_layout;
    }

    @Override
    protected void initAppData(Bundle bundle) {
        Object object=bundle.get(IntentKey.SEARCH_VALUE);
        if(object!=null){
            if(object instanceof String){
                keyWord=String.valueOf(object);
            }
            if(object instanceof Integer){
                menuID=(Integer)object;
            }
            if(object instanceof Category.Data){
                category= (Category.Data) object;
            }
            if(object instanceof CategoryItem.Data){
                categoryItem= (CategoryItem.Data) object;
            }
        }
        adapter = new ProductVerticalAdapter(this);
        adapter.setOnRecycleItemClickListener(this);
    }

    @Override
    protected void initView() {
        showBackImb();
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, spanCount, GridLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(gridLayoutManager);
        adapter.setViewType(0);
        recycleView.setAdapter(adapter);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchOfKey();
                }
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        sortRadioGroup.setOnCheckedChangeListener(this);
        if(StringUtil.isNoEmpty(keyWord)){
            etSearch.setText(keyWord);
            etSearch.setSelection(keyWord.length());
        }
    }

    @Override
    protected SearchDataPresenter initPresenter() {
        SearchDataPresenter searchDataPresenter=new SearchDataPresenter();
        if(StringUtil.isNoEmpty(keyWord)){
            searchDataPresenter.setKeyWord(keyWord);
            searchDataPresenter.loadRefresh();
        }
        if(menuID!=0){
            searchDataPresenter.setMenuID(menuID);
            searchDataPresenter.loadRefresh();
        }
        if(category!=null){
            searchDataPresenter.setCategoryID(category.getCategory_id());
            searchDataPresenter.loadRefresh();
        }
        if(categoryItem!=null){
            searchDataPresenter.setCategoryItemID(categoryItem.getCategory_item_id());
            searchDataPresenter.loadRefresh();
        }
        return searchDataPresenter;
    }

    @Override
    public void onLoadRefresh(List<Product.Data> dataList, int totalPage) {
        missLoading();
        adapter.setDataList(dataList);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(List<Product.Data> dataList, int totalPage) {
        adapter.addDataList(dataList);
        refreshLayout.finishLoadMore();
    }

    private void searchOfKey() {
        keyWord = etSearch.getText().toString();
        showLoading(getString(R.string.loading_search));
        getPresenter().setKeyWord(keyWord);
        getPresenter().loadRefresh();
    }

    @Override
    public void onItemClick(Product.Data data, int position) {
        StartUtil.getInstance().startProductDetail(this, data);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getPresenter().loadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().loadRefresh();
    }

    @Override
    public void onDismissListener() {
        btnSelect.setSelected(false);
    }

    @Override
    public void onClick(ProductPopupWindow.Param param) {
        getPresenter().setMinPrice(param.getMinPrice());
        getPresenter().setMaxPrice(param.getMaxPrice());
        getPresenter().setType(param.getTypes());
        showLoading(getString(R.string.loading_data));
        getPresenter().loadRefresh();
    }

    @OnClick({R.id.imb_vertical, R.id.btn_select, R.id.tv_search,R.id.sort_radio_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imb_vertical:
                if (view.isSelected()) {
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    recycleView.setLayoutManager(gridLayoutManager);
                    adapter.setViewType(0);
                    recycleView.scrollToPosition(position);
                } else {
                    int position = gridLayoutManager.findFirstVisibleItemPosition();
                    recycleView.setLayoutManager(linearLayoutManager);
                    adapter.setViewType(1);
                    recycleView.scrollToPosition(position);
                }
                view.setSelected(!view.isSelected());
                break;
            case R.id.btn_select:
                if (popupWindow == null) {
                    popupWindow = ProductPopupWindow.newInstance(this).setOnDownClick(this);
                }
                btnSelect.setSelected(true);
                popupWindow.showAsDropDown(sortRadioGroup);
                break;
            case R.id.tv_search:
                searchOfKey();
                break;
            case R.id.sort_radio_price:
                showLoading(getString(R.string.loading_data));
                if (sortRadioPrice.isSelected()) {
                    getPresenter().setLoadSort("priceInverse");
                } else {
                    getPresenter().setLoadSort("price");
                }
                getPresenter().loadRefresh();
                sortRadioPrice.setSelected(!sortRadioPrice.isSelected());
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.sort_radio_all:
                showLoading(getString(R.string.loading_data));
                getPresenter().setLoadSort("");
                getPresenter().loadRefresh();
                break;
            case R.id.sort_radio_volume:
                showLoading(getString(R.string.loading_data));
                getPresenter().setLoadSort("volume");
                getPresenter().loadRefresh();
                break;
        }
    }
}
