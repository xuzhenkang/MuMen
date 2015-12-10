package com.uesugi.mumen.user;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uesugi.mumen.R;
import com.uesugi.mumen.entity.BaseEntity;
import com.uesugi.mumen.utils.Constants;
import com.uesugi.mumen.utils.DisplayUtil;
import com.uesugi.mumen.utils.RemoteUtils;
import com.uesugi.mumen.utils.ShowAlertDialog;
import com.uesugi.mumen.utils.StringUtils;
import com.uesugi.mumen.utils.WHTTHttpRequestCallBack;

/**
 * 我的BBS首页
 * 
 * @author whtt
 * 
 */

//
// _ooOoo_
// o8888888o
// 88" . "88
// (| -_- |)
// O\ = /O
// ____/`---'\____
// .' \\| |// `.
// / \\||| : |||// \
// / _||||| -:- |||||- \
// | | \\\ - /// | |
// | \_| ''\---/'' | |
// \ .-\__ `-` ___/-. /
// ___`. .' /--.--\ `. . __
// ."" '< `.___\_<|>_/___.' >'"".
// | | : `- \`.;`\ _ /`;.`/ - ` : | |
// \ \ `-. \_ __\ /__ _/ .-` / /
// ==========`-.____`-.___\_____/___.-`===========
// `=---='
//
//
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
// 佛祖保佑 永无BUG 永不修改

public class UserWyfxActivity extends FinalActivity {

	private final static String TAG = "UserWyfxActivity";

	private Context mContext = null;
	@ViewInject(id = R.id.top_view_btn_left, click = "btnLeft")
	private ImageButton mTopBtnLeft;
	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;

	private ShowAlertDialog mDialog = null;

	@ViewInject(id = R.id.header_imgv_bg)
	private ImageView mImgVHBG;
	@ViewInject(id = R.id.header_imgv_icon)
	private ImageView mImgVHIcon;

	@ViewInject(id = R.id.wyfx_edit_1)
	private EditText mEdit1;
	@ViewInject(id = R.id.wyfx_edit_2)
	private EditText mEdit2;
	@ViewInject(id = R.id.wyfx_edit_3)
	private EditText mEdit3;
	@ViewInject(id = R.id.wyfx_edit_4)
	private EditText mEdit4;
	@ViewInject(id = R.id.wyfx_edit_5)
	private EditText mEdit5;
	@ViewInject(id = R.id.wyfx_btn_ok, click = "btnOk")
	private ImageButton mBtnOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_wyfx);

		mContext = this;

		initView();

	}

	public void btnLeft(View v) {
		finish();
	}

	private void initView() {
		mImgVHBG.setLayoutParams(new RelativeLayout.LayoutParams(
				Constants.width, (int) ((Constants.width - DisplayUtil.dip2px(
						mContext, 20)) * 0.39f)
						+ DisplayUtil.dip2px(mContext, 20)));
		mImgVHIcon
				.setLayoutParams(new LinearLayout.LayoutParams(Constants.width
						- DisplayUtil.dip2px(mContext, 20),
						(int) ((Constants.width - DisplayUtil.dip2px(mContext,
								20)) * 0.39f)));

		mTopBtnLeft.setVisibility(View.VISIBLE);
		mDialog = new ShowAlertDialog(mContext);

		mTextTopTitle.setText("我要分享");

		Resources res = mContext.getResources();

	}

	public void btnOk(View v) {

		String title = mEdit1.getText().toString();
		String content = mEdit2.getText().toString();
		String position = mEdit3.getText().toString();
		String name = mEdit4.getText().toString();
		String address = mEdit5.getText().toString();

		if (StringUtils.isBlank(title)) {
			Toast.makeText(mContext, "请输入标题!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.isBlank(content)) {
			Toast.makeText(mContext, "请输入内容!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.isBlank(position)) {
			Toast.makeText(mContext, "请输入职务!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.isBlank(name)) {
			Toast.makeText(mContext, "请输入姓名!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.isBlank(address)) {
			Toast.makeText(mContext, "请输入地址!", Toast.LENGTH_SHORT).show();
			return;
		}

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEdit1.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(mEdit2.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(mEdit3.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(mEdit4.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(mEdit5.getWindowToken(), 0);
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);

		RemoteUtils.setWyfx(title, content, position, name, address,
				new WHTTHttpRequestCallBack() {

					@Override
					public void result(Object obj) {
						// TODO Auto-generated method stub

						mDialog.dismissProgressDlg();

						BaseEntity entity = (BaseEntity) obj;

						if (!entity.success) {
							Toast.makeText(mContext, entity.msg,
									Toast.LENGTH_SHORT).show();

						} else {
							// UserPreferences.saveUserPref(mContext,
							// entity.l_user);
							Toast.makeText(mContext, "发布成功！",
									Toast.LENGTH_SHORT).show();
							finish();

						}

					}
				});
	}
}
