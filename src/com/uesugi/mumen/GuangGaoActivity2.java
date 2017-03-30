package com.uesugi.mumen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.uesugi.mumen.utils.ShowAlertDialog;

public class GuangGaoActivity2 extends Activity {
	private final static String TAG = "GuangGaoActivity";

	private WebView webview = null;
	private FrameLayout video_fullView;// 全屏时视频加载view
	private View xCustomView;
	private CustomViewCallback xCustomViewCallback;
	private myWebChromeClient xwebchromeclient;

	private Context mContext = null;

	private TextView mTxtTopTitle = null;
	private ImageButton mBtnTopRight = null;
	private ImageButton mBtnTopLeft = null;

	private ShowAlertDialog mDialog = null;

	private ProgressDialog mProgressDlg = null;

	private String mName = null;
	private String mUrl = null;
	private String mAid = null;
	private String mHtml = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_gg);
		mName = getIntent().getStringExtra("title");
		mUrl = getIntent().getStringExtra("url");
		mContext = this;

		Log.e("mUrl", mUrl);
		initView();
		showProgressDlg("正在加载中...");
//		getYouku();
	}

	private void initView() {
		mDialog = new ShowAlertDialog(mContext);

		mTxtTopTitle = (TextView) findViewById(R.id.top_view_title);
		mTxtTopTitle.setText(mName);
		mTxtTopTitle.setTextColor(getResources().getColor(R.color.title_txt));

		mBtnTopLeft = (ImageButton) findViewById(R.id.top_view_btn_left);
		mBtnTopLeft.setVisibility(View.VISIBLE);
		mBtnTopLeft.setOnClickListener(mOnClickListener);

		video_fullView = (FrameLayout) findViewById(R.id.video_fullView);

		webview = (WebView) findViewById(R.id.gg_webview);
		// 设置WebView属性，能够执行Javascript脚本
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new HelloWebViewClient());
	}

	private View.OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.top_view_btn_left: {
				finish();
				break;
			}

			}
		}
	};

	private void webStart() {

		webview.loadDataWithBaseURL("http://youku.com", mHtml, "", "utf-8",
				null);

	}

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.e("url", url);

			view.loadUrl(url);

			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			// mLayoutMain.setBackgroundResource(R.color.black);
			// webview.loadUrl("javascript:setting(0, 2)");
			dismissProgressDlg();
			Log.e("yes", "js yes");
		}

	}

	public void showProgressDlg(String msg) {
		if (mProgressDlg != null) {
			mProgressDlg.dismiss();
			mProgressDlg = null;
		}

		mProgressDlg = new ProgressDialog(mContext);
		mProgressDlg.setMessage(msg);
		mProgressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDlg.setIndeterminate(false);
		mProgressDlg.setCancelable(true);
		mProgressDlg.setCanceledOnTouchOutside(false);
		mProgressDlg.show();
	}

	public void dismissProgressDlg() {
		if (mProgressDlg != null) {
			mProgressDlg.dismiss();
			mProgressDlg = null;
		}
	}

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



	@Override
	protected void onResume() {
		super.onResume();
		super.onResume();
		webview.onResume();
		webview.resumeTimers();
	}

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