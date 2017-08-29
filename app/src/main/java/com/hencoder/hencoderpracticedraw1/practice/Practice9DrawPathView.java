package com.hencoder.hencoderpracticedraw1.practice;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Practice9DrawPathView extends View {

    private Handler mAnimatorHandler;
    private Paint mPaint;
    private PathMeasure mMeasure;
    private Path path;
    //view宽高
    private int mViewWidth;
    private int mViewHeight;

    // 这个视图拥有的状态
    public static enum State {
        NONE,
        STARTING,
        ENDING
    }
    // 当前的状态(非常重要)
    private State mCurrentState = State.NONE;

    // 控制各个过程的动画
    private ValueAnimator mStartingAnimator;
    private ValueAnimator mEndingAnimator;

    // 动画数值(用于控制动画状态,因为同一时间内只允许有一种状态出现,具体数值处理取决于当前状态)
    private float mAnimatorValue = 0;

    // 默认的动效周期 2s
    private int defaultDuration = 5000;

    // 动效过程监听器
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;


    public Practice9DrawPathView(Context context) {
        this(context,null);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initAll();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initAll() {

        initPaint();

        initPath();

        initListener();

        initHandler();

        initAnimator();

        // 进入开始动画
        mCurrentState = State.STARTING;
        mStartingAnimator.start();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }

    private void initPath() {
        int radius = 150;
        path = new Path();
        RectF rectF = new RectF(getWidth()/2-radius,getHeight()/2,getWidth()/2,getHeight()/2+radius);
        path.addArc(rectF,150,210);
        rectF = new RectF(getWidth()/2,getHeight()/2,getWidth()/2+radius,getHeight()/2+radius);
        path.arcTo(rectF,180,210,false);
        path.lineTo(getWidth()/2,230);
        path.close();
        mMeasure = new PathMeasure();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制路径
        //练习内容：使用 canvas.drawPath() 方法画心形
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.FILL);
//
//        Path path = new Path();
//        int radius = 150;

        //方法一
//        path.addArc(getWidth()/2-radius,getHeight()/2,getWidth()/2,getHeight()/2+radius,150,210);
//        //注意addArc和arcTo起点不一样
//        path.arcTo(getWidth()/2,getHeight()/2,getWidth()/2+radius,getHeight()/2+radius,180,210,false);
//        path.lineTo(getWidth()/2,getHeight()*5/6);
//        path.close();
//        canvas.drawPath(path,paint);

        //方法二
//        path.addArc(getWidth()/2-radius,getHeight()/2,getWidth()/2,getHeight()/2+radius,150,210);
//        path.addArc(getWidth()/2,getHeight()/2,getWidth()/2+radius,getHeight()/2+radius,180,210);
//        path.lineTo(getWidth()/2,getHeight()*5/6);
//        path.lineTo((float) getArcStartX(radius),(float) getArcStartY(radius));
//        canvas.drawPath(path,paint);

        //方法三
        //计算出心形点画出心形
//        float angle = 10;
//        while (angle < 180) {
//            Point p = getHeartPoint(angle);
//            canvas.drawPoint(p.x, p.y, paint);
//            angle = angle + 0.01f;
//        }
        drawShape(canvas);

    }

    private double getArcStartX(int radius){
        return getWidth()/2- radius/2 - radius/2 * Math.cos(Math.toRadians(30.0f));
    }

    private double getArcStartY(int radius){
        return getHeight()/2 + radius/2 + radius/2 * Math.sin(Math.toRadians(30.0f));
    }

    public Point getHeartPoint(float angle) {
        float t = (float) (angle / Math.PI);
        float x = (float) (19.5 * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-20 * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));
        int offsetX = getWidth() / 2;
        int offsetY = getHeight() / 2 - 55;
        return new Point(offsetX + (int) x, offsetY + (int) y);
    }

    private void initListener() {
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //通知handler动画状态更新
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    private void initAnimator() {
        mStartingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defaultDuration);
        mEndingAnimator = ValueAnimator.ofFloat(1, 0).setDuration(defaultDuration);

        mStartingAnimator.setRepeatCount(100);
        mEndingAnimator.setRepeatCount(100);

        mStartingAnimator.addUpdateListener(mUpdateListener);
        mEndingAnimator.addUpdateListener(mUpdateListener);

        mStartingAnimator.addListener(mAnimatorListener);
        mEndingAnimator.addListener(mAnimatorListener);
    }

    private void initHandler() {
        mAnimatorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState) {
                    case STARTING:
                        // 从开始动画转换好搜索动画
                        mCurrentState = State.ENDING;
                        mStartingAnimator.removeAllListeners();
                        break;
                    case ENDING:
                        // 从结束动画转变为无状态
                        mCurrentState = State.NONE;
                        break;
                }
            }
        };
    }

    private void drawShape(Canvas canvas){

        canvas.translate(mViewWidth / 2, mViewHeight / 2);

        switch (mCurrentState) {
            case NONE:
                canvas.drawPath(path, mPaint);
                break;
            case STARTING:
                mMeasure.setPath(path, false);
                Path dst = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength(), dst, true);
                canvas.drawPath(dst, mPaint);
                break;
            case ENDING:
                mMeasure.setPath(path, false);
                Path dst3 = new Path();
                mMeasure.getSegment(mMeasure.getLength() * mAnimatorValue, mMeasure.getLength(), dst3, true);
                canvas.drawPath(dst3, mPaint);
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

}
