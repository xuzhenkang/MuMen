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
import cn.jpush.android.api.JPushInterface;

import com.uesugi.mumen.user.LoginActivity;
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
		// Constants.userCityEntity = UserPreferences.loadCityPref(mContext);
		// mImgVLou = (ImageView) this.findViewById(R.id.sp_imgv_lou);
		// mImgVLou.setLayoutParams(new RelativeLayout.LayoutParams(
		// Constants.width, (int) ((float) Constants.width * 0.46f)));
		statrRun();
	}

	private void statrRun() {

		new Handler().postDelayed(new Runnable() {
			public void run() {
				checkLogin();
				// System.exit(0);
				// Intent i = new Intent(SplashActivity.this,
				// MainActivity.class);
				//
				// SplashActivity.this.startActivity(i); // 启动Main界面
				// SplashActivity.this.finish(); // 关闭自己这个开场屏

			}

		}, 2000); // 2秒，够用了吧
	}

	 @Override
	 protected void onResume() {
	 super.onResume();
	 JPushInterface.onResume(mContext);
	 }
	
	 @Override
	 protected void onPause() {
	 // TODO Auto-generated method stub
	 super.onPause();
	 JPushInterface.onPause(mContext);
	 }

	private void checkLogin() {
		Intent i = new Intent();
		i.setClass(mContext, LoginActivity.class);
		startActivityForResult(i, Constants.REQUEST_USER_LOGIN);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == Constants.REQUEST_USER_LOGIN) {

			if (resultCode == RESULT_CANCELED) {
				Intent i = new Intent(SplashActivity.this, MainActivity.class);
				i.putExtra("flag", true);
				SplashActivity.this.startActivity(i); // 启动Main界面
				SplashActivity.this.finish(); // 关闭自己这个开场屏
			} else if (resultCode == RESULT_OK) {

				Intent i = new Intent(SplashActivity.this, MainActivity.class);

				SplashActivity.this.startActivity(i); // 启动Main界面
				SplashActivity.this.finish(); // 关闭自己这个开场屏

			}

		}
	}
}
