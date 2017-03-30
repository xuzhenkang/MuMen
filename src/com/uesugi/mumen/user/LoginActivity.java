package com.uesugi.mumen.user;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.uesugi.mumen.R;
import com.uesugi.mumen.entity.HyUserEntity;
import com.uesugi.mumen.entity.LoginEntity;
import com.uesugi.mumen.entity.TestEntity;
import com.uesugi.mumen.utils.Constants;
import com.uesugi.mumen.utils.RemoteUtils;
import com.uesugi.mumen.utils.ShowAlertDialog;
import com.uesugi.mumen.utils.StringUtils;
import com.uesugi.mumen.utils.UserPreferences;
import com.uesugi.mumen.utils.WHTTHttpRequestCallBack;

/**
 * 登陆页面
 * 
 * @author whtt
 * 
 */
public class LoginActivity extends FinalActivity {

	private final static String TAG = "LoginActivity";

	private Context mContext = null;

	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;
	@ViewInject(id = R.id.top_view_btn_left, click = "btnLeft")
	private ImageButton mTopBtnLeft = null;

	@ViewInject(id = R.id.login_btn_ok, click = "btnlogin")
	private ImageButton mBtnLogin;

	@ViewInject(id = R.id.login_edit_phone)
	private EditText mEdtPhone;
	@ViewInject(id = R.id.login_edit_yanzheng)
	private EditText mEdtVerifiy;
	@ViewInject(id = R.id.login_txt_yanzheng, click = "btnVerifiy")
	private TextView mTextVerifiy;
	private ShowAlertDialog mDialog = null;
	@ViewInject(id = R.id.login_imgv_icon)
	private ImageView mImgVIcon;
	private Timer mTimer = null;
	private TimerTask mTimerTask = null;
	private int mTimeNum = 0;

	private boolean isCheck = false;
	private final static int TIME_SUCCESS = 10;
	private static final int MSG_AUTH_CANCEL = 6;
	private static final int MSG_AUTH_ERROR = 7;
	private static final int MSG_AUTH_COMPLETE = 8;
	private String mUdId = "";

	private String loginType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		mContext = this;

