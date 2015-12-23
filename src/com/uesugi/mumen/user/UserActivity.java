package com.uesugi.mumen.user;

import java.io.File;

import lecho.lib.filechooser.FilechooserActivity;
import lecho.lib.filechooser.ItemType;
import lecho.lib.filechooser.SelectionMode;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uesugi.mumen.CircleImageView;
import com.uesugi.mumen.MuMenApplication;
import com.uesugi.mumen.R;
import com.uesugi.mumen.entity.LoginEntity;
import com.uesugi.mumen.utils.Constants;
import com.uesugi.mumen.utils.FileUtils;
import com.uesugi.mumen.utils.RemoteUtils;
import com.uesugi.mumen.utils.ShowAlertDialog;
import com.uesugi.mumen.utils.StringUtils;
import com.uesugi.mumen.utils.UserPreferences;
import com.uesugi.mumen.utils.WHTTHttpRequestCallBack;

/**
 * 我的
 * 
 * @author whtt
 * 
 */

public class UserActivity extends FinalActivity {

	private final static String TAG = "UserActivity";

	private Context mContext = null;

	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;
	@ViewInject(id = R.id.top_view_textbtn_right, click = "btnRight")
	private Button mTopBtnTextRight;

	@ViewInject(id = R.id.user_imgv_icon, click = "btnIconShow")
	private CircleImageView mImgVIcon;
	@ViewInject(id = R.id.user_txt_name)
	private TextView mTextName;
	private FinalBitmap mFinalBitmap;

	private Bitmap mDefaultBitmap;
	private Bitmap mDefaultBitmap2;

	private ShowAlertDialog mDialog = null;

