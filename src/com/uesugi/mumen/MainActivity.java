package com.uesugi.mumen;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.uesugi.mumen.entity.BaseEntity;
import com.uesugi.mumen.entity.DianEntity;
import com.uesugi.mumen.entity.MsgDianListEntity;
import com.uesugi.mumen.expand.ExpandActivity;
import com.uesugi.mumen.promotion.PromotionActivity;
import com.uesugi.mumen.sales.SalesActivity;
import com.uesugi.mumen.top.TopActivity;
import com.uesugi.mumen.user.UserActivity;
import com.uesugi.mumen.utils.Constants;
import com.uesugi.mumen.utils.FileUtils;
import com.uesugi.mumen.utils.RemoteUtils;
import com.uesugi.mumen.utils.ShowAlertDialog;
import com.uesugi.mumen.utils.StringUtils;
import com.uesugi.mumen.utils.UserPreferences;
import com.uesugi.mumen.utils.WHTTHttpRequestCallBack;

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

	private ImageView mImgVDian1 = null;
	private ImageView mImgVDian2 = null;
	private ImageView mImgVDian3 = null;
	private ImageView mImgVDian5 = null;

	private EditText mEditName;
	private RelativeLayout mLayoutNameShow;
	private RelativeLayout mLayoutNameBg;
	private TextView mTextNameCancel;
	private TextView mBtnNameOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Constants.mainActivity = this;
		mContext = this;
		boolean exitFlag = getIntent().getBooleanExtra("flag", false);
		if (exitFlag) {
			System.exit(0);
		}

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
		Constants.height = dm.heightPixels;
		Constants.width5_1 = dm.widthPixels / 5;
		// Constants.userCityEntity = UserPreferences.loadCityPref(mContext);
		initView();
		String lastTime = UserPreferences.loadTimePref(mContext,
				Constants.entityUser.id);
		if (lastTime == null) {

			long tsLong = System.currentTimeMillis() / 1000;
			String ts = tsLong + "";
			UserPreferences.saveTimePref(mContext, ts, Constants.entityUser.id);
		} else {
			getDianList(lastTime);
		}
		String msgTime = UserPreferences.loadMsgTimePref(mContext,
				Constants.entityUser.id);
		if (msgTime == null) {

			getMsg("");
		} else {
			getMsg("&lasttime=" + msgTime);

		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Constants.mainActivity = null;
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
			mImgVDian1.setVisibility(View.GONE);
			mImgVTab1.setSelected(true);
			mImgVTab2.setSelected(false);
			mImgVTab3.setSelected(false);
			mImgVTab4.setSelected(false);
			mImgVTab5.setSelected(false);
			break;
		}
		case R.id.main_layout_tab2: {

			setCurrentTabByTag(TAG_TBA2);
			mImgVDian2.setVisibility(View.GONE);
			mImgVTab2.setSelected(true);
			mImgVTab1.setSelected(false);
			mImgVTab3.setSelected(false);
			mImgVTab4.setSelected(false);
			mImgVTab5.setSelected(false);
			break;
		}

		case R.id.main_layout_tab3: {

			setCurrentTabByTag(TAG_TBA3);
			mImgVDian3.setVisibility(View.GONE);
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
		mEditName = (EditText) findViewById(R.id.promotion_et_name);
		mLayoutNameShow = (RelativeLayout) findViewById(R.id.promotion_ly_name_show);
		mLayoutNameShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		mLayoutNameBg = (RelativeLayout) findViewById(R.id.promotion_ly_name_bg);
		mLayoutNameBg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLayoutNameBg.setVisibility(View.GONE);
			}
		});
		mTextNameCancel = (TextView) findViewById(R.id.promotion_btn_cancel);
		mTextNameCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLayoutNameBg.setVisibility(View.GONE);
			}
		});
		mBtnNameOk = (TextView) findViewById(R.id.promotion_btn_ok);
		mBtnNameOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mail = mEditName.getText().toString();

				if (StringUtils.isBlank(mail)) {
					Toast.makeText(mContext, "请输入您的邮箱地址!", Toast.LENGTH_SHORT).show();
					return;
				}

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mEditName.getWindowToken(), 0);
				sendEmail(mail);
			}
		});

		mDialog = new ShowAlertDialog(mContext);

		mImgVTab1 = (ImageView) findViewById(R.id.main_imgv_tab1);
		mImgVTab2 = (ImageView) findViewById(R.id.main_imgv_tab2);
		mImgVTab3 = (ImageView) findViewById(R.id.main_imgv_tab3);
		mImgVTab4 = (ImageView) findViewById(R.id.main_imgv_tab4);
		mImgVTab5 = (ImageView) findViewById(R.id.main_imgv_tab5);

		mImgVDian1 = (ImageView) findViewById(R.id.main_imgv_dian1);
		mImgVDian2 = (ImageView) findViewById(R.id.main_imgv_dian2);
		mImgVDian3 = (ImageView) findViewById(R.id.main_imgv_dian3);
		mImgVDian5 = (ImageView) findViewById(R.id.main_imgv_dian5);

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

	public void closeDian(String index) {
		if (index.equals("1")) {
			mImgVDian1.setVisibility(View.GONE);
		} else if (index.equals("2")) {
			mImgVDian2.setVisibility(View.GONE);
		} else if (index.equals("3")) {
			mImgVDian3.setVisibility(View.GONE);
		} else if (index.equals("5")) {
			mImgVDian5.setVisibility(View.GONE);
		}
	}

	public void getDianList(String lasttime) {

		RemoteUtils.getDian(lasttime, new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				DianEntity entity = (DianEntity) obj;

				if (!entity.success) {

				} else {
					if (entity.column1.equals("0")) {
						mImgVDian1.setVisibility(View.GONE);
					} else {
						mImgVDian1.setVisibility(View.VISIBLE);
					}
					if (entity.column2.equals("0")) {
						mImgVDian2.setVisibility(View.GONE);
					} else {
						mImgVDian2.setVisibility(View.VISIBLE);
					}
					if (entity.column3.equals("0")) {
						mImgVDian3.setVisibility(View.GONE);
					} else {
						mImgVDian3.setVisibility(View.VISIBLE);
					}
				}

			}
		});
	}

	public void getMsg(String lasttime) {

		RemoteUtils.getMsg(lasttime, "1", "20", new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				MsgDianListEntity entity = (MsgDianListEntity) obj;

				if (!entity.success) {

				} else {
					long tsLong = System.currentTimeMillis() / 1000;
					String ts = tsLong + "";
					UserPreferences.saveMsgTimePref(mContext, ts,
							Constants.entityUser.id);
					if (entity.list.size() > 0) {
						mImgVDian5.setVisibility(View.VISIBLE);
					} else {
						mImgVDian5.setVisibility(View.GONE);
					}

				}

			}
		});
	}

	public boolean getDian5Show() {
		if (mImgVDian5.isShown()) {
			return true;
		} else {
			return false;
		}

	}

	public void setDian5Show() {
		mImgVDian5.setVisibility(View.VISIBLE);

	}

	public void showMsgDian() {
		mImgVDian5.setVisibility(View.VISIBLE);
		if (Constants.userActivity != null) {
			Constants.userActivity.showMsgDian();
		}
	}

	public void closeMsgDian() {
		mImgVDian5.setVisibility(View.GONE);
		if (Constants.userActivity != null) {
			Constants.userActivity.closeMsgDian();
		}
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == Constants.REQUEST_USER_LOGIN) {

			if (resultCode == RESULT_CANCELED) {
				System.exit(0);
			} else if (resultCode == RESULT_OK) {

				System.exit(0);

			}

		}
	}

	public void logout() {
		Intent intent = new Intent();
		intent.setClass(mContext, SplashActivity.class);
		startActivity(intent);
		finish();

	}

	private String mMailId = "";

	public void showMailEdit(String id) {
		mMailId = id;
		mEditName.setText("");
		mLayoutNameBg.setVisibility(View.VISIBLE);
	}

	public void sendEmail(String email) {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.sendEmail(mMailId, email, new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				BaseEntity entity = (BaseEntity) obj;
				mDialog.dismissProgressDlg();
				if (!entity.success) {
					Toast.makeText(mContext, entity.msg, Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(mContext, "提交成功！", Toast.LENGTH_SHORT)
					.show();
					mLayoutNameBg.setVisibility(View.GONE);

				}

			}
		});
	}
}
