package com.uesugi.mumen.user;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uesugi.mumen.GuangGaoActivity;
import com.uesugi.mumen.R;
import com.uesugi.mumen.entity.BaseEntity;
import com.uesugi.mumen.entity.FieldListEntity;
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

public class UserDzysActivity extends FinalActivity {

	private final static String TAG = "UserDzysActivity";

	private Context mContext = null;
	@ViewInject(id = R.id.top_view_btn_left, click = "btnLeft")
	private ImageButton mTopBtnLeft;
	// @ViewInject(id = R.id.top_view_textbtn_right, click = "btnRight")
	// private Button mTopBtnRight;
	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;

	private FinalBitmap mFinalBitmap;
	@ViewInject(id = R.id.header_imgv_bg)
	private ImageView mImgVHBG;
	@ViewInject(id = R.id.header_imgv_icon)
	private ImageView mImgVHIcon;
	private ShowAlertDialog mDialog = null;

	@ViewInject(id = R.id.dzys_btn_ok, click = "btnOk")
	private ImageButton mBtnOk;

	private FieldListEntity mEntity;

	private List<String> mNameList = new ArrayList<String>();
	private List<String> mContentList = new ArrayList<String>();

	@ViewInject(id = R.id.dzys_layout_bg)
	private RelativeLayout mLayoutBg;
	@ViewInject(id = R.id.dzys_layout_main)
	private LinearLayout mLayoutMain;
	@ViewInject(id = R.id.dzys_imgv_photo, click = "btnPhoto")
	private ImageView mImgVPhoto;
	private List<EditText> mEditList = new ArrayList<EditText>();
	private List<ImageView> mBgList = new ArrayList<ImageView>();
	private List<TextView> mTextList = new ArrayList<TextView>();
	@ViewInject(id = R.id.dzys_txt_jj)
	private TextView mTextJJ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_uset_dzys);

		mContext = this;

		initView();
		getField();
		mLayoutBg.setVisibility(View.GONE);
	}

	public void btnLeft(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		for (int i = 0; i < mEditList.size(); i++) {
			imm.hideSoftInputFromWindow(mEditList.get(i).getWindowToken(), 0);
		}
		finish();
	}

	public void btnPhoto(View v) {

		Intent intent = new Intent();
		intent.setClass(mContext, DzysImgAddActivity.class);
		startActivity(intent);
	}

	public void getField() {

		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.getFieldListDZ(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub

				mDialog.dismissProgressDlg();

				FieldListEntity entity = (FieldListEntity) obj;

				if (!entity.success) {
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
				mContext, mEntity.list.size() * 50 + 120)));
		for (int i = 0; i < mEntity.list.size(); i++) {
			LinearLayout view = (LinearLayout) LayoutInflater.from(mContext)
					.inflate(R.layout.row_field_list, null);
			TextView name = (TextView) view
					.findViewById(R.id.row_field_txt_name);
			ImageView bg = (ImageView) view
					.findViewById(R.id.row_field_iv_content_bg);
			EditText content = (EditText) view
					.findViewById(R.id.row_field_edit_content);
			name.setText(mEntity.list.get(i).title + ":");
			mTextList.add(name);
			mBgList.add(bg);
			content.setHint("请输入" + mEntity.list.get(i).title + "!");

			mEditList.add(content);

			mLayoutMain.addView(view);

		}
		
		if (mEntity.list.size()>0) {
			int x=0;
			int p=0;
			for (int i = 0; i < mEntity.list.size(); i++) {
				if (mEntity.list.get(i).title.length()>x) {
					p=i;
					x=mEntity.list.get(i).title.length();
				}
			}
			final TextView xTextView = mTextList.get(p);
			ViewTreeObserver mViewTreeObserver = xTextView.getViewTreeObserver();
			mViewTreeObserver
					.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout() {
							// TODO Auto-generated method stub
							xTextView.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
							int w = Constants.width
									- DisplayUtil.dip2px(mContext, 70)
									- xTextView.getWidth();
							for (int i = 0; i < mEditList.size(); i++) {
								mEditList.get(i).getLayoutParams().width = w;
								mEditList.get(i).requestLayout();
								mBgList.get(i).getLayoutParams().width = w;
								mBgList.get(i).requestLayout();
							}

						}
					});

		}
		
		mLayoutBg.setVisibility(View.VISIBLE);
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
		// String content = mEditContent.getText().toString();
		//
		// if (StringUtils.isBlank(content)) {
		// Toast.makeText(mContext, "请输入内容!", Toast.LENGTH_SHORT).show();
		// return;
		// }
		//
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		for (int i = 0; i < mEditList.size(); i++) {
			imm.hideSoftInputFromWindow(mEditList.get(i).getWindowToken(), 0);
		}
		if (Constants.dzysUploadEntity == null) {
			Constants.dzysUploadEntity = new UploadEntity();
		}
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.setDzys(mContentList, mNameList,
				Constants.dzysUploadEntity.imgs, new WHTTHttpRequestCallBack() {

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
							Constants.dzysBitmap1 = null;
							Constants.dzysBitmap2 = null;
							Constants.dzysBitmap3 = null;
							Constants.dzysBitmap4 = null;
							Constants.dzysBitmap5 = null;
							Constants.dzysUploadEntity = null;
							Toast.makeText(mContext, "发布成功！",
									Toast.LENGTH_SHORT).show();
							finish();

						}

					}
				});

	}

	public void btnRight(View v) {
		Intent intent = new Intent();
		intent.setClass(mContext, GuangGaoActivity.class);
		intent.putExtra("title", "汇报说明");
		intent.putExtra("url",
				"http://115.28.137.139:89/Api/Public/get_html_accep?id="
						+ Constants.entityUser.factory_id);
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
				"http://115.28.137.139:89/Api/Public/get_pic_accep?id="
						+ Constants.entityUser.factory_id);
		mTopBtnLeft.setVisibility(View.VISIBLE);
		// mTopBtnRight.setVisibility(View.VISIBLE);
		// mTopBtnRight.setText("汇报说明");
		mDialog = new ShowAlertDialog(mContext);

		mTextTopTitle.setText("店装验收");

		if (!StringUtils.isBlank(Constants.entityUser.assep_content)) {
			mTextJJ.setVisibility(View.VISIBLE);
			mTextJJ.setText(Constants.entityUser.assep_content);
		}

		mFinalBitmap = FinalBitmap.create(mContext);
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
		Resources res = mContext.getResources();

	}

}
