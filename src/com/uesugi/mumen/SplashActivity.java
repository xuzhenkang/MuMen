package com.uesugi.mumen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.uesugi.mumen.utils.Constants;

public class SplashActivity extends Activity {
	private final static String TAG = "SplashActivity";

	private Context mContext = null;
	private ImageView mImgVLou;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		mContext = this;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// 获取分辨率
		Constants.width = dm.widthPixels;
		Constants.width5_1 = dm.widthPixels / 5;
//		Constants.userCityEntity = UserPreferences.loadCityPref(mContext);
//		mImgVLou = (ImageView) this.findViewById(R.id.sp_imgv_lou);
//		mImgVLou.setLayoutParams(new RelativeLayout.LayoutParams(
//				Constants.width, (int) ((float) Constants.width * 0.46f)));
		statrRun();
	}

	private void statrRun() {

		new Handler().postDelayed(new Runnable() {
			public void run() {

				Intent i = new Intent(SplashActivity.this, MainActivity.class);

				SplashActivity.this.startActivity(i); // 启动Main界面
				SplashActivity.this.finish(); // 关闭自己这个开场屏

			}

		}, 2000); // 2秒，够用了吧
	}

//	@Override
//	protected void onResume() {
//		super.onResume();
//		JPushInterface.onResume(mContext);
//	}
//
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		JPushInterface.onPause(mContext);
//	}
}
