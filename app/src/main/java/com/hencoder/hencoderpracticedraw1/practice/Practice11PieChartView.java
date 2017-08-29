package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class Practice11PieChartView extends View {

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        int left = 200;
        int top = 100;
        int right = 700;
        int bottom = 600;
        RectF rect = new RectF(left,top,right,bottom);


        //画紫色部分
        paint.setColor(Color.parseColor("#8E388E"));
        canvas.drawArc(rect,0,15,true,paint);

        //画灰色部分
        paint.setColor(Color.GRAY);
        canvas.drawArc(rect,16,12,true,paint);

        //画绿色部分
        paint.setColor(Color.GREEN);
        canvas.drawArc(rect,30,50,true,paint);

        //画蓝色部分
        paint.setColor(Color.BLUE);
        canvas.drawArc(rect,82,98,true,paint);

        //画黄色部分
        paint.setColor(Color.YELLOW);
        canvas.drawArc(rect,300,58,true,paint);
        //画红色部分
        canvas.translate(-20,-20);
        paint.setColor(Color.RED);
        canvas.drawArc(rect,182,116,true,paint);

        //画圆弧中心点
        canvas.translate(20,20);
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(10f);
        canvas.drawPoint(450,350,paint);
        canvas.drawPoint((float) getArcMiddlePoint(15/2)[0],(float)getArcMiddlePoint(15/2)[1],paint);
        canvas.drawPoint((float) getArcMiddlePoint(16+12/2)[0],(float)getArcMiddlePoint(16+12/2)[1],paint);
        canvas.drawPoint((float) getArcMiddlePoint(30+50/2)[0],(float)getArcMiddlePoint(30+50/2)[1],paint);
        canvas.drawPoint((float) getArcMiddlePoint(82+98/2)[0],(float)getArcMiddlePoint(82+98/2)[1],paint);
        canvas.drawPoint((float) getArcMiddlePoint(300+58/2)[0],(float)getArcMiddlePoint(300+58/2)[1],paint);
        canvas.translate(-20,-20);
        canvas.drawPoint((float) getArcMiddlePoint(182+116/2)[0],(float)getArcMiddlePoint(182+116/2)[1],paint);

        //画线和文字(懒，意思到了就行)
        canvas.translate(20,20);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2f);
        paint.setTextSize(30f);
        canvas.drawLine((float) getArcMiddlePoint(15/2)[0],(float)getArcMiddlePoint(15/2)[1],
                (float) getArcMiddlePoint(15/2)[0] + 50,(float)getArcMiddlePoint(15/2)[1],paint);
        canvas.drawText("Froyo",(float) getArcMiddlePoint(15/2)[0] + 50,(float)getArcMiddlePoint(15/2)[1],paint);

    }

    private double[] getArcMiddlePoint(double degree){
        double[] points = new double[2];
        int radius = 500/2;
        points[0] = 450 + radius*Math.cos(Math.toRadians(degree));
        points[1] = 350 + radius*Math.sin(Math.toRadians(degree));
        return points;
    }
}
