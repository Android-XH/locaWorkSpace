package com.example.shop.presenter;

import com.example.shop.bean.array.SearchList;
import com.example.shop.mode.ISearchMode;
import com.example.shop.mode.impl.SearchModeImpl;
import com.example.shop.viewImpl.ISearchView;
import com.example.worktools.model.CallBack;
import com.example.worktools.presenter.BasePresenter;

public class SearchPresenter extends BasePresenter<ISearchMode, ISearchView> {
    @Override
    public void getData() {
        getMode().getSearchKey(new CallBack<SearchList>() {
            @Override
            public void onSuccess(SearchList searchList) {
                getView().onLoadData(searchList.getData());
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    public ISearchMode initMode() {
        return new SearchModeImpl();
    }

}
