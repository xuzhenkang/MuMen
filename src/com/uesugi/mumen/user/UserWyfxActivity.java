package com.uesugi.mumen.user;

import java.io.File;
import java.util.ArrayList;

import lecho.lib.filechooser.FilechooserActivity;
import lecho.lib.filechooser.ItemType;
import lecho.lib.filechooser.SelectionMode;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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
import com.uesugi.mumen.entity.UploadEntity;
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
	@ViewInject(id = R.id.wyfx_txt_jj)
	private TextView mTextJJ;
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

	@ViewInject(id = R.id.wyfx_imgv_photo, click = "btnPhoto")
	private ImageView mImgVPhoto;
	@ViewInject(id = R.id.wyfx_imgv_file, click = "btnFile")
	private ImageView mImgVFile;
	@ViewInject(id = R.id.wyfx_txt_file)
	private TextView mTextFile;
	@ViewInject(id = R.id.wyfx_layout_cancel, click = "btnFileCancel")
	private RelativeLayout mLayoutFileCancel;
	@ViewInject(id = R.id.wyfx_txt_email)
	private TextView mTextEMail;
	private String mPath = "";
	private File mFile = null;
	private String att_name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_wyfx);

		mContext = this;

		initView();

	}

	public void btnLeft(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEdit1.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(mEdit2.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(mEdit3.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(mEdit4.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(mEdit5.getWindowToken(), 0);
		finish();
	}

	public void btnFile(View v) {
		Intent i = new Intent(mContext, FilechooserActivity.class);
		i.putExtra(FilechooserActivity.BUNDLE_ITEM_TYPE, ItemType.ALL);
		i.putExtra(FilechooserActivity.BUNDLE_SELECTION_MODE,
				SelectionMode.SINGLE_ITEM);
		startActivityForResult(i, 1);
	}

	public void btnFileCancel(View v) {
		mLayoutFileCancel.setVisibility(View.GONE);
		mTextFile.setText("");
		mFile = null;
	}

	public void btnPhoto(View v) {

		Intent intent = new Intent();
		intent.setClass(mContext, WyfxImgAddActivity.class);
		startActivity(intent);
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
		FinalBitmap mFinalBitmap = FinalBitmap.create(mContext);
		mFinalBitmap.configDisplayer(new Displayer() {

			@Override
			public void loadFailDisplay(View arg0, Bitmap arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void loadCompletedisplay(View arg0, Bitmap arg1,
					BitmapDisplayConfig arg2) {
				// TODO Auto-generated method stub
				ImageView x = (ImageView) arg0;
				x.setImageBitmap(arg1);
				// if(arg0.getId()==R.id.more_imgv_icon){
				//
				// }
			}
		});
		mFinalBitmap.display(mImgVHIcon,
				"http://115.28.137.139:89/Api/Public/get_pic_share?id="
						+ Constants.entityUser.factory_id);
		mTopBtnLeft.setVisibility(View.VISIBLE);
		mDialog = new ShowAlertDialog(mContext);

		mTextTopTitle.setText("我要分享");
		if (!StringUtils.isBlank(Constants.entityUser.share_content)) {
			mTextJJ.setVisibility(View.VISIBLE);
			mTextJJ.setText(Constants.entityUser.share_content);
		}
		Resources res = mContext.getResources();

		if (!StringUtils.isBlank(Constants.entityUser.factory_email)) {
			mTextEMail.setText("也可将分享发送至:" + Constants.entityUser.factory_email
					+ "邮箱");
		} else {
			mTextEMail.setText("");
		}

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
		if (Constants.wyfxUploadEntity == null) {
			Constants.wyfxUploadEntity = new UploadEntity();
		}
		RemoteUtils.setWyfx(mFile, att_name, Constants.wyfxUploadEntity.imgs,
				title, content, position, name, address,
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
							Constants.wyfxBitmap1 = null;
							Constants.wyfxBitmap2 = null;
							Constants.wyfxBitmap3 = null;
							Constants.wyfxBitmap4 = null;
							Constants.wyfxBitmap5 = null;
							Constants.wyfxUploadEntity = null;
							Toast.makeText(mContext, "发布成功！",
									Toast.LENGTH_SHORT).show();
							finish();

						}

					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			ArrayList<String> paths = data
					.getStringArrayListExtra(FilechooserActivity.BUNDLE_SELECTED_PATHS);
			StringBuilder sb = new StringBuilder();
			for (String path : paths) {
				sb.append(path);
			}
			ArrayList<String> names = data
					.getStringArrayListExtra(FilechooserActivity.BUNDLE_SELECTED_NAMES);
			StringBuilder namesb = new StringBuilder();
			for (String name : names) {
				namesb.append(name);
			}

			mFile = (File) data
					.getSerializableExtra(FilechooserActivity.BUNDLE_SELECTED_FILES);

			mPath = sb.toString();
			att_name = namesb.toString();
			mTextFile.setText(namesb.toString());
			mLayoutFileCancel.setVisibility(View.VISIBLE);
			if (mFile != null) {
				Log.e("file", "file!=null");
			} else {
				Log.e("file", "file==null");
			}

		}
	}
}
