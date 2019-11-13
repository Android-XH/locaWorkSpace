package com.xuhao.smallgame;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhao.smallgame.bean.Bubble;
import com.xuhao.smallgame.view.WaveViewByBezier;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaoActivity extends AppCompatActivity implements WaveViewByBezier.onClickPopListener {

    @BindView(R.id.bubbleView)
    WaveViewByBezier bubbleView;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private boolean isPlaying;
    private float point = 0;
    private final long TIME=60*1000;//游戏时间60秒
    private final long SECEND=1000;//每次跳动1秒
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pao);
        ButterKnife.bind(this);
        play();
        bubbleView.setOnClickPopListener(this);
    }

    /**
     * 倒计时60秒，一次1秒
     */
    CountDownTimer timer = new CountDownTimer(TIME, SECEND) {
        @SuppressLint("DefaultLocale")
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            String time=String.valueOf(millisUntilFinished / 1000);
            if(millisUntilFinished<=10000){
                tvTime.setTextColor(Color.RED);
            }else{
                tvTime.setTextColor(Color.BLACK);
            }
            tvTime.setText(String.format("还剩%s秒",time ));
        }

        @Override
        public void onFinish() {
            tvTime.setText("时间到");
            stopGame();
        }
    };

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        startGame();
    }

    private void startGame() {
        isPlaying=true;
        timer.start();
        point=0;
        tvPoint.setText(String.format("分数：%.0f",point));
        btnStart.setVisibility(View.GONE);
        bubbleView.startAnim();
        if (null != mpMediaPlayer&&!mpMediaPlayer.isPlaying()) {
            mpMediaPlayer.start();
        }
    }
    private void stopGame(){
        isPlaying=false;
        btnStart.setVisibility(View.VISIBLE);
        bubbleView.stopAnim();
        if (null != mpMediaPlayer&&mpMediaPlayer.isPlaying()) {
            mpMediaPlayer.pause();
        }
        Toast.makeText(PaoActivity.this,"游戏时间到！",Toast.LENGTH_SHORT).show();
    }
    MediaPlayer mpMediaPlayer;//背景音乐播放控制器

    protected void play() {
        mpMediaPlayer = MediaPlayer.create(this, R.raw.backmusic);
        try {
            mpMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mpMediaPlayer.setOnCompletionListener(mp -> mp.start());
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClickPop(Bubble bubble) {
        playBoom();
        point += bubble.getRadius();
        tvPoint.setText(String.format("分数：%.0f",point));
    }

    private void playBoom() {
        MediaPlayer popMediaPlayer = MediaPlayer.create(this, R.raw.popboom);
        try {
            popMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (popMediaPlayer.isPlaying()) {
            popMediaPlayer.reset();
        }
        popMediaPlayer.start();
        popMediaPlayer.setOnCompletionListener(arg0 -> {
            arg0.stop();
            arg0.release();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mpMediaPlayer) {
            mpMediaPlayer.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mpMediaPlayer) {
            mpMediaPlayer.pause();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isPlaying&&null != mpMediaPlayer) {
            mpMediaPlayer.start();
        }
    }
}
