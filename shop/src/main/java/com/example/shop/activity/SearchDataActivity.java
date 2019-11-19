package com.example.shop.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.ProductAdapter;
import com.example.shop.adapter.ProductVerticalAdapter;
import com.example.shop.bean.Product;
import com.example.shop.common.IntentKey;
import com.example.shop.presenter.SearchDataPresenter;
import com.example.shop.util.StartUtil;
import com.example.shop.util.StringUtil;
import com.example.shop.viewImpl.ISearchDataView;
import com.example.worktools.adapter.listener.OnRecycleItemClickListener;
import com.example.worktools.recycle.SpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.worktools.util.DpUtil.dip2px;

public class SearchDataActivity extends AppBaseActivity<SearchDataPresenter> implements ISearchDataView, OnRecycleItemClickListener<Product.Data>, OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String keyWord;
    private ProductVerticalAdapter adapter;
    @Override
    protected int setContentView() {
        return R.layout.activity_search_data_layout;
    }

    @Override
    protected void initAppData(Bundle bundle) {
        keyWord = bundle.getString(IntentKey.KEY_WORD);
        adapter=new ProductVerticalAdapter(this);
        adapter.setOnRecycleItemClickListener(this);
    }

    @Override
    protected void initView() {
        showBackImb();
        int lineWidth=5;
        int spanCount=2;
        recycleView.setLayoutManager(new GridLayoutManager(this, spanCount,GridLayoutManager.VERTICAL,false));
        recycleView.addItemDecoration(new SpacesItemDecoration(dip2px(lineWidth), dip2px(lineWidth), getResources().getColor(R.color.line)));
        recycleView.setAdapter(adapter);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
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

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        searchOfKey();
    }
    private void searchOfKey(){
        keyWord=etSearch.getText().toString();
        if(StringUtil.isNoEmpty(keyWord)){
            showLoading(getString(R.string.loading_search));
            getPresenter().setKeyWord(keyWord);
            getPresenter().loadRefresh();
        }else{
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
}
