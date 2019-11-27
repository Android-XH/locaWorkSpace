package com.example.shop.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.shop.R;
import com.example.worktools.util.BitmapUtil;
import com.example.worktools.util.DpUtil;

/**
 * Created by wing on 16/1/14.
 */
public class LaunchProgressView extends View {
    private Paint mSRCPaint;

    private Paint mPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private int y;
    private int x;

    private int bigCircleColor;//圆颜色

    private int smallCircleColor;//圆颜色

    int verticalCenter   ;
    int horizontalCenter ;

    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private Bitmap bgBitmap;
    private Path mPath;
    private boolean isLeft;
    //    private int y;
    private int mWidth;
    private int mHeight;
    private int mPercent;

    private final int default_reached_color = Color.rgb(66, 145, 241);

    private Drawable bitMap;

    public LaunchProgressView(Context context) {
        this(context, null);
    }

    public LaunchProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LaunchProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle(context,attrs,defStyleAttr);
    }

    private void initStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PointView, defStyleAttr, 0);
        bigCircleColor = typedArray.getColor(R.styleable.PointView_point_big_circle_color, default_reached_color);
        smallCircleColor = typedArray.getColor(R.styleable.PointView_point_small_circle_color, default_reached_color);
        mPercent=typedArray.getInt(R.styleable.PointView_point_progress,0);
        bitMap=typedArray.getDrawable(R.styleable.PointView_point_bitmap);
        typedArray.recycle();
        mPaint = new Paint();

        mPaint.setStrokeWidth(10);


        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setColor(smallCircleColor);

        mBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mSRCPaint = new Paint();
        mSRCPaint.setAntiAlias(true);//抗锯齿
        mSRCPaint.setColor(bigCircleColor);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
            horizontalCenter=mWidth/2;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
            verticalCenter=mWidth/2;
        }
        y = mHeight;
        setMeasuredDimension(mWidth, mHeight);
        if(bitMap==null){
            bgBitmap=BitmapUtil.decodeSampledBitmapFromResource(getResources(),R.drawable.icon_logo,horizontalCenter,verticalCenter);
        }else{
            bgBitmap = BitmapUtil.drawableToBitmap(bitMap);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (x > 50) {
            isLeft = true;
        } else if (x < 0) {
            isLeft = false;
        }
        if (isLeft) {
            x = x - 1;
        } else {
            x = x + 1;
        }
        mPath.reset();
        y = (int) ((1-start /100f) *mHeight);
        mPath.moveTo(0, y);
        mPath.cubicTo(100 + x * 2, 50 + y, 100 + x * 2, y - 50, mWidth, y);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();

        //清除掉图像 不然path会重叠
        mBitmap.eraseColor(Color.parseColor("#00000000"));

        mCanvas.drawCircle(horizontalCenter, verticalCenter, mWidth / 2, mSRCPaint);

        mPaint.setXfermode(mMode);
        //src
        mCanvas.drawPath(mPath, mPaint);
        mPaint.setXfermode(null);

        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawBitmap(bgBitmap,horizontalCenter-bgBitmap.getWidth()/2, verticalCenter-bgBitmap.getHeight()/2,null);
        postInvalidateDelayed(10);
    }

    public void setPercent(int percent){
        mPercent = percent;
        startRunBarBrother();
    }
    private int getPoint(){
        return mPercent;
    }
    private int start;
    private ValueAnimator valueAnimator;
    private void startRunBarBrother() {
        valueAnimator = ValueAnimator.ofInt(start, getPoint());
        valueAnimator.setDuration(2200);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());//加速减速
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                start = (int) valueAnimator.getAnimatedValue();
                if(getProgressListener()!=null&&start==100){
                    getProgressListener().onDone();
                }
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(valueAnimator!=null){
            valueAnimator.cancel();
            valueAnimator=null;
        }
    }
    private OnProgressListener progressListener;

    public OnProgressListener getProgressListener() {
        return progressListener;
    }

    public void setProgressListener(OnProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public interface OnProgressListener{
        void onDone();
    }
}