	@ViewInject(id = R.id.user_layout_icon, click = "btnIcon")
	private RelativeLayout mLayoutIcon;
	@ViewInject(id = R.id.user_layout_hb, click = "btnHb")
	private RelativeLayout mLayoutHb;
	@ViewInject(id = R.id.user_layout_fx, click = "btnFx")
	private RelativeLayout mLayoutFx;
	@ViewInject(id = R.id.user_layout_dz, click = "btnDz")
	private RelativeLayout mLayoutDz;
	@ViewInject(id = R.id.user_layout_sj, click = "btnSj")
	private RelativeLayout mLayoutSj;
	@ViewInject(id = R.id.user_layout_exit, click = "btnExit")
	private RelativeLayout mLayoutExit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user);

		mContext = this;
		initView();
		initUser();
	}

	public void btnExit(View v) {
		AlertDialog.Builder alog = new Builder(this);
		alog.setTitle("注销");
		alog.setMessage("是否确定注销");
		alog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				dialog.dismiss();

			}

		});

		alog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				MuMenApplication.getInstance().logout();

			}

		});

		alog.create().show();
	}
	public void btnRight(View v) {
		finish();
	}

	public void btnHb(View v) {
		Intent intent = new Intent();
		intent.setClass(mContext, UserXshbActivity.class);
		startActivity(intent);
	}

	public void btnFx(View v) {
		Intent intent = new Intent();
		intent.setClass(mContext, UserWyfxActivity.class);
		startActivity(intent);
	}

	public void btnDz(View v) {
		Intent intent = new Intent();
		intent.setClass(mContext, UserDzysActivity.class);
		startActivity(intent);
	}

	private void initView() {
		mDialog = new ShowAlertDialog(mContext);

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
		mDefaultBitmap = BitmapFactory.decodeResource(res, R.drawable.bg_head);
		mTextTopTitle.setText("我");

		if (Constants.entityUser.role.equals("1")) {
			mLayoutIcon.setVisibility(View.VISIBLE);
			mLayoutHb.setVisibility(View.VISIBLE);
			mLayoutFx.setVisibility(View.VISIBLE);
			mLayoutDz.setVisibility(View.VISIBLE);
			mLayoutSj.setVisibility(View.GONE);
		} else {
			mLayoutIcon.setVisibility(View.GONE);
			mLayoutHb.setVisibility(View.GONE);
			mLayoutFx.setVisibility(View.GONE);
			mLayoutDz.setVisibility(View.GONE);
			mLayoutSj.setVisibility(View.VISIBLE);
		}

	}

	public void btnIcon(View v) {

		showImagePicker();

	}

	public void btnIconShow(View v) {

		Intent intent = new Intent();
		intent.setClass(mContext, IconActivity.class);
		startActivity(intent);

	}

	public void btnSj(View v) {
		Intent intent = new Intent();
		intent.setClass(mContext, ShopActivity.class);
		startActivity(intent);

	}

	private void initUser() {
		
		mImgVIcon.setImageBitmap(mDefaultBitmap);
		if (Constants.entityUser.role.equals("1")) {

			mTextName.setText(Constants.entityUser.store_name);
			if (!StringUtils.isBlank(Constants.entityUser.store_icon)) {

				mFinalBitmap.display(mImgVIcon,
						Constants.entityUser.store_icon, mDefaultBitmap,
						mDefaultBitmap);

			} else {
				mImgVIcon.setImageBitmap(mDefaultBitmap);
			}
		} else {
			mTextName.setText(Constants.entityUser.factory_name);
			if (!StringUtils.isBlank(Constants.entityUser.factory_icon)) {

				mFinalBitmap.display(mImgVIcon,
						Constants.entityUser.factory_icon, mDefaultBitmap,
						mDefaultBitmap);

			} else {
				mImgVIcon.setImageBitmap(mDefaultBitmap);
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 999 && resultCode == RESULT_OK) {

			myHandler.sendMessage(myHandler.obtainMessage(MSG_SHOW_CARMERA));

		} else if (requestCode == 998 && resultCode == RESULT_OK) {
			Uri uri = data.getData();

			Message msg = new Message();
			msg.what = MSG_SHOW_ALBUM;
			msg.obj = uri;
			myHandler.sendMessage(msg);

		} else if (requestCode == 997 && resultCode == RESULT_OK) {
			Bitmap bmap = data.getParcelableExtra("data");

			Message msg = new Message();
			msg.what = MSG_SHOW_CROP;
			msg.obj = bmap;
			myHandler.sendMessage(msg);

		}
	}

	/**
	 * 以下为图片相关
	 */

	private void showImagePicker() {

		String[] ComplaintTypes = new String[] { "拍摄照片", "从相册中选取" };

		new AlertDialog.Builder(this)
				.setTitle("上传头像")
				.setItems(ComplaintTypes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

								if (which == 0) {
									myHandler.sendMessage(myHandler
											.obtainMessage(MSG_OPEN_CARMERA));
								} else {
									myHandler.sendMessage(myHandler
											.obtainMessage(MSG_OPEN_ALBUM));
								}

							}
						}).setNegativeButton("取消", null).show();

	}

	private File mTempCameraFile = null;
	private int MSG_OPEN_CARMERA = 0;
	private int MSG_OPEN_ALBUM = 1;
	private int MSG_SHOW_CARMERA = 2;
	private int MSG_SHOW_ALBUM = 3;
	private int MSG_OPEN_CROP = 4;
	private int MSG_SHOW_CROP = 5;

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.what == MSG_OPEN_ALBUM) {

				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);

				startActivityForResult(Intent.createChooser(intent, "请选择位置"),
						998);

			} else if (msg.what == MSG_OPEN_CARMERA) {
				
				String fileName = FileUtils.createFileName() + ".jpg";
				
				//System.out.println("文件保存路径: " + Constants.IMAGE_CACHE_PATH + " 文件名: " + fileName);
				
				mTempCameraFile = new File(Constants.IMAGE_CACHE_PATH, fileName);

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(mTempCameraFile));
				intent.putExtra("return-data", true);
				startActivityForResult(intent, 999);

			} else if (msg.what == MSG_SHOW_CARMERA) {
				String path = mTempCameraFile.getPath();

				Message message = new Message();
				message.what = MSG_OPEN_CROP;
				message.obj = path;
				myHandler.sendMessage(message);

			} else if (msg.what == MSG_SHOW_ALBUM) {
				Uri uri = (Uri) msg.obj;

				String path = null;

				try {
					path = getRealPath(uri);
				} catch (Exception e) {
					path = uri.getPath();
				}

				Message message = new Message();
				message.what = MSG_OPEN_CROP;
				message.obj = path;
				myHandler.sendMessage(message);

			} else if (msg.what == MSG_OPEN_CROP) {
				
				
				String path = String.valueOf(msg.obj);

				Uri uri = Uri.fromFile(new File(path));

				Intent intent = new Intent();

				intent.setAction("com.android.camera.action.CROP");
				intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);// 裁剪框比例
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", 200);// 输出图片大小
				intent.putExtra("outputY", 200);
				intent.putExtra("return-data", true);

				startActivityForResult(intent, 997);

			} else if (msg.what == MSG_SHOW_CROP) {
				final Bitmap bitmap = (Bitmap) msg.obj;

				String fileName = FileUtils.createFileName() + ".jpg";
				File temp = new File(Constants.IMAGE_CACHE_PATH, fileName);
				FileUtils.savePhoto(bitmap, temp);

				// 开始上传
				mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);

				RemoteUtils.setShopIcon(temp, new WHTTHttpRequestCallBack() {

					@Override
					public void result(Object obj) {
						// TODO Auto-generated method stub

						LoginEntity entity = (LoginEntity) obj;
						mDialog.dismissProgressDlg();

						if (entity.success) {
							
							Constants.entityUser = entity.l_user;
							
							// Constants.TOKEN = entity.l_user.token;
							UserPreferences.saveUserPref(mContext,
									Constants.entityUser);
							mImgVIcon.setImageBitmap(bitmap);
							Toast.makeText(mContext, "上传成功!",
									Toast.LENGTH_SHORT).show();

						} else {
							if (entity.resultCode.equals("1310")) {
								Toast.makeText(mContext, entity.msg,
										Toast.LENGTH_SHORT).show();
								// LoveCityWideApplication.getInstance().logout();
								// relogin();

							} else {
								Toast.makeText(mContext, entity.msg,
										Toast.LENGTH_SHORT).show();
							}

						}

					}
				});

			}

		}

	};

	public String getRealPath(Uri contentUri) {
		String path = null;
		String[] images_data = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(contentUri, images_data,
				null, null, null);
		if (cursor.moveToFirst()) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			path = cursor.getString(column_index);
		}
		cursor.close();
		return path;
	}

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

					System.exit(0);

				}

			});

			alog.create().show();

			return true;
		}
		}

		return super.onKeyDown(keyCode, event);

	}

}
