package com.example.ronny_xie.gdcp.Fragment.WeatherActivity.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * 自定义天气降雨量曲线图
 * Created by HYH on 2017/8/7.
 */

public class WeatherChartView extends View {

    Paint chartLinePaint;//降雨量预报曲线
    Paint chartGradientPaint;//渐变填充

    Paint yLinePaint;//Y轴刻度线

    float gridX, gridY, xSpace = 0, ySpace = 0, spaceYT = 0.005f;//降雨量精度为0.005f
    int xCount=120, yCount=100;//天气预报默认120分钟，降雨量0.000f-0.500f(0.005f*100)

    List<Float> dataY = null;//Y轴值集合

    private int mLineColor=Color.BLUE;

    private int mYLineColor=Color.GRAY;

    public WeatherChartView(Context context) {
        this(context, null);
    }

    public WeatherChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 更新图表
     * @param dataY
     */
    public void refreshView(List<Float> dataY){
        this.dataY = dataY;
        invalidate();
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
        invalidate();
    }

    public void setYLineColor(int yLineColor) {
        this.mYLineColor = yLineColor;
        invalidate();
    }

    private void initPaint() {
        chartLinePaint = new Paint();
        chartGradientPaint = new Paint();

        //绘制的折线
        chartLinePaint.setStyle(Paint.Style.STROKE);

        CornerPathEffect cornerPathEffect = new CornerPathEffect(200);
        chartLinePaint.setPathEffect(cornerPathEffect);
        chartLinePaint.setAntiAlias(true);

        //绘制的渐变
        chartGradientPaint.setStyle(Paint.Style.FILL);
        chartGradientPaint.setAntiAlias(true);

        //Y轴刻度线
        yLinePaint = new Paint();
        yLinePaint.setStyle(Paint.Style.STROKE);
        yLinePaint.setStrokeWidth(1);
    }

    private void setValue(){
        chartLinePaint.setStrokeWidth(5);
        chartLinePaint.setColor(mLineColor);

        yLinePaint.setColor(mYLineColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setValue();

        //基准点。
        gridX = 0;
        gridY = getHeight();

        xSpace = (getWidth() - gridX) / xCount;//X轴刻度
        ySpace = gridY / yCount;//Y轴刻度

        canvas.drawLine(gridX,0,getWidth(),0,yLinePaint);
        canvas.drawLine(gridX,gridY/3,getWidth(),gridY/3,yLinePaint);
        canvas.drawLine(gridX,2*gridY/3,getWidth(),2*gridY/3,yLinePaint);
        canvas.drawLine(gridX,gridY,getWidth(),gridY,yLinePaint);

        if (dataY != null && dataY.size() > 0) {
            float currentPointX;//当前点
            float currentPointY;

            /**
             * 曲线路径
             */
            Path curvePath = new Path();
            /**
             * 渐变色路径
             */
            Path gradientPath = new Path();

            for (int m = 0; m < dataY.size(); m++) {
                currentPointX = m * xSpace + gridX;
                //currentPointY = gridY-30 - ((dataY.get(m)-yStart)*(ySpace/spaceYT));
                currentPointY = gridY- (dataY.get(m)/spaceYT)*ySpace;

                if(m==0){
                    curvePath.moveTo(currentPointX,currentPointY);
                    gradientPath.moveTo(currentPointX,gridY);
                }else {
                    curvePath.lineTo(currentPointX,currentPointY);
                }

                gradientPath.lineTo(currentPointX,currentPointY);

                if(m==dataY.size()-1){
                    gradientPath.lineTo(currentPointX,gridY);
                }
            }

            //画曲线
            canvas.drawPath(curvePath, chartLinePaint);

            //画渐变
            Shader mShader = new LinearGradient(0, 0, 0, gridY, new int[]{mLineColor,
                    Color.argb(100, 255, 255, 255)}, null, Shader.TileMode.REPEAT);
            chartGradientPaint.setShader(mShader);
            canvas.drawPath(gradientPath,chartGradientPaint);

        }
    }
}
