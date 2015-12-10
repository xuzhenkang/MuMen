package com.uesugi.mumen.user;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.uesugi.mumen.R;
import com.uesugi.mumen.utils.Constants;
import com.uesugi.mumen.utils.StringUtils;

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

public class IconActivity extends FinalActivity {

	private final static String TAG = "IconActivity";

	private FinalBitmap mFinalBitmap;

	private Context mContext = null;

	@ViewInject(id = R.id.icon_imgv)
	private ImageView mImageView;

	@ViewInject(id = R.id.icon_layout_bg, click = "btnBg")
	private RelativeLayout mLayoutBG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_icon);

		mContext = this;

		initView();

	}

	public void btnBg(View v) {
		finish();
	}

	private void initView() {
		mImageView.setLayoutParams(new RelativeLayout.LayoutParams(
				Constants.width, Constants.width));

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
		if (Constants.entityUser.role.equals("1")) {

			if (!StringUtils.isBlank(Constants.entityUser.store_icon)) {

				mFinalBitmap.display(mImageView,
						Constants.entityUser.store_icon);

			}
		} else {
			if (!StringUtils.isBlank(Constants.entityUser.factory_icon)) {

				mFinalBitmap.display(mImageView,
						Constants.entityUser.factory_icon);

			}
		}

	}
}
