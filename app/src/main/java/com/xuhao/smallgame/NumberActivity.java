package com.xuhao.smallgame;

import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberActivity extends AppCompatActivity {

    @BindView(R.id.tv_random)
    TextView tvRandom;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.btn_start)
    Button btnStart;
    private float value,userValue;
    private boolean isRunning;
    private final float MAX_VALUE=9999.9999f;
    private final long TIME=10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        ButterKnife.bind(this);
        value=getRandom();
        tvRandom.setText(String.format("%.4f",value));
    }

    @OnClick({R.id.tv_random, R.id.btn_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_random:
                if(!isRunning){
                    value=getRandom();
                    tvRandom.setText(String.format("%.4f",value));
                }
                break;
            case R.id.btn_start:
                if(isRunning){
                    stopAnimation();
                }else{
                    startAnimation();
                }
                break;
        }
    }
    private float getRandom(){
        return (float) (Math.random()*1.0000f*MAX_VALUE);
    }
    private void showAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(0.0001f,MAX_VALUE);
        animator.setDuration(TIME);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(valueAnimator1 -> {
            if(isRunning){
                userValue = (float) valueAnimator1.getAnimatedValue();
                tvNumber.setText(String.format("%.4f",userValue));
            }
        });
        animator.start();
    }
    private void startAnimation(){
        timer= new Timer();
        isRunning=true;
        btnStart.setText("停止");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                runOnUiThread(() -> showAnimation());
            }
        },0, TIME);
    }
    private void stopAnimation(){
        isRunning=false;
        btnStart.setText("开始");
        timer.cancel();
        Toast.makeText(this,value==userValue?"恭喜你，点中了！":"唉！没点中，再接再厉！",Toast.LENGTH_SHORT).show();
    }
    Timer timer;
}
