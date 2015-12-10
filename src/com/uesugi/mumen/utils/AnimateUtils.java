package com.uesugi.mumen.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;


public class AnimateUtils {

	public static void animateClickView(final View view ,final ClickAnimation callBack){
		
		Animation animation = new ScaleAnimation(1.0f,0.9f,1.0f,0.9f,50,50);
		animation.setFillAfter(false);
		animation.setDuration(100);
		animation.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				
				callBack.onClick(view);
				
			}
		});
		
		view.startAnimation(animation);
	}
	
	
	public interface ClickAnimation{
		public void onClick(View v);
	}
}
