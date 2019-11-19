package com.example.shop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.SearchTagAdapter;
import com.example.shop.bean.Search;
import com.example.shop.presenter.SearchPresenter;
import com.example.shop.util.StartUtil;
import com.example.shop.viewImpl.ISearchView;
import com.example.worktools.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;

public class SearchActivity extends AppBaseActivity<SearchPresenter> implements TagFlowLayout.OnTagClickListener<Search.Data> , ISearchView {
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;
    private SearchTagAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void initAppData(Bundle bundle) {
        adapter=new SearchTagAdapter(this);
    }

    @Override
    protected void initView() {
        showBackImb();
        setTitle(R.string.search);
        flowlayout.setAdapter(adapter);
        flowlayout.setOnTagClickListener(this);
    }

    @Override
    protected SearchPresenter initPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void onTagClick(int position, Search.Data data) {
        StartUtil.getInstance().startSearchData(this,data.getKeyword());
    }

    @Override
    public void onLoadData(List<Search.Data> dataList) {
        adapter.setDataList(dataList);
    }
}
