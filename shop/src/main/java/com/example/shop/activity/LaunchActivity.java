package com.example.shop.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.shop.BuildConfig;
import com.example.shop.R;
import com.example.shop.view.LaunchProgressView;
import com.example.worktools.util.AppVersionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xuhao on 2017/12/20.
 */

public class LaunchActivity extends Activity implements LaunchProgressView.OnProgressListener{
    @BindView(R.id.tv_app_ver)
    TextView tvAppVer;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;
    @BindView(R.id.launch_progress_view)
    LaunchProgressView launchProgressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen(true);
        setContentView(R.layout.activity_launch_layout);
        ButterKnife.bind(this);
        launchProgressView.setPercent(100);
        String ver = getString(R.string.app_name) + (BuildConfig.DEBUG ? "开发版 V " : " V ") + AppVersionUtil.getVerName(this);
        tvAppVer.setText(ver);
        launchProgressView.setProgressListener(this);
    }

    private void setFullscreen(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onDone() {
        startActivity(new Intent(LaunchActivity.this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}
