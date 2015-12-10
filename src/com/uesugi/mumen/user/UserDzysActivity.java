package com.uesugi.mumen.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uesugi.mumen.R;
import com.uesugi.mumen.entity.BaseEntity;
import com.uesugi.mumen.entity.UploadEntity;
import com.uesugi.mumen.utils.Constants;
import com.uesugi.mumen.utils.FileUtils;
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

	private int MSG_OPEN_CARMERA = 0;
	private int MSG_OPEN_ALBUM = 1;
	private int MSG_GO_SEND = 2;
	private File mTempPhotoFile = null;
	private String mPhotoLoc = null;

	private Context mContext = null;
	@ViewInject(id = R.id.top_view_btn_left, click = "btnLeft")
	private ImageButton mTopBtnLeft;
	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;

	private FinalBitmap mFinalBitmap;

	private ShowAlertDialog mDialog = null;

	@ViewInject(id = R.id.dzys_imgv_1, click = "btnImg1")
	private ImageView mImgV1;
	@ViewInject(id = R.id.dzys_imgv_2, click = "btnImg2")
	private ImageView mImgV2;

	@ViewInject(id = R.id.dzys_btn_ok, click = "btnOk")
	private ImageButton mBtnOk;
	@ViewInject(id = R.id.dzys_edit_content)
	private EditText mEditContent;

	private String mImgIndex = "";
	private File mImgFile1 = null;
	private File mImgFile2 = null;

	private Bitmap mImgBitmap1 = null;
	private Bitmap mImgBitmap2 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_uset_dzys);

		mContext = this;

		initView();

	}

	public void btnLeft(View v) {
		finish();
	}

	public void btnOk(View v) {
		File temp1 = null;
		File temp2 = null;
		File temp3 = null;
		File temp4 = null;
		File temp5 = null;

		if (mImgBitmap1 != null) {
			String fileName1 = FileUtils.createFileName() + "dz1.jpg";
			temp1 = new File(Constants.IMAGE_CACHE_PATH, fileName1);
			FileUtils.savePhoto(mImgBitmap1, temp1);
		}
		if (mImgBitmap2 != null) {
			String fileName2 = FileUtils.createFileName() + "dz2.jpg";
			temp2 = new File(Constants.IMAGE_CACHE_PATH, fileName2);
			FileUtils.savePhoto(mImgBitmap2, temp2);
		}
		if (mImgBitmap1 == null && mImgBitmap2 == null) {
			mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
			setDz(null);
		} else {

			// 开始上传
			mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);

			RemoteUtils.upDateIcon(temp1, temp2, temp3, temp4, temp5,
					new WHTTHttpRequestCallBack() {

						@Override
						public void result(Object obj) {
							// TODO Auto-generated method stub

							UploadEntity entity = (UploadEntity) obj;

							if (entity.success) {
								// Constants.uploadEntity = entity;
								// Toast.makeText(mContext, "上传成功!",
								// Toast.LENGTH_SHORT).show();
								//
								// finish();

								setDz(entity);

							} else {
								mDialog.dismissProgressDlg();
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

	private void setDz(UploadEntity entityx) {
		if (entityx == null) {
			entityx = new UploadEntity();
		}

		String content = mEditContent.getText().toString();

		if (StringUtils.isBlank(content)) {
			Toast.makeText(mContext, "请输入内容!", Toast.LENGTH_SHORT).show();
			return;
		}

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEditContent.getWindowToken(), 0);

		RemoteUtils.setDzys(content, entityx.imgs,
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

	private void initView() {

		mTopBtnLeft.setVisibility(View.VISIBLE);
		mDialog = new ShowAlertDialog(mContext);

		mTextTopTitle.setText("店装验收");

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

	public void btnImg1(View v) {
		mImgIndex = "1";
		showImagePicker(mImgFile1);
	}

	public void btnImg2(View v) {
		mImgIndex = "2";
		showImagePicker(mImgFile2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == Constants.REQUEST_MORE_CAMERA
				&& resultCode == Activity.RESULT_OK) {

			Log.e("1111111LOC", mTempPhotoFile.getAbsolutePath());
			Log.e("2222LOC", "22222");

			/**
			 * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
			 */
			int degree = readPictureDegree(mTempPhotoFile.getAbsolutePath());

			Bitmap photo = FileUtils.scaleBitmapFromFile(
					mTempPhotoFile.getAbsolutePath(), 600, 600);

			photo = rotaingImageView(degree, photo);

			FileOutputStream fout = null;
			File file = new File(Constants.IMAGE_EDIT_PATH + "/");
			if (!file.exists()) {
				file.mkdirs();
			}
			mPhotoLoc = Constants.IMAGE_EDIT_PATH + "/" + "xxx.jpg";

			try {
				fout = new FileOutputStream(mPhotoLoc);
				photo.compress(Bitmap.CompressFormat.JPEG, 70, fout);
				if (mImgIndex.equals("1")) {
					mImgBitmap1 = photo;
				} else if (mImgIndex.equals("2")) {
					mImgBitmap2 = photo;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {

					fout.flush();
					fout.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

			if (mImgIndex.equals("1")) {
				mImgV1.setImageBitmap(mImgBitmap1);
				mImgV2.setVisibility(View.VISIBLE);

			} else if (mImgIndex.equals("2")) {
				mImgV2.setImageBitmap(mImgBitmap2);

			}

		} else if (requestCode == Constants.REQUEST_MORE_PICTURE
				&& resultCode == Activity.RESULT_OK && null != data) {
			if (mImgIndex.equals("1")) {
				mImgBitmap1 = null;
				mImgBitmap1 = setImage(data.getData());
				mImgV1.setImageBitmap(mImgBitmap1);
			} else if (mImgIndex.equals("2")) {
				mImgBitmap2 = null;
				mImgBitmap2 = setImage(data.getData());
				mImgV2.setImageBitmap(mImgBitmap2);
			}
		}
	}

	/**
	 * 以下为图片相关
	 */

	private void showImagePicker(File file) {

		String[] ComplaintTypes = new String[] { "拍摄照片", "从相册中选取" };

		new AlertDialog.Builder(this)
				.setTitle("上传图片")
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

	private Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.what == MSG_OPEN_ALBUM) {

				Intent picture = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(picture, Constants.REQUEST_MORE_PICTURE);

			} else if (msg.what == MSG_OPEN_CARMERA) {
				File file = new File(Constants.IMAGE_EDIT_PATH + "/");
				if (!file.exists()) {
					file.mkdirs();
				}
				String fileName = FileUtils.createFileName() + ".jpg";
				mTempPhotoFile = new File(Constants.IMAGE_EDIT_PATH + "/"
						+ fileName);

				Intent cameraIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(mTempPhotoFile));
				startActivityForResult(cameraIntent,
						Constants.REQUEST_MORE_CAMERA);

			}
		}

	};

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		;
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	// private Bitmap imageChange(Bitmap photo) {
	// Bitmap bm = null;
	//
	// int screenWidth = mImgVImg.getWidth();
	// int picWidth = photo.getWidth();
	// int picHeight = photo.getHeight();
	// float scaleWidth;
	//
	// if (picWidth >= picHeight) {
	// // 计算缩放率，新尺寸除原始尺寸
	// scaleWidth = ((float) screenWidth) / picHeight;
	// } else {
	// // 计算缩放率，新尺寸除原始尺寸
	// scaleWidth = ((float) screenWidth) / picWidth;
	// }
	//
	// // 创建操作图片用的matrix对象
	// Matrix matrix = new Matrix();
	// // 缩放图片动作
	// matrix.postScale(scaleWidth, scaleWidth);
	// // 新得到的图片是原图片经过变换填充到整个屏幕的图片
	// bm = Bitmap
	// .createBitmap(photo, 0, 0, picWidth, picHeight, matrix, true);
	//
	// return bm;
	// }

	private Bitmap setImage(Uri mImageCaptureUri) {

		// 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
		// 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看

		ContentResolver cr = this.getContentResolver();
		Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找
		if (cursor != null) {
			cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
			String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路
			String orientation = cursor.getString(cursor
					.getColumnIndex("orientation"));// 获取旋转的角度
			cursor.close();
			if (filePath != null) {
				// Bitmap bitmap = BitmapFactory.decodeFile(filePath);//
				// 根据Path读取资源图片
				Bitmap bitmap = FileUtils.scaleBitmapFromFile(filePath, 800,
						800);
				int angle = 0;
				if (orientation != null && !"".equals(orientation)) {
					angle = Integer.parseInt(orientation);
				}
				if (angle != 0) {
					// 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
					Matrix m = new Matrix();
					int width = bitmap.getWidth();
					int height = bitmap.getHeight();
					m.setRotate(angle); // 旋转angle度
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
							m, true);// 从新生成图片

				}
				return bitmap;
			}
		}
		return null;
	}
}
