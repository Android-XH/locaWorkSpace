package com.example.shop.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.SearchTagAdapter;
import com.example.shop.bean.Search;
import com.example.shop.presenter.SearchPresenter;
import com.example.shop.util.StartUtil;
import com.example.worktools.util.StringUtil;
import com.example.shop.viewImpl.ISearchView;
import com.example.worktools.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends AppBaseActivity<SearchPresenter> implements TagFlowLayout.OnTagClickListener<Search.Data>, ISearchView {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;
    private SearchTagAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void initAppData(Bundle bundle) {
        adapter = new SearchTagAdapter(this);
    }

    @Override
    protected void initView() {
        showBackImb();
        setTitle(R.string.search);
        flowlayout.setAdapter(adapter);
        flowlayout.setOnTagClickListener(this);
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
    }

    @Override
    protected SearchPresenter initPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void onTagClick(int position, Search.Data data) {
        StartUtil.getInstance().startSearchData(this, data.getKeyword());
    }

    @Override
    public void onLoadData(List<Search.Data> dataList) {
        adapter.setDataList(dataList);
    }


    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        searchOfKey();
    }
    private void searchOfKey(){
        String keyWord=etSearch.getText().toString();
        if(StringUtil.isNoEmpty(keyWord)){
            StartUtil.getInstance().startSearchData(this,keyWord);
        }else{
            showToastMsg(getString(R.string.empty_input));
        }
    }
}
