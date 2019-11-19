package com.example.shop.activity;

import android.os.Bundle;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.adapter.ProductAdapter;
import com.example.shop.adapter.ProductVerticalAdapter;
import com.example.shop.bean.Product;
import com.example.shop.common.IntentKey;
import com.example.shop.presenter.SearchDataPresenter;
import com.example.shop.util.StringUtil;
import com.example.shop.viewImpl.ISearchDataView;
import com.example.worktools.recycle.SpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.worktools.util.DpUtil.dip2px;

public class SearchDataActivity extends AppBaseActivity<SearchDataPresenter> implements ISearchDataView {
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
    }

    @Override
    protected void initView() {
        showBackImb();
        int lineWidth=5;
        int spanCount=2;
        recycleView.setLayoutManager(new GridLayoutManager(this, spanCount,GridLayoutManager.VERTICAL,false));
        recycleView.addItemDecoration(new SpacesItemDecoration(dip2px(lineWidth), dip2px(lineWidth), getResources().getColor(R.color.line)));
        recycleView.setAdapter(adapter);
    }

    @Override
    protected SearchDataPresenter initPresenter() {
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
        if(StringUtil.isNoEmpty(etSearch.getText().toString())){
            keyWord=etSearch.getText().toString();
            showLoading(getString(R.string.loading_search));
            getPresenter().setKeyWord(keyWord);
            getPresenter().loadRefresh();
        }
    }
}
