package com.uesugi.mumen;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.uesugi.mumen.expand.ExpandActivity;
import com.uesugi.mumen.promotion.PromotionActivity;
import com.uesugi.mumen.sales.SalesActivity;
import com.uesugi.mumen.top.TopActivity;
import com.uesugi.mumen.user.UserActivity;
import com.uesugi.mumen.utils.Constants;
import com.uesugi.mumen.utils.FileUtils;
import com.uesugi.mumen.utils.ShowAlertDialog;

/**
 * TabActivity首页 应用核心！
 * 
 * @author whtt
 * 
 */

//
// 佛祖对不起！
//
public class MainActivity extends TabActivity implements OnClickListener {

	private Context mContext = null;

	public static String TAG_TBA1 = "tab1";
	public static String TAG_TBA2 = "tab2";
	public static String TAG_TBA3 = "tab3";
	public static String TAG_TBA4 = "tab4";
	public static String TAG_TBA5 = "tab5";
	public static TabHost mTabHost;
	private ImageView mImgVTab1 = null;
	private ImageView mImgVTab2 = null;
	private ImageView mImgVTab3 = null;
	private ImageView mImgVTab4 = null;
	private ImageView mImgVTab5 = null;
	private RelativeLayout mLayoutTab1 = null;
	private RelativeLayout mLayoutTab2 = null;
	private RelativeLayout mLayoutTab3 = null;
	private RelativeLayout mLayoutTab4 = null;
	private RelativeLayout mLayoutTab5 = null;
	private Intent mIntentPromotion = null; // 首页
	private Intent mIntentExpand = null; // 商户
	private Intent mIntentSales = null; // 购物
	private Intent mIntentTop = null;// 周边
	private Intent mIntentUser = null;// 我的
	private String mUdId = null;

	private ShowAlertDialog mDialog = null;

	private int mCurTabId = R.id.main_layout_tab1;

	private boolean mFlagJPush = false;
	private String mPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mContext = this;
		
		FileUtils.createParentPath(Constants.IMAGE_CACHE_PATH);
		FileUtils.createParentPath(Constants.IMAGE_EDIT_PATH);

