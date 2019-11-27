package com.example.shop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shop.R;
import com.example.shop.activity.CategoryDataActivity;
import com.example.shop.presenter.SearchDataPresenter;
import com.example.worktools.baseview.BaseFragment;

public class ProductListFragment extends BaseFragment<SearchDataPresenter, CategoryDataActivity> {

    @Override
    protected int getLayout() {
        return R.layout.fragment_product_list_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected SearchDataPresenter initPresent() {
        return new SearchDataPresenter();
    }
}
