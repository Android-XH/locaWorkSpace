package com.xuhao.smallgame.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.xuhao.smallgame.R;
import com.xuhao.smallgame.bean.Bubble;
import com.xuhao.smallgame.util.ColorUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by allen on 2016/12/13.
 * <p>
 * 波浪动画
 */

public class WaveViewByBezier extends View {

    private Context mContext;
    /**
     * 振幅
     */
    private int A;
    /**
     * 偏距
     */
    private int K;

    /**
     * 波形的颜色
     */
    private int waveColor = 0x802896C8;

    /**
     * 初相
     */
    private float φ;

    /**
     * 波形移动的速度
     */
    private float waveSpeed = 3f;

    /**
     * 角速度
     */
    private double ω;

    /**
     * 开始位置相差多少个周期
     */
    private double startPeriod;

    /**
     * 是否直接开启波形
     */
    private boolean waveStart;

    private Path path;
    private Paint paint;
    private static final int SIN = 0;
    private static final int COS = 1;
    private int waveType;
    private static final int TOP = 0;
    private static final int BOTTOM = 1;
    private int waveFillType;
    private ValueAnimator valueAnimator;

    /**
     * 气泡属性
     */
    private static final int MSG_CREATE_BUBBLE = 0;
    protected float mWidth;
    protected float mHeight;
    //气泡上升高度(水面高度)
    protected float mTop;
    //底部 == mHeight
    protected Paint mPaint;
    //存放气泡集合
    private List<Bubble> mBubbles = new ArrayList<>();
    private Random mRandom = new Random();
    private boolean mStarting = false;
    private Paint mBubblePaint;
    //气泡随机颜色值
    private String[] mBubbleColors = {
            "#1E90FF",
            "#00FA9A",
            "#00EEEE"
    };

    public WaveViewByBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttr(attrs);
        K = A;
        initPaint();
        initAnimation();
    }

    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RadarWaveView);

        waveType = typedArray.getInt(R.styleable.RadarWaveView_waveType, SIN);
        waveFillType = typedArray.getInt(R.styleable.RadarWaveView_waveFillType, BOTTOM);
        A = typedArray.getDimensionPixelOffset(R.styleable.RadarWaveView_waveAmplitude, dp2px(10));
        waveColor = typedArray.getColor(R.styleable.RadarWaveView_waveColor, waveColor);
        waveSpeed = typedArray.getFloat(R.styleable.RadarWaveView_waveSpeed, waveSpeed);
        startPeriod = typedArray.getFloat(R.styleable.RadarWaveView_waveStartPeriod, 0);
        waveStart = typedArray.getBoolean(R.styleable.RadarWaveView_waveStart, true);

        typedArray.recycle();
    }

    private void initPaint() {
        mBubblePaint = new Paint();
        mPaint = new Paint();
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(waveColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mTop=dp2px(50);
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ω = 2 * Math.PI / getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (waveType) {
            case SIN:
                drawSin(canvas);
                break;
            case COS:
                drawCos(canvas);
                break;
        }
        if (mStarting) {
            drawBubble(canvas);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_CREATE_BUBBLE) {
                if (mStarting) {
                    if (mWidth > 0) {
                        Bubble bubble = new Bubble();
                        int radius = mRandom.nextInt((int) (mWidth / 10f) + 10);
                        radius = Math.max(Math.min(radius, dp2px(50)), dp2px(20));
                        bubble.setRadius(radius);
                        bubble.setX(mRandom.nextInt((int) mWidth));
                        bubble.setY(mHeight);
                        // - 0.5f 左右随机摆动
                        bubble.setSpeedX((mRandom.nextFloat() - 0.5f) * 2.3f);
                        bubble.setSpeedY(mRandom.nextFloat() * 10 + 1);
                        //设置气泡颜色, 实际会渐变模糊
                        bubble.setShaderColor(ColorUtil.parseColor(mBubbleColors[mRandom.nextInt(mBubbleColors.length)], "#00"));
                        //将气泡添加到集合
                        mBubbles.add(bubble);
                    }
                    //下次产生气泡的时间
                    mHandler.sendEmptyMessageDelayed(MSG_CREATE_BUBBLE, (long) ((mRandom.nextInt(2) + 1) * 300));
                }
            }
        }
    };

    /**
     * 开始气泡上升动画
     */
    public void startAnim() {
        if (!mStarting) {
            mStarting = true;
            //此处开始需要通知不断绘制
            invalidate();
            //发送消息生成气泡
            mHandler.sendEmptyMessage(MSG_CREATE_BUBBLE);
        }
    }

    /**
     * 停止气泡上升动画
     */
    public void stopAnim() {
        if (mStarting) {
            mStarting = false;
            mBubbles.clear();
            invalidate();
            mHandler.removeMessages(MSG_CREATE_BUBBLE);
        }
    }

    @Override
    public void invalidate() {
        if (mStarting) {
            super.invalidate();
        }
    }

    private void drawBubble(Canvas canvas) {
        Iterator<Bubble> it = mBubbles.iterator();
        while (it.hasNext()) {
            Bubble bubble = it.next();
            float y = bubble.getY();
            float x = bubble.getX();
            float speedY = bubble.getSpeedY();
            float speedX = bubble.getSpeedX();
            float radius = bubble.getRadius();

            if (y - bubble.getRemoveY() - speedY <= mTop) {
                //消失后就移除
                it.remove();
            } else {
                if (bubble.isToach()) {
                    it.remove();
                } else {
                    if ((x + speedX <= radius) || (x + speedX >= mWidth - radius)) {
                        //边缘处理 减缓左右摆动幅度
                        speedX = -speedX * 0.8f;
                        bubble.setSpeedX(speedX);
                    }

                    //变换x y坐标
                    x += speedX;
                    y -= speedY;

                    //速度 (加速减速可自行设置)
                    speedY *= 0.99f;

                    if (speedY < 1) {
                        //最小上升速度
                        speedY = 2;
                    }

                    bubble.setX(x);
                    bubble.setY(y);
                    bubble.setSpeedY(speedY);
                    //设置气泡渐变
                    RadialGradient shader = new RadialGradient(x, y, radius, bubble.getShaderColor(), null, Shader.TileMode.REPEAT);
                    mBubblePaint.setShader(shader);
                    canvas.drawCircle(x, y, radius, mBubblePaint);
                }
            }
        }
    }

    /**
     * 根据cos函数绘制波形
     *
     * @param canvas
     */
    private void drawCos(Canvas canvas) {
        switch (waveFillType) {
            case TOP:
                fillTop(canvas);
                break;
            case BOTTOM:
                fillBottom(canvas);
                break;
        }
    }

    /**
     * 根据sin函数绘制波形
     *
     * @param canvas
     */
    private void drawSin(Canvas canvas) {

        switch (waveFillType) {
            case TOP:
                fillTop(canvas);
                break;
            case BOTTOM:
                fillBottom(canvas);
                break;
        }

    }

    /**
     * 填充波浪上面部分
     */
    private void fillTop(Canvas canvas) {

        φ -= waveSpeed / 100;
        float y;

        path.reset();
        path.moveTo(0, getHeight());

        for (float x = 0; x <= getWidth(); x += 20) {
            y = (float) (A * Math.sin(ω * x + φ + Math.PI * startPeriod) + K);
            path.lineTo(x, getHeight() - y);
        }

        path.lineTo(getWidth(), 0);
        path.lineTo(0, 0);
        path.close();

        canvas.drawPath(path, paint);

    }

    /**
     * 填充波浪下面部分
     */
    private void fillBottom(Canvas canvas) {

        φ -= waveSpeed / 100;
        float y;

        path.reset();
        path.moveTo(0, 0);

        for (float x = 0; x <= getWidth(); x += 20) {
            y = (float) (A * Math.sin(ω * x + φ + Math.PI * startPeriod) + K);
            path.lineTo(x, y);
        }

        //填充矩形
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();

        canvas.drawPath(path, paint);

    }

    private void initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, getWidth());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 刷新页面调取onDraw方法，通过变更φ 达到移动效果
                 */
                invalidate();
            }
        });
        if (waveStart) {
            valueAnimator.start();
        }
    }