		// mUdId = JPushInterface.getRegistrationID(getApplicationContext());
		initView();

	}

	private void initView() {

		mDialog = new ShowAlertDialog(mContext);

		mImgVIcon.setLayoutParams(new RelativeLayout.LayoutParams(
				Constants.width, (int) (Constants.width * 1.02f)));

		mTextTopTitle.setText("登录");
		// mTopBtnLeft.setVisibility(View.VISIBLE);
		mBtnLogin.setClickable(false);
		mBtnLogin.setSelected(true);

		mEdtPhone.addTextChangedListener(tWatcher);
		mEdtVerifiy.addTextChangedListener(tWatcher);
	}

	private TextWatcher tWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

			if (mEdtPhone.getText().toString().trim().length() == 11
					&& mEdtVerifiy.getText().toString().length() != 0) {
				mBtnLogin.setClickable(true);
				mBtnLogin.setSelected(false);
			} else {
				mBtnLogin.setClickable(false);
				mBtnLogin.setSelected(true);

			}

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub

		}
	};

	public void btnLeft(View v) {
		finish();
	}

	public void btnVerifiy(View v) {
		if (isCheck)
			return;
		String phone = mEdtPhone.getText().toString();

		if (StringUtils.isBlank(phone)) {
			Toast.makeText(mContext, "请输入您的手机号码!", Toast.LENGTH_SHORT).show();
			return;
		}

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEdtPhone.getWindowToken(), 0);
		isCheck = true;
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);

		// String udid = JPushInterface.getRegistrationID(mContext);

		RemoteUtils.doVerify(phone, new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub

				mDialog.dismissProgressDlg();

				TestEntity entity = (TestEntity) obj;

				if (!entity.success) {
					Toast.makeText(mContext, entity.msg, Toast.LENGTH_SHORT)
							.show();
					isCheck = false;
				} else {
					// UserPreferences.saveUserPref(mContext, entity.l_user);
					Toast.makeText(mContext, "发送成功！", Toast.LENGTH_SHORT)
							.show();
					// mEdtVerifiy.setText(entity.verify);
					mTimeNum = 60;
					timerStart();
				}

			}
		});
	}

	public void btnlogin(View v) {
		String phone = mEdtPhone.getText().toString();
		String verifiy = mEdtVerifiy.getText().toString();
		if (StringUtils.isBlank(phone)) {
			Toast.makeText(mContext, "请输入您的手机号!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.isBlank(verifiy)) {
			Toast.makeText(mContext, "请输入您的验证码!", Toast.LENGTH_SHORT).show();
			return;
		}

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEdtPhone.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(mEdtVerifiy.getWindowToken(), 0);
		mDialog.showProgressDlg("正在登陆中...");
		RemoteUtils.doLogin(phone, verifiy, mUdId,
				new WHTTHttpRequestCallBack() {

					@Override
					public void result(Object obj) {
						// TODO Auto-generated method stub
						mDialog.dismissProgressDlg();
						LoginEntity entity = (LoginEntity) obj;

						if (!entity.success) {
							Toast.makeText(mContext, entity.msg,
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mContext, "登陆成功！",
									Toast.LENGTH_SHORT).show();

							Constants.entityUser = entity.l_user;
							Constants.TOKEN = entity.l_user.token;
							UserPreferences.saveUserPref(mContext,
									Constants.entityUser);

							Set<String> tagSet = new LinkedHashSet<String>();
							tagSet.add("xy" + Constants.entityUser.factory_id);

							JPushInterface.setAliasAndTags(mContext, null,
									tagSet, new TagAliasCallback() {

										@Override
										public void gotResult(int arg0,
												String arg1, Set<String> arg2) {
											// TODO Auto-generated method stub

										}
									});
							String hyName = UserPreferences.loadUserNamePref(
									mContext, mEdtPhone.getText().toString());

							if (hyName == null ) {
								Intent intent = new Intent();
								intent.setClass(mContext, HyActivity.class);
								if (Constants.entityUser.factory_id.equals("1")) {
									intent.putExtra("flag", true);
								}
								startActivity(intent);
								UserPreferences.saveUserNamePref(mContext, mEdtPhone.getText().toString());
							}

							setResult(RESULT_OK);
							finish();

						}

					}
				});
	}

	/**
	 * 启动幻灯
	 */

	private void timerStart() {
		Log.e("ShopActivity", "picture show begin");
		if (mTimer != null && mTimerTask != null) {
			return;
		}

		if (mTimer != null) {
			mTimer.cancel();
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
		}

		mTimer = new Timer();
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				myHandler.sendMessage(myHandler.obtainMessage(TIME_SUCCESS));
			}
		};

		mTimer.schedule(mTimerTask, 0, 1000);
	}

	/**
	 * 停止幻灯
	 */

	private void timerStop() {
		Log.e("ShopActivity", "picture show stop");

		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}

	}

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {

			case TIME_SUCCESS: {

				if (mTimeNum == -1) {
					timerStop();
					isCheck = false;
					mTextVerifiy.setText("获取验证码");
					break;
				}
				mTextVerifiy.setText(mTimeNum + "秒");
				mTimeNum--;
				break;
			}

			}

		}

	};

	@SuppressLint("NewApi")
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK: {

			AlertDialog.Builder alog = new Builder(this);
			alog.setTitle("退出");
			alog.setMessage("是否确定退出");
			alog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					dialog.dismiss();

				}

			});

			alog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					goback();
				}

			});

			alog.create().show();

			return true;
		}
		}

		return super.onKeyDown(keyCode, event);

	}

	public void goback() {
		setResult(RESULT_CANCELED);
		finish();
	}
}
