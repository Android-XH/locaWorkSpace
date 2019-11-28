package com.example.shop.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.fragment.CategoryListFragment;
import com.example.shop.fragment.HomeFragment;
import com.example.shop.util.PgyUtil;
import com.example.worktools.adapter.ViewPageTitleAdapter;
import com.example.worktools.baseview.BaseActivity;
import com.example.worktools.presenter.BasePresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.shop.common.ConfigCommon.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppBaseActivity implements BaseActivity.RequestPermissionCallBack {
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tl_menu)
    TabLayout tlMenu;
    private List<Fragment> fragmentList;
    private List<String>titleList;
    private ViewPageTitleAdapter adapter;
    @Override
    protected int setContentView() {
        return R.layout.activity_main_layout;
    }

    @Override
    protected void initAppData(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE);
        } else {
            if (Build.VERSION.SDK_INT >= 26) {
                boolean b = getPackageManager().canRequestPackageInstalls();
                if (b) {
                    PgyUtil.Update();
                } else {
                    requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, this);
                }
            } else {
                PgyUtil.Update();
            }
        }
        titleList= new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menu)));
        fragmentList=new ArrayList<>();
        fragmentList.add(HomeFragment.getInstance());
        fragmentList.add(CategoryListFragment.getInstance());
        adapter=new ViewPageTitleAdapter(getSupportFragmentManager(),titleList,fragmentList);
    }

    @Override
    protected void initView() {
        vpContent.setAdapter(adapter);
        tlMenu.setupWithViewPager(vpContent);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void granted() {
        PgyUtil.Update();
    }

    @Override
    public void denied() {

    }
}
