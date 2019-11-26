package com.example.shop.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.ProductVerticalAdapter;
import com.example.shop.bean.Product;
import com.example.shop.common.IntentKey;
import com.example.shop.popup.ProductPopupWindow;
import com.example.shop.presenter.SearchDataPresenter;
import com.example.shop.util.StartUtil;
import com.example.shop.util.StringUtil;
import com.example.shop.viewImpl.ISearchDataView;
import com.example.worktools.adapter.listener.OnRecycleItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchDataActivity extends AppBaseActivity<SearchDataPresenter> implements ISearchDataView, OnRecycleItemClickListener<Product.Data>, OnLoadMoreListener, OnRefreshListener, ProductPopupWindow.onDownClick {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.sort_radio_group)
    RadioGroup sortRadioGroup;
    private String keyWord;
    private ProductVerticalAdapter adapter;
    int lineWidth = 5;
    int spanCount = 2;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private ProductPopupWindow popupWindow;

    @Override
    protected int setContentView() {
        return R.layout.activity_search_data_layout;
    }

    @Override
    protected void initAppData(Bundle bundle) {
        keyWord = bundle.getString(IntentKey.KEY_WORD);
        adapter = new ProductVerticalAdapter(this);
        adapter.setOnRecycleItemClickListener(this);
    }

    @Override
    protected void initView() {
        showBackImb();
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, spanCount, GridLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(gridLayoutManager);
//        recycleView.addItemDecoration(new SpacesItemDecoration(dip2px(lineWidth), dip2px(lineWidth), getResources().getColor(R.color.line)));
        adapter.setViewType(0);
        recycleView.setAdapter(adapter);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索要做的操作
                    searchOfKey();
                }
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        etSearch.setText(keyWord);
        etSearch.setSelection(keyWord.length());
    }

    @Override
    protected SearchDataPresenter initPresenter() {
        showLoading(getString(R.string.loading_search));
        return new SearchDataPresenter(keyWord);
    }

    @Override
    public void onLoadRefresh(List<Product.Data> dataList, int totalPage) {
        missLoading();
        adapter.setDataList(dataList);
    }

    @Override
    public void onLoadMore(List<Product.Data> dataList, int totalPage) {
        adapter.addDataList(dataList);
    }

    private void searchOfKey() {
        keyWord = etSearch.getText().toString();
        if (StringUtil.isNoEmpty(keyWord)) {
            showLoading(getString(R.string.loading_search));
            getPresenter().setKeyWord(keyWord);
            getPresenter().loadRefresh();
        } else {
            showToastMsg(getString(R.string.empty_input));
        }
    }

    @Override
    public void onItemClick(Product.Data data, int position) {
        StartUtil.getInstance().startProductDetail(this, data);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getPresenter().loadMore();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().loadRefresh();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onClick(ProductPopupWindow.Param param) {
        getPresenter().setMinPrice(param.getMinPrice());
        getPresenter().setMaxPrice(param.getMaxPrice());
        getPresenter().setType(param.getTypes());
        showLoading(getString(R.string.loading_data));
        getPresenter().loadRefresh();
    }

    @OnClick({R.id.imb_vertical, R.id.btn_select, R.id.tv_search})
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
                popupWindow.showAsDropDown(sortRadioGroup);
                break;
            case R.id.tv_search:
                searchOfKey();
                break;
        }
    }
}
