package com.uesugi.mumen;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyViewPager extends ViewPager {

	public MyViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	PointF downPoint = new PointF();
	OnSingleTouchListener onSingleTouchListener;

	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		switch (evt.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 记录按下时候的坐标
			downPoint.x = evt.getX();
			downPoint.y = evt.getY();
//			if (this.getChildCount() > 1) { // 有内容，多于1个时
//				// 通知其父控件，现在进行的是本控件的操作，不允许拦截
//				getParent().requestDisallowInterceptTouchEvent(true);
//			}
			break;
		case MotionEvent.ACTION_MOVE:
			float currentX = evt.getX();
			long currentTime = evt.getEventTime();
			float difference = Math.abs(downPoint.x - currentX);

			if (this.getChildCount() > 1) {

				/** X轴滑动距离大于100，并且时间小于220ms,并且向X轴右方向滑动 && (time < 220) */
				if (difference > 20) {
					// 有内容，多于1个时
					// 通知其父控件，现在进行的是本控件的操作，不允许拦截
					getParent().requestDisallowInterceptTouchEvent(true);
				}

			}
			break;
		case MotionEvent.ACTION_UP:
			// 在up时判断是否按下和松手的坐标为一个点
			if (PointF.length(evt.getX() - downPoint.x, evt.getY()
					- downPoint.y) < (float) 10.0) {
				onSingleTouch(this);
				return true;
			}
			break;
		}
		return super.onTouchEvent(evt);
	}

	public void onSingleTouch(View v) {
		if (onSingleTouchListener != null) {
			onSingleTouchListener.onSingleTouch(v);

		}
	}

	public interface OnSingleTouchListener {
		public void onSingleTouch(View v);
	}

	public void setOnSingleTouchListener(
			OnSingleTouchListener onSingleTouchListener) {
		this.onSingleTouchListener = onSingleTouchListener;
	}
}
