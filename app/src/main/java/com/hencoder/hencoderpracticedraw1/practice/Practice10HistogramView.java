package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {

    private String[] titles = {"Froyo","GB","ICS","JB","KitKat","L","M"};

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(30f);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        //边线
        path.moveTo(getWidth()/10,getHeight()/15);
        path.lineTo(getWidth()/10,getHeight()*3/4);
        path.lineTo(getWidth()*9/10,getHeight()*3/4);
        canvas.drawPath(path,paint);
        //画文字
        for (int i = 0; i < titles.length; i++) {
            canvas.drawText(titles[i],getWidth()/10 + 50 + 130*i,getHeight()*3/4+30,paint);
        }
        paint.setTextSize(50f);
        canvas.drawText("直方图",getWidth()/2,getHeight()*9/10,paint);
        //画柱状图
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < titles.length; i++) {
            canvas.drawRect(getWidth()/10 + 130*i,getHeight()*3/4 - 20 - 70*i,getWidth()/10 + 100 +  130*i,
                    getHeight()*3/4,paint);
        }


//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
    }
}
