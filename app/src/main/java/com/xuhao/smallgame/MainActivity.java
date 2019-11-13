package com.xuhao.smallgame;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_number, R.id.btn_pp, R.id.btn_ds})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_number:
                startActivity(new Intent(this,NumberActivity.class));
                break;
            case R.id.btn_pp:
                startActivity(new Intent(this,PaoActivity.class));
                break;
            case R.id.btn_ds:
                break;
        }
    }
}
