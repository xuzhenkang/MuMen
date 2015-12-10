package com.uesugi.mumen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.uesugi.mumen.MyWebView.onTouchEventClickListener;

public class NewsInfoActivity extends Activity {

	private final static String TAG = "NewsInfoActivity";

	private Context mContext = null;
	private ProgressBar mProgressBar = null;

	private MyWebView webview = null;

	private FrameLayout video_fullView;// 全屏时视频加载view
	private View xCustomView;
	private CustomViewCallback xCustomViewCallback;
	private myWebChromeClient xwebchromeclient;

	private String mAid = null;

	private boolean mShowFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_info);

		mContext = this;

		Intent i = getIntent();
		mAid = i.getStringExtra("url");

		initView();

		webStart2();

	}

	private void initView() {
		video_fullView = (FrameLayout) findViewById(R.id.video_fullView);
		mProgressBar = (ProgressBar) findViewById(R.id.news_info_probar);

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void webStart2() {
		webview = (MyWebView) findViewById(R.id.news_info_view_webview);
		webview.setOnTouchEventClickListener(mOnTouchEventClickListener);
		webview.setBackgroundColor(Color.parseColor("#00ffffff"));
		// 设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDatabaseEnabled(true);
		String htmlDb = this.getApplicationContext()
				.getDir("database", Context.MODE_PRIVATE).getPath();
		webview.getSettings().setDatabasePath(htmlDb);
		webview.getSettings().setDomStorageEnabled(true);
		webview.getSettings().setAppCacheMaxSize(1024 * 1024 * 1024);// 缓存最多可以有8M
		webview.getSettings().setAllowFileAccess(true);// 可以读取文件缓存(manifest生效)
		webview.getSettings().setAppCacheEnabled(true);// 应用可以有缓存
		webview.getSettings().setSavePassword(true);
		webview.getSettings().setSaveFormData(true);// 保存表单数据
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setGeolocationEnabled(true);// 启用地理定位
		webview.getSettings().setGeolocationDatabasePath(
				"/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
		webview.getSettings().setDomStorageEnabled(true);
		webview.getSettings().setSupportMultipleWindows(true);// 新加
		webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		xwebchromeclient = new myWebChromeClient();
		webview.setWebChromeClient(xwebchromeclient);
		// 设置Web视图
		webview.setWebViewClient(new HelloWebViewClient());
		// webview.loadUrl("http://115.28.137.139:89/Api/Article/view/id/132.html");
		webview.loadUrl(mAid);

	}

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.e("url", url + mAid);
			// view.loadUrl(url);
			if (url.contains("http://comment/")) {

			} else {
				view.loadUrl(url);
			}

			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			mProgressBar.setVisibility(View.GONE);
			webview.setVisibility(View.VISIBLE);
			Log.e("yes", "js yes");
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK: {
			if (inCustomView()) {
				// webViewDetails.loadUrl("about:blank");
				hideCustomView();
				return true;
			} else if (mShowFlag) {
				mShowFlag = false;
				return true;
			} else {
				goBack();
			}
		}
		}

		return super.onKeyDown(keyCode, event);

	}

	/**
	 * MyWebView左右监听事件
	 */
	private onTouchEventClickListener mOnTouchEventClickListener = new onTouchEventClickListener() {

		@Override
		public void onLeft() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRight() {
			// TODO Auto-generated method stub
			goBack();
		}

	};

	public void goBack() {
		finish();
	}

	// private WebChromeClient m_chromeClient = new WebChromeClient() {
	// // 扩充缓存的容量
	// @Override
	// public void onReachedMaxAppCacheSize(long spaceNeeded,
	// long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
	// quotaUpdater.updateQuota(spaceNeeded * 2);
	// }
	// };

	public class myWebChromeClient extends WebChromeClient {
		private View xprogressvideo;

		// 播放网络视频时全屏会被调用的方法
		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			webview.setVisibility(View.INVISIBLE);
			// 如果一个视图已经存在，那么立刻终止并新建一个
			if (xCustomView != null) {
				callback.onCustomViewHidden();
				return;
			}
			video_fullView.addView(view);
			xCustomView = view;
			xCustomViewCallback = callback;
			video_fullView.setVisibility(View.VISIBLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

		// 视频播放退出全屏会被调用的
		@Override
		public void onHideCustomView() {
			if (xCustomView == null)// 不是全屏播放状态
				return;

			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			xCustomView.setVisibility(View.GONE);
			video_fullView.removeView(xCustomView);
			xCustomView = null;
			video_fullView.setVisibility(View.GONE);
			xCustomViewCallback.onCustomViewHidden();
			webview.setVisibility(View.VISIBLE);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

		// 视频加载时进程loading
		@Override
		public View getVideoLoadingProgressView() {
			if (xprogressvideo == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				xprogressvideo = inflater.inflate(
						R.layout.video_loading_progress, null);
			}
			return xprogressvideo;
		}
	}

	/**
	 * 判断是否是全屏
	 * 
	 * @return
	 */
	public boolean inCustomView() {
		return (xCustomView != null);
	}

	/**
	 * 全屏时按返加键执行退出全屏方法
	 */
	public void hideCustomView() {
		xwebchromeclient.onHideCustomView();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		super.onResume();
		super.onResume();
		webview.onResume();
		webview.resumeTimers();

		/**
		 * 设置为横屏
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPause() {
		super.onPause();
		webview.onPause();
		webview.pauseTimers();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		super.onDestroy();
		video_fullView.removeAllViews();
		webview.loadUrl("about:blank");
		webview.stopLoading();
		webview.setWebChromeClient(null);
		webview.setWebViewClient(null);
		webview.destroy();
		webview = null;
	}

}