//    public void startAnimation() {
//        if (valueAnimator != null) {
//            valueAnimator.start();
//        }
//    }
//
//    public void stopAnimation() {
//        if (valueAnimator != null) {
//            valueAnimator.cancel();
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public void pauseAnimation() {
//        if (valueAnimator != null) {
//            valueAnimator.pause();
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public void resumeAnimation() {
//        if (valueAnimator != null) {
//            valueAnimator.resume();
//        }
//    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * 是否碰撞
     */
    private boolean isCollisionWithCircle(float x1, float y1, float x2, float y2, float r1, float r2) {
        //直角坐标系，依点1和点2做平行线，|x1-x2|为横向直角边，|y1-y2|为纵向直角边 依勾股定理 c^2=a^2+b^2
        if (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) <= (r1 + r2) * 0.85f) {
            // 如果两圆的圆心距小于或等于两圆半径和则认为发生碰撞
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int size=mBubbles.size();
            for(int i=size-1;i>=0;i--){
                Bubble bundle = mBubbles.get(i);
                float tempX = bundle.getX();
                float tempY = bundle.getY();
                if (isCollisionWithCircle(tempX, tempY, event.getX(), event.getY(), bundle.getRadius(), dp2px(30))) {
                    bundle.setToach(true);
                    if(getOnClickPopListener()!=null){
                        getOnClickPopListener().onClickPop(bundle);
                    }
                    break;
                }
            }
        }
        return true;
    }
    private onClickPopListener onClickPopListener;

    public WaveViewByBezier.onClickPopListener getOnClickPopListener() {
        return onClickPopListener;
    }

    public void setOnClickPopListener(WaveViewByBezier.onClickPopListener onClickPopListener) {
        this.onClickPopListener = onClickPopListener;
    }

    public interface onClickPopListener{
        void onClickPop(Bubble bundle);
    }
}