		// mFlagJPush = getIntent().getBooleanExtra(Constants.INTENT_LOGIN_PUSH,
		// false);
		// if (mFlagJPush) {
		// mPage = getIntent()
		// .getStringExtra(Constants.INTENT_LOGIN_PUSH_PAGE);
		// }

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// 获取分辨率
		Constants.width = dm.widthPixels;
		Constants.width5_1 = dm.widthPixels / 5;
		// Constants.userCityEntity = UserPreferences.loadCityPref(mContext);
		initView();
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mCurTabId == v.getId()) {
			return;
		}

		// if (Constants.entityUser == null && v.getId() ==
		// R.id.main_layout_tab4) {
		// // TODO
		// Intent i = new Intent();
		// i.setClass(mContext, LoginActivity.class);
		// startActivityForResult(i, Constants.REQUEST_USER_LOGIN);
		// return;
		// }

		int checkedId = v.getId();

		switch (checkedId) {
		case R.id.main_layout_tab1: {

			setCurrentTabByTag(TAG_TBA1);

			mImgVTab1.setSelected(true);
			mImgVTab2.setSelected(false);
			mImgVTab3.setSelected(false);
			mImgVTab4.setSelected(false);
			mImgVTab5.setSelected(false);
			break;
		}
		case R.id.main_layout_tab2: {

			setCurrentTabByTag(TAG_TBA2);
			mImgVTab2.setSelected(true);
			mImgVTab1.setSelected(false);
			mImgVTab3.setSelected(false);
			mImgVTab4.setSelected(false);
			mImgVTab5.setSelected(false);
			break;
		}

		case R.id.main_layout_tab3: {

			setCurrentTabByTag(TAG_TBA3);
			mImgVTab3.setSelected(true);
			mImgVTab1.setSelected(false);
			mImgVTab2.setSelected(false);
			mImgVTab4.setSelected(false);
			mImgVTab5.setSelected(false);
			break;
		}
		case R.id.main_layout_tab4: {

			setCurrentTabByTag(TAG_TBA4);
			mImgVTab4.setSelected(true);
			mImgVTab1.setSelected(false);
			mImgVTab2.setSelected(false);
			mImgVTab3.setSelected(false);
			mImgVTab5.setSelected(false);
			break;

		}
		case R.id.main_layout_tab5: {
			setCurrentTabByTag(TAG_TBA5);
			mImgVTab5.setSelected(true);
			mImgVTab1.setSelected(false);
			mImgVTab2.setSelected(false);
			mImgVTab3.setSelected(false);
			mImgVTab4.setSelected(false);
			break;
		}
		}

		mCurTabId = checkedId;
	}

	private TabHost.TabSpec buildTabSpec(String tag, String resLabel,
			int resIcon, final Intent content) {
		return mTabHost.newTabSpec(tag)
				.setIndicator(resLabel, getResources().getDrawable(resIcon))
				.setContent(content);
	}

	public static void setCurrentTabByTag(String tab) {
		mTabHost.setCurrentTabByTag(tab);
	}

	private void initView() {

		mDialog = new ShowAlertDialog(mContext);

		mImgVTab1 = (ImageView) findViewById(R.id.main_imgv_tab1);
		mImgVTab2 = (ImageView) findViewById(R.id.main_imgv_tab2);
		mImgVTab3 = (ImageView) findViewById(R.id.main_imgv_tab3);
		mImgVTab4 = (ImageView) findViewById(R.id.main_imgv_tab4);
		mImgVTab5 = (ImageView) findViewById(R.id.main_imgv_tab5);
		mLayoutTab1 = (RelativeLayout) findViewById(R.id.main_layout_tab1);
		mLayoutTab2 = (RelativeLayout) findViewById(R.id.main_layout_tab2);
		mLayoutTab3 = (RelativeLayout) findViewById(R.id.main_layout_tab3);
		mLayoutTab4 = (RelativeLayout) findViewById(R.id.main_layout_tab4);
		mLayoutTab5 = (RelativeLayout) findViewById(R.id.main_layout_tab5);

		// mIntentHome = new Intent(mContext, HomeActivity.class);
		// mIntentShop = new Intent(mContext, Home1Activity.class);
		// mIntentShoping = new Intent(mContext, Home1Activity.class);
		// mIntentCoupon = new Intent(mContext, Home1Activity.class);
		// mIntentUser = new Intent(mContext, Home1Activity.class);
		mIntentPromotion = new Intent(mContext, PromotionActivity.class);
		mIntentExpand = new Intent(mContext, ExpandActivity.class);
		mIntentSales = new Intent(mContext, SalesActivity.class);
		mIntentTop = new Intent(mContext, TopActivity.class);
		mIntentUser = new Intent(mContext, UserActivity.class);

		mTabHost = getTabHost();

		mTabHost.addTab(buildTabSpec(TAG_TBA1, "", R.drawable.tabbar_tab1,
				mIntentPromotion));
		mTabHost.addTab(buildTabSpec(TAG_TBA2, "", R.drawable.tabbar_tab2,
				mIntentExpand));
		mTabHost.addTab(buildTabSpec(TAG_TBA3, "", R.drawable.tabbar_tab3,
				mIntentSales));
		mTabHost.addTab(buildTabSpec(TAG_TBA4, "", R.drawable.tabbar_tab4,
				mIntentTop));
		mTabHost.addTab(buildTabSpec(TAG_TBA5, "", R.drawable.tabbar_tab5,
				mIntentUser));
		setCurrentTabByTag(TAG_TBA1);

		mImgVTab1.setSelected(true);

		mLayoutTab1.setOnClickListener(this);
		mLayoutTab2.setOnClickListener(this);
		mLayoutTab3.setOnClickListener(this);
		mLayoutTab4.setOnClickListener(this);
		mLayoutTab5.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// JPushInterface.onResume(mContext);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// JPushInterface.onPause(mContext);
	}

	// public void doJPush(String name) {
	//
	// if (name.equals("msg")) {
	//
	// if (Constants.entityUser == null) {
	// return;
	// }
	// Intent i = new Intent();
	// i.setClass(mContext, MsgActivity.class);
	// startActivityForResult(i, Constants.REQUEST_MSG);
	// } else {
	// Intent i = new Intent();
	// i.setClass(mContext, MySystemActivity.class);
	// startActivity(i);
	// }
	// }

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (resultCode == RESULT_OK) {
	// if (requestCode == Constants.REQUEST_USER_LOGIN) {
	// if (Constants.ma != null) {
	// Constants.ma.initUser();
	// }
	// Intent i = new Intent();
	// i.setClass(mContext, MsgActivity.class);
	// startActivityForResult(i, Constants.REQUEST_MSG);
	//
	// } else if (requestCode == Constants.REQUEST_MSG) {
	// if (Constants.ma != null) {
	// Constants.ma.initUser();
	// }
	//
	// rLogin();
	// }
	//
	// }
	// }
	//
	// public void rLogin() {
	//
	// Intent i = new Intent();
	// i.setClass(mContext, LoginActivity.class);
	// startActivityForResult(i, Constants.REQUEST_USER_LOGIN);
	// }

	// private void initImagePath() {
	// try {
	//
	// File file = new File(Constants.APP_BASE_PATH + "/");
	// if (!file.exists()) {
	// file.mkdirs();
	//
	// }
	// File file2 = new File(Constants.APP_BASE_PATH, "/pic_love_city.jpg");
	// Bitmap pic = BitmapFactory.decodeResource(getResources(),
	// R.drawable.logo);
	// FileOutputStream fos = new FileOutputStream(file2);
	// pic.compress(CompressFormat.JPEG, 100, fos);
	// fos.flush();
	// fos.close();
	//
	// } catch (Throwable t) {
	// t.printStackTrace();
	// Constants.TEST_IMAGE = "";
	// }
	//
	// }

}
