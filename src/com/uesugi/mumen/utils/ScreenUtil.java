package com.uesugi.mumen.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtil {
	public final static String TAG = "ScreenUtil";
	
	private static int mWidth;
	private static int mHeight;
	private static int mViewWidth;
	private static int mViewHeight;
	private static int mDensityDpi;
	private static DisplayMetrics mDm;

	/************************************************
	 * 方法名称 // init
	 * 功能描述 // 获得屏幕分辨�?
	 * 输出参数 // 
	 * 输入参数 // 
	 * 其他说明 //
	 ************************************************/
	public static void init(Context context, DisplayMetrics dm) {
		 mDm = dm;
		 mWidth = dm.widthPixels;
		 mHeight = dm.heightPixels;
		 mDensityDpi = dm.densityDpi;
		 
		 mViewWidth = mWidth;
		 mViewHeight = mHeight - (int)(56 * dm.density);
	}

	/************************************************
	 * 方法名称 // getDm
	 * 功能描述 // 返回对话�?
	 * 输出参数 // 
	 * 输入参数 // 
	 * 其他说明 //
	 ************************************************/
	public static DisplayMetrics getDm() {
		return mDm;
	}
	
	/************************************************
	 * 方法名称 // getDensityDpi
	 * 功能描述 // 获得屏幕密度
	 * 输出参数 // 
	 * 输入参数 // 
	 * 其他说明 //
	 ************************************************/
	public static int getDensityDpi() {
		return mDensityDpi;
	}
	
	/************************************************
	 * 方法名称 // getWidth
	 * 功能描述 // 获取屏幕宽度
	 * 输出参数 // 
	 * 输入参数 // 
	 * 其他说明 //
	 ************************************************/
	public static int getWidth() {
		return mWidth;
	}
	
	/************************************************
	 * 方法名称 // getHeight
	 * 功能描述 // 获取屏幕�?
	 * 输出参数 // 
	 * 输入参数 // 
	 * 其他说明 //
	 ************************************************/
	public static int getHeight() {
		return mHeight;
	}
	
	/************************************************
	 * 方法名称 // getViewWidth
	 * 功能描述 // 获取试图�?
	 * 输出参数 // 
	 * 输入参数 // 
	 * 其他说明 //
	 ************************************************/
	public static int getViewWidth() {
		return mViewWidth;
	}
	
	/************************************************
	 * 方法名称 // getViewHeight
	 * 功能描述 // 获取试图�?
	 * 输出参数 // 
	 * 输入参数 // 
	 * 其他说明 //
	 ************************************************/
	public static int getViewHeight() {
		return mViewHeight;
	}
}

