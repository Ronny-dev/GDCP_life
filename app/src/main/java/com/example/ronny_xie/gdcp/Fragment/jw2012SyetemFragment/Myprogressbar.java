package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class Myprogressbar extends ProgressBar {
	private String text_progress;
	private Paint mPaint;// 画笔

	public Myprogressbar(Context context) {
		super(context);
		initPaint();
	}

	public Myprogressbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	public Myprogressbar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}

	@Override
	public synchronized void setProgress(int progress) {
		super.setProgress(progress);
		setTextProgress(progress);
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Rect rect = new Rect();
		this.mPaint.getTextBounds(this.text_progress, 0,
				this.text_progress.length(), rect);
		this.mPaint.setTextSize(22f);
		int x = (getWidth() / 2) - rect.centerX();
		int y = (getHeight() / 2) - rect.centerY();
		canvas.drawText(this.text_progress, x, y, this.mPaint);
	}

	private void initPaint() {
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);
		this.mPaint.setColor(Color.WHITE);
	}

	private void setTextProgress(int progress) {
		int i = (int) ((progress * 1.0f / this.getMax()) * 100);
		this.text_progress = String.valueOf(i) + "分";
	}
}
