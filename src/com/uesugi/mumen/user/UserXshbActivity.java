package com.uesugi.mumen.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import cn.sharesdk.framework.authorize.f;

import com.uesugi.mumen.R;
import com.uesugi.mumen.entity.BaseEntity;
import com.uesugi.mumen.entity.FieldListEntity;
import com.uesugi.mumen.entity.ShopDataListEntity;
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

public class UserXshbActivity extends FinalActivity {

	private final static String TAG = "UserXshbActivity";

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

	@ViewInject(id = R.id.xshb_btn_ok, click = "btnOk")
	private ImageButton mBtnOk;
	@ViewInject(id = R.id.xshb_txt_date)
	private TextView mTextDate;

	private FieldListEntity mEntity = null;
	private ShopDataListEntity mShopDataListEntity = null;

	private List<String> mNameList = new ArrayList<String>();
	private List<String> mContentList = new ArrayList<String>();

	@ViewInject(id = R.id.xshb_layout_bg)
	private RelativeLayout mLayoutBg;
	@ViewInject(id = R.id.xshb_layout_main)
	private LinearLayout mLayoutMain;

	private List<EditText> mEditList = new ArrayList<EditText>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_xshb);

		mContext = this;
		mLayoutBg.setVisibility(View.GONE);
		initView();
		getField();
	}

	public void btnLeft(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		for (int i = 0; i < mEditList.size(); i++) {
			imm.hideSoftInputFromWindow(mEditList.get(i).getWindowToken(), 0);
		}
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

		mTextTopTitle.setText("销售汇报");

		Resources res = mContext.getResources();

		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd");
		String m = formatter.format(date);
		String d = formatter2.format(date);

		mTextDate.setText(m + "月" + d + "日");
	}

	public void btnOk(View v) {

		mContentList.clear();
		mNameList.clear();
		for (int i = 0; i < mEditList.size(); i++) {
			String content = mEditList.get(i).getText().toString();
			if (StringUtils.isBlank(content)) {
				Toast.makeText(mContext,
						"请输入" + mEntity.list.get(i).title + "!",
						Toast.LENGTH_SHORT).show();
				return;
			}

			mContentList.add(content);
			mNameList.add(mEntity.list.get(i).field);
		}
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		for (int i = 0; i < mEditList.size(); i++) {
			imm.hideSoftInputFromWindow(mEditList.get(i).getWindowToken(), 0);
		}
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);

		RemoteUtils.setXshb(mContentList, mNameList,
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
							Toast.makeText(mContext, "提交成功！",
									Toast.LENGTH_SHORT).show();
							finish();

						}

					}
				});
	}

	public void getField() {

		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.getFieldListHB(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub

				FieldListEntity entity = (FieldListEntity) obj;

				if (!entity.success) {
					mDialog.dismissProgressDlg();
					Toast.makeText(mContext, entity.msg, Toast.LENGTH_SHORT)
							.show();

				} else {
					mEntity = entity;
					initField();
				}

			}
		});

	}

	private void initField() {

		Log.e("mEntity.list.size() * 50 + 110",
				(mEntity.list.size() * 50 + 110) + "");

		mLayoutBg.setLayoutParams(new LinearLayout.LayoutParams(Constants.width
				- DisplayUtil.dip2px(mContext, 20), DisplayUtil.dip2px(
				mContext, mEntity.list.size() * 50 + 150)));
		for (int i = 0; i < mEntity.list.size(); i++) {
			LinearLayout view = (LinearLayout) LayoutInflater.from(mContext)
					.inflate(R.layout.row_field_list, null);
			TextView name = (TextView) view
					.findViewById(R.id.row_field_txt_name);
			EditText content = (EditText) view
					.findViewById(R.id.row_field_edit_content);
			name.setText(mEntity.list.get(i).title + ":");
			content.setHint("请输入" + mEntity.list.get(i).title + "!");
			mEditList.add(content);

			mLayoutMain.addView(view);

		}
		mLayoutBg.setVisibility(View.VISIBLE);
		getShopDataList();
	}

	public void getShopDataList() {

		// mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.getShopDataList(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub

				mDialog.dismissProgressDlg();

				ShopDataListEntity entity = (ShopDataListEntity) obj;

				if (!entity.success) {
					// Toast.makeText(mContext, entity.msg, Toast.LENGTH_SHORT)
					// .show();

				} else {
					mShopDataListEntity = entity;
					initFieldData();
				}

			}
		});

	}

	private void initFieldData() {
		for (int i = 0; i < mEditList.size(); i++) {
			for (int j = 0; j < mShopDataListEntity.list.size(); j++) {
				if (mEntity.list.get(i).field.equals(mShopDataListEntity.list
						.get(j).field)) {
					mEditList.get(i).setText(
							mShopDataListEntity.list.get(j).value);
				}
			}
		}
	}
}
