package com.example.shop.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shop.R;
import com.example.shop.activity.MainActivity;
import com.example.shop.adapter.CategoryContentAdapter;
import com.example.shop.adapter.CategoryTitleAdapter;
import com.example.shop.bean.Category;
import com.example.shop.bean.CategoryItem;
import com.example.shop.bean.MCategory;
import com.example.shop.bean.array.MCategoryList;
import com.example.shop.presenter.MCategoryPresenter;
import com.example.shop.util.StartUtil;
import com.example.shop.viewImpl.IMCategoryView;
import com.example.worktools.baseview.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CategoryListFragment extends BaseFragment<MCategoryPresenter, MainActivity> implements IMCategoryView {
    private static final String M_CATEGORY_DATA = "m_category_data";
    @BindView(R.id.left_list)
    ListView leftList;
    @BindView(R.id.right_list)
    ListView rightList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_refresh_title)
    TextView tvRefreshTitle;
    @BindView(R.id.tv_refresh_bottom)
    TextView tvRefreshBottom;
    private CategoryTitleAdapter titleAdapter;
    private CategoryContentAdapter contentAdapter;
    private int select;
    private int first, center;

    public static CategoryListFragment getInstance() {
        CategoryListFragment fragment = new CategoryListFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_product_list_layout;
    }

    @Override
    protected void initData(Bundle bundle) {
        titleAdapter = new CategoryTitleAdapter(getAppActivity());
        contentAdapter = new CategoryContentAdapter(getAppActivity());
    }

    @Override
    protected void initView() {
        leftList.setAdapter(titleAdapter);
        rightList.setAdapter(contentAdapter);
        contentAdapter.setOnItemClickListener(new CategoryContentAdapter.onItemClickListener() {
            @Override
            public void onItemClick(CategoryItem.Data data) {
                StartUtil.getInstance().startSearchData(getAppActivity(),data);
            }
        });
        leftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                select = position;
                setSelect();
            }
        });
        leftList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                first = firstVisibleItem;
                center = totalItemCount / 2;
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                select -= 1;
                select = select < 0 ? 0 : select;
                setSelect();
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                select += 1;
                setSelect();
            }
        });
    }

    private void setSelect() {
        rightList.postDelayed(new Runnable() {
            @Override
            public void run() {
                titleAdapter.setSelectItem(select);
                contentAdapter.refreshList(titleAdapter.getList().get(select).getCategories());
                if (select > center) {
                    leftList.post(new Runnable() {
                        @Override
                        public void run() {
                            int s = select - center;
                            if (s > first) {
                                leftList.setSelection(s);
                            } else {
                                leftList.smoothScrollToPosition(s);
                            }
                        }
                    });
                }else{
                    leftList.smoothScrollToPosition(select);
                }
                rightList.setSelection(0);
                List<MCategory.Data>data=titleAdapter.getList();
                if (select == 0) {
                    refreshLayout.setEnableRefresh(false);
                    refreshLayout.setEnableLoadMore(true);
                    tvRefreshBottom.setText(String.format(getString(R.string.down_category),data.get(select + 1).getName()));
                } else if (select == titleAdapter.getCount() - 1) {
                    tvRefreshTitle.setText(String.format(getString(R.string.up_category),data.get(select - 1).getName()));
                    refreshLayout.setEnableRefresh(true);
                    refreshLayout.setEnableLoadMore(false);
                } else {
                    refreshLayout.setEnableRefresh(true);
                    refreshLayout.setEnableLoadMore(true);
                    tvRefreshBottom.setText(String.format(getString(R.string.down_category),data.get(select + 1).getName()));
                    tvRefreshTitle.setText(String.format(getString(R.string.up_category),data.get(select - 1).getName()));
                }
            }
        }, 300);

    }

    @Override
    protected MCategoryPresenter initPresent() {
        return new MCategoryPresenter();
    }

    @Override
    public void onLoadData(MCategoryList dataList) {
        List<MCategory.Data> data = dataList.getData();
        if (data.size() > 0) {
            if (data.size() > 1 && data.get(1) != null) {
                tvRefreshTitle.setText(String.format(getString(R.string.up_category),data.get(1).getName()));
            }
            data.get(0).setSelect(true);
            contentAdapter.refreshList(data.get(0).getCategories());
        }
        titleAdapter.refreshList(data);
    }

    @Override
    public void onLoadFail(String msg) {

    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        StartUtil.getInstance().startSearch(getAppActivity());
    }
}
