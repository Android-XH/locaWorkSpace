package com.xuhao.smallgame.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.xuhao.smallgame.R;
import com.xuhao.smallgame.bean.Bubble;
import com.xuhao.smallgame.util.ColorUtil;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class BubbleView extends View implements onBubbleListener {

    private static final int MSG_CREATE_BUBBLE = 0;
    protected float mWidth;
    protected float mHeight;
    //气泡上升高度(水面高度)
    protected float mTop;
    //底部 == mHeight
    protected Paint mPaint;
    //存放气泡集合
    private List<Bubble> mBubbles = new CopyOnWriteArrayList<>();
    private Random mRandom = new Random();
    private boolean mStarting = false;
    private Paint mBubblePaint;
    //气泡随机颜色值
    private String[] mBubbleColors = {
            "#1E90FF",
            "#00FA9A",
            "#00EEEE"
    };
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





    public BubbleView(Context context) {
        super(context);
        mContext = context;
        K = A;
        initPaint();
        initAnimation();
    }
    public BubbleView(Context context,AttributeSet attrs) {
        super(context,attrs);
        mContext = context;
        getAttr(attrs);
        K = A;
        initPaint();
        initAnimation();
    }
    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getAttr(attrs);
        K = A;
        initPaint();
        initAnimation();
    }
    private void initPaint(){
        mBubblePaint=new Paint();
        mPaint=new Paint();
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(waveColor);
    }
    private void initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, getWidth());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            /**
             * 刷新页面调取onDraw方法，通过变更φ 达到移动效果
             */
            invalidate();
        });
        if (waveStart) {
            valueAnimator.start();
        }
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
    /**
     开始气泡上升动画
     */
    @Override
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
     停止气泡上升动画
     */
    @Override
    public void stopAnim() {
        if (mStarting) {
            mStarting = false;
            mHandler.removeMessages(MSG_CREATE_BUBBLE);
            mBubbles.clear();
            invalidate();
        }
    }
    @Override
    public void invalidate() {
        if (mStarting) {
            super.invalidate();
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int)mWidth,(int)mHeight);
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_CREATE_BUBBLE) {
                if (mStarting) {
                    if (mWidth > 0) {
                        Bubble bubble = new Bubble();
                        //此处随机气泡半径, 因项目控件width较小, 可自行设置气泡半径随机范围
                        int radius = mRandom.nextInt((int) (mWidth / 20f)) + (int)(mWidth / 10);
                        bubble.setRadius(radius);
                        bubble.setX(mWidth / 2);
                        bubble.setY(mHeight);
                        // - 0.5f 左右随机摆动
                        bubble.setSpeedX((mRandom.nextFloat() - 0.5f) * 2.3f);
                        bubble.setSpeedY(mRandom.nextFloat() * 10 + 1);
                        //此属性表示不一定所有气泡必须浮出水面才消失(可不要)
                        bubble.setRemoveY(mRandom.nextInt(radius));
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
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBubble(canvas);
        drwaLine(canvas);
        postInvalidate();
    }
    private void drwaLine(Canvas canvas){
        switch (waveType) {
            case SIN:
                drawSin(canvas);
                break;
            case COS:
                drawCos(canvas);
                break;
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
    private void drawBubble(Canvas canvas) {
        List<Bubble> list = mBubbles;
        for (Bubble bubble : list) {
            float y = bubble.getY();
            float x = bubble.getX();
            float speedY = bubble.getSpeedY();
            float speedX = bubble.getSpeedX();
            float radius = bubble.getRadius();

            if (y - bubble.getRemoveY() - speedY <= mTop) {
                //消失后就移除
                mBubbles.remove(bubble);
            } else {
                if(bubble.isToach()){
                    mBubbles.remove(bubble);
                }
                if ((x + speedX <= radius) || (x + speedX >= mWidth - radius)) {
                    //边缘处理 减缓左右摆动幅度
                    speedX = -speedX * 0.8f;
                    bubble.setSpeedX(speedX);
                }

                //变换x y坐标
                x += speedX;
                y -= speedY;

                //减速 (加速减速可自行设置)
                speedY *= 0.99f;

                if (speedY < 1) {
                    //最小上升速度
                    speedY = 1;
                }

                bubble.setX(x);
                bubble.setY(y);
                bubble.setSpeedY(speedY);
                //设置气泡渐变
                RadialGradient shader = new RadialGradient(x, y, radius, bubble.getShaderColor(), null, Shader.TileMode.REPEAT);
                mBubblePaint.setShader(shader);
                canvas.drawCircle(x, y, radius, mBubblePaint);
                //气泡碰撞计算
//                collision(bubble, x, y, radius);
            }
        }
    }

    private void collision(final Bubble bubble, final float x, final float y, final float radius) {
        //开启线程避免界面卡顿
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Bubble tempBubble : mBubbles) {
                    if (tempBubble != bubble) {
                        float tempX = tempBubble.getX();
                        float tempY = tempBubble.getY();
                        float tempRadius = tempBubble.getRadius();
                        if (isCollisionWithCircle(x, y, tempX, tempY, radius, tempRadius)) {
                            //气泡的半径扩大
                            float r = Math.max(radius, tempRadius) * 1.015f;
                            float maxRadius = mWidth / 3f;
                            if (r > maxRadius) {
                                r = maxRadius;
                            }

                            Bubble removeBubble, leaveBubble;
                            //移除下面气泡, 保留上面气泡
                            if (y < tempY) {
                                removeBubble = tempBubble;
                                leaveBubble = bubble;
                            } else {
                                removeBubble = bubble;
                                leaveBubble = tempBubble;
                            }
                            mBubbles.remove(removeBubble);

                            //模拟碰撞 方向改变
                            float tempLeaveSpeedX = leaveBubble.getSpeedX();
                            float leaveSpeedX = Math.abs(tempLeaveSpeedX);
                            if (leaveBubble.getX() < removeBubble.getX()) {
                                leaveSpeedX = -Math.abs(tempLeaveSpeedX);
                            }

                            //保留气泡属性设置
                            leaveBubble.setRadius(r);
                            leaveBubble.setSpeedX(leaveSpeedX * 0.9f);
                            //碰撞速度加快
                            leaveBubble.setSpeedY((removeBubble.getSpeedY() + leaveBubble.getSpeedY()) / 2f);
                            //leaveBubble.setShaderColor(blendColor(leaveBubble.getOriginalColor(), removeBubble.getOriginalColor()));
                        }
                    }
                }
            }
        }).start();
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
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            Log.e(">>>>","x=="+event.getX()+"Y=="+event.getY());
            for(Bubble bundle:mBubbles){
                float tempX = bundle.getX();
                float tempY = bundle.getY();
                if(isCollisionWithCircle(tempX,tempY,event.getX(),event.getY(),bundle.getRadius(),0f)){
                    bundle.setToach(true);
                }
            }
        }
        return true;
    }
    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
