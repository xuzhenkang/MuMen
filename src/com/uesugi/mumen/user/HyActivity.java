package com.uesugi.mumen.user;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.uesugi.mumen.MyViewPager;
import com.uesugi.mumen.MyViewPager.OnSingleTouchListener;
import com.uesugi.mumen.R;
import com.uesugi.mumen.entity.HyEntity;
import com.uesugi.mumen.utils.Constants;
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

public class HyActivity extends FinalActivity {

	private final static String TAG = "HyActivity";

	private Context mContext = null;

	private FinalBitmap mFinalBitmap;

	private Bitmap mDefaultBitmap;
	private Bitmap mDefaultBitmapSP1;
	private Bitmap mDefaultBitmapSP2;
	private Bitmap mDefaultBitmapSP3;
	private Bitmap mDefaultBitmapSP4;

	private ShowAlertDialog mDialog = null;

	private boolean flag = false;

	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips;
	/**
	 * 装ImageView数组
	 */
	public ImageView[] mImageViews;
	private MyAdapter mMyAdapter;

	@ViewInject(id = R.id.hy_viewGroup_hd)
	private ViewGroup mGroup;
	public List<String> mPicurlList = new ArrayList<String>();

	@ViewInject(id = R.id.hy_myviewpager_hd)
	private MyViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hy);

		mContext = this;
		flag = getIntent().getBooleanExtra("flag", false);
		initView();
		if (flag) {
			mPicurlList.add("sp1");
			mPicurlList.add("sp2");
			mPicurlList.add("sp3");
			mPicurlList.add("sp4");
			mPicurlList.add("xx");
			setHD();
		} else {

			getList();
		}
	}

	public void btnLeft(View v) {
		finish();
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
		mDefaultBitmap = BitmapFactory.decodeResource(res,
				R.drawable.bg_default_list);
		mDefaultBitmapSP1 = BitmapFactory.decodeResource(res, R.drawable.sp1);
		mDefaultBitmapSP2 = BitmapFactory.decodeResource(res, R.drawable.sp2);
		mDefaultBitmapSP3 = BitmapFactory.decodeResource(res, R.drawable.sp3);
		mDefaultBitmapSP4 = BitmapFactory.decodeResource(res, R.drawable.sp4);

		mMyAdapter = new MyAdapter();
		mViewPager.setOnSingleTouchListener(new OnSingleTouchListener() {

			@Override
			public void onSingleTouch(View v) {
				// TODO Auto-generated method stub

			}

		});

		// 设置监听，主要是设置点点的背景
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (mPicurlList.size() > 0) {
					int x = (arg0 % mPicurlList.size());
					setImageBackground(x);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.e("onPageScrolled", arg0 + "/" + arg1 + "/" + arg2);
				if (arg0 >= mPicurlList.size() - 2 && arg2 > 10) {
					finish();
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void getList() {
		// mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		// p4 += 1;
		RemoteUtils.getHY(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mDialog.dismissProgressDlg();
				HyEntity entity = (HyEntity) obj;

				if (!entity.success) {
					finish();
				} else {
					if (entity.list.size() == 0) {
						finish();
					} else {
						Log.e("HyEntity", entity.list.size() + "");
						entity.list.add("xxx");
						mPicurlList = entity.list;
						setHD();
					}
				}

			}
		});
	}

	/**
	 * 
	 * @author whtt
	 * 
	 */
	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {

			return mPicurlList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {

			((MyViewPager) container).removeView(mImageViews[position]);

		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			try {
				((ViewPager) container).addView(mImageViews[position
						% mImageViews.length], 0);
			} catch (Exception e) {
				// handler something
			}
			return mImageViews[position];

		}

	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.diandiandian_w);
			} else {
				tips[i].setBackgroundResource(R.drawable.diandiandian_g);
			}
		}
	}

	@SuppressLint("NewApi")
	private void setHD() {
		// 加入幻灯
		if (mPicurlList.size() > 0) {
			tips = new ImageView[mPicurlList.size() - 1];
			Log.e("kkkkk", tips.length + "");
			mGroup.removeAllViews();
			for (int i = 0; i < tips.length; i++) {
				ImageView imageView = new ImageView(mContext);
				imageView.setLayoutParams(new LayoutParams(
						Constants.width / 60, Constants.width / 60));
				tips[i] = imageView;
				if (i == 0) {
					tips[i].setBackgroundResource(R.drawable.diandiandian_w);
				} else {
					tips[i].setBackgroundResource(R.drawable.diandiandian_g);
				}

				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						new ViewGroup.LayoutParams(Constants.width / 60,
								Constants.width / 60));
				layoutParams.leftMargin = Constants.width / 120;
				layoutParams.rightMargin = Constants.width / 120;
				mGroup.addView(imageView, layoutParams);

			}

			mImageViews = new ImageView[mPicurlList.size()];

			for (int i = 0; i < mPicurlList.size(); i++) {

				String pic = mPicurlList.get(i);
				ImageView imageView = new ImageView(mContext);
				imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setLayoutParams(new LayoutParams(Constants.width,
						Constants.height));

				if (!StringUtils.isBlank(pic)) {
					if (pic.equals("sp1")) {
						imageView.setImageBitmap(mDefaultBitmapSP1);
					} else if (pic.equals("sp2")) {
						imageView.setImageBitmap(mDefaultBitmapSP2);
					} else if (pic.equals("sp3")) {
						imageView.setImageBitmap(mDefaultBitmapSP3);
					} else if (pic.equals("sp4")) {
						imageView.setImageBitmap(mDefaultBitmapSP4);
					} else {
						mFinalBitmap.display(imageView, pic);
					}

				} else {
					imageView.setImageBitmap(mDefaultBitmap);

				}
				mImageViews[i] = imageView;

			}

			Log.e("mImageViews.length", mMyAdapter.getCount() + "");
			mViewPager.setAdapter(mMyAdapter);
			mViewPager.setCurrentItem(0);
			mMyAdapter.notifyDataSetChanged();
			mMyAdapter.notifyDataSetChanged();
		}
	}
}
