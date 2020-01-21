package com.example.shop.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.shop.AppBaseActivity;
import com.example.shop.R;
import com.example.shop.fragment.CategoryListFragment;
import com.example.shop.fragment.HomeFragment;
import com.example.shop.fragment.MineFragment;
import com.example.shop.util.PgyUtil;
import com.example.shop.util.StartUtil;
import com.example.shop.util.share.SpUtil;
import com.example.worktools.adapter.ViewPageTitleAdapter;
import com.example.worktools.baseview.BaseActivity;
import com.example.worktools.presenter.BasePresenter;
import com.example.worktools.util.LogUtil;
import com.example.worktools.util.StringUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.shop.common.ConfigCommon.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppBaseActivity implements BaseActivity.RequestPermissionCallBack{
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tl_menu)
    TabLayout tlMenu;
    private List<Fragment> fragmentList;
    private List<String>titleList;
    private ViewPageTitleAdapter adapter;
    private int vpPosition;
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
        fragmentList.add(MineFragment.getInstance());
        adapter=new ViewPageTitleAdapter(getSupportFragmentManager(),titleList,fragmentList);
    }

    @Override
    protected void initView() {
//        vpContent.addOnPageChangeListener(this);
        vpContent.setAdapter(adapter);
        tlMenu.setupWithViewPager(vpContent);
        tlMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtil.e("onTabSelected>>>>"+tab.getPosition());
                if(tab.getPosition()==2&&!StartUtil.getInstance().isLogin()){
                    vpContent.setCurrentItem(vpPosition);
                }else{
                    vpPosition=tab.getPosition();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
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
