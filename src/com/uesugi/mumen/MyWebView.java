package com.uesugi.mumen;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.ViewFlipper;

import com.uesugi.mumen.utils.Constants;

public class MyWebView extends WebView {
	float downXValue;
	long downTime;
	private NewsInfoActivity mBaseActivity = null;
	private static ViewFlipper flipper;
	private float lastTouchX, lastTouchY;
	private boolean hasMoved = false;

	private onTouchEventClickListener onClickListener = null;

	public MyWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setActivity(NewsInfoActivity activity) {
		mBaseActivity = activity;
	}

	public interface onTouchEventClickListener {
		public void onLeft();

		public void onRight();
	}

	public void setOnTouchEventClickListener(
			onTouchEventClickListener listener) {
		onClickListener = listener;
	}

	public boolean onTouchEvent(MotionEvent evt) {

		boolean consumed = super.onTouchEvent(evt);
		if (isClickable()) {
			switch (evt.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d("lxy", "browser ---> down envent");
				lastTouchX = evt.getX();
				lastTouchY = evt.getY();
				downXValue = evt.getX();
				downTime = evt.getEventTime();
				hasMoved = false;
				break;
			case MotionEvent.ACTION_MOVE:
				hasMoved = moved(evt);
				break;
			case MotionEvent.ACTION_UP:
				float currentX = evt.getX();
				long currentTime = evt.getEventTime();
				float difference = Math.abs(downXValue - currentX);
				long time = currentTime - downTime;

				Log.i("Touch Event:", "Distance: " + difference + "px Time: "
						+ time + "ms");
				/** X轴滑动距离大于100，并且时间小于220ms,并且向X轴右方向滑动 && (time < 220) */
				if ((downXValue < currentX)
						&& (difference > (Constants.width/3) && (time < 220))) {
					/** 跳到上一页 */
					Log.e("wwww", "right");
					if (onClickListener != null) {
						onClickListener.onRight();
					}
				}
				/** X轴滑动距离大于100，并且时间小于220ms,并且向X轴左方向滑动 */
				if ((downXValue > currentX) && (difference > (Constants.width/3))
						&& (time < 220)) {
					/** 跳到下一页 */
					Log.e("wwww", "left");
					// flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					// R.layout.right_in));
					// flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					// R.layout.right_out));
					// flipper.showPrevious();
					if (onClickListener != null) {
						onClickListener.onLeft();
					}
				}

				break;
			}
		}
		return consumed || isClickable();
	}

	private boolean moved(MotionEvent evt) {
		return hasMoved || Math.abs(evt.getX() - lastTouchX) > 10.0
				|| Math.abs(evt.getY() - lastTouchY) > 10.0;
	}
}
