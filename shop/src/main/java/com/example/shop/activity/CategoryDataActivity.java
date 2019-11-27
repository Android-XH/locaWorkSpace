package com.example.shop.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.bean.Product;
import com.example.shop.common.IntentKey;
import com.example.shop.fragment.ProductListFragment;
import com.example.shop.presenter.SearchDataPresenter;
import com.example.shop.util.StringUtil;
import com.example.shop.viewImpl.ISearchDataView;
import com.example.worktools.adapter.ViewPageTitleAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryDataActivity extends AppBaseActivity<SearchDataPresenter> implements ISearchDataView {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tl_category)
    TabLayout tlCategory;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private String keyWord;
    private String title;
    private List<Fragment>fragmentList;
    private List<String>titleList;
    @Override
    protected int setContentView() {
        return R.layout.activity_category_data_layout;
    }

    @Override
    protected void initAppData(Bundle bundle) {
        title = bundle.getString(IntentKey.TITLE);
        setTitle(title);
        keyWord = bundle.getString(IntentKey.KEY_WORD);
        fragmentList=new ArrayList<>();
        titleList=new ArrayList<>();
    }

    @Override
    protected void initView() {
        showBackImb();
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
        if(StringUtil.isNoEmpty(keyWord)){
            etSearch.setText(keyWord);
            etSearch.setSelection(keyWord.length());
        }
        for(int i=0;i<3;i++){
            titleList.add("标题"+i);
            fragmentList.add( new ProductListFragment());
        }

        vpContent.setAdapter(new ViewPageTitleAdapter(getSupportFragmentManager(),titleList,fragmentList));
        tlCategory.setupWithViewPager(vpContent);
    }

    @Override
    protected SearchDataPresenter initPresenter() {
        showLoading(getString(R.string.loading_search));
        return new SearchDataPresenter();
    }

    @Override
    public void onLoadRefresh(List<Product.Data> dataList, int totalPage) {
        missLoading();
    }

    @Override
    public void onLoadMore(List<Product.Data> dataList, int totalPage) {
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
    @OnClick({R.id.tv_search, R.id.imb_vertical})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                break;
            case R.id.imb_vertical:
                break;
        }
    }
}
