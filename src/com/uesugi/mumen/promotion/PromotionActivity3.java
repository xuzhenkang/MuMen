package com.uesugi.mumen.promotion;

import java.util.HashMap;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

import com.uesugi.mumen.R;
import com.uesugi.mumen.adapter.ArticleAdapter4;
import com.uesugi.mumen.entity.ArticleListEntity;
import com.uesugi.mumen.pulldown.PullDownView;
import com.uesugi.mumen.pulldown.PullDownView.OnPullDownListener;
import com.uesugi.mumen.utils.Constants;
import com.uesugi.mumen.utils.RemoteUtils;
import com.uesugi.mumen.utils.ShowAlertDialog;
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

public class PromotionActivity3 extends FinalActivity {

	private final static String TAG = "PromotionActivity";

	private Context mContext = null;
	@ViewInject(id = R.id.top_view_btn_left, click = "btnLeft")
	private ImageButton mTopBtnLeft = null;
	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;

	private ArticleListEntity mEntity4 = null;
	private ArticleAdapter4 mAdapter4;
	private ListView mListView4;
	private PullDownView mPullDownView4;
	private int visibleLastIndex4;
	private boolean isLoading4 = false;
	private int p4 = 0;
	private boolean mFlagLoading4 = false;

	private RelativeLayout mViewFoot4 = null;

	private FinalBitmap mFinalBitmap;

	private Bitmap mDefaultBitmap;

	private ShowAlertDialog mDialog = null;

	private String title = null;
	private String id = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_promotion_3);

		mContext = this;

		title = getIntent().getStringExtra("title");
		id = getIntent().getStringExtra("id");
		initView();
		getList4();
	}

	public void btnLeft(View v) {
		finish();
	}

	private void initView() {
		mTopBtnLeft.setVisibility(View.VISIBLE);
		mDialog = new ShowAlertDialog(mContext);

		mTextTopTitle.setText(title);

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

		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView4 = (PullDownView) findViewById(R.id.promotion_pull_down_view4);

		mPullDownView4.setOnPullDownListener(new OnPullDownListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mAdapter4.clearAll();
				mFlagLoading4 = false;
				p4 = 0;
				mViewFoot4.setVisibility(View.GONE);
				getList4();
			}

			@Override
			public void onMore() {
				// TODO Auto-generated method stub
				mPullDownView4.notifyDidMore();
			}
		});
		mListView4 = mPullDownView4.getListView();
		mPullDownView4.setBackgroundResource(R.color.transparent);
		mListView4.setBackgroundResource(R.color.transparent);

		mListView4.setSelector(R.color.transparent);
		// 去掉ListView 边缘模糊
		mListView4.setFadingEdgeLength(0);
		// 加载翻页样式
		RelativeLayout ViewFoot4 = (RelativeLayout) LayoutInflater.from(
				mContext).inflate(R.layout.layout_list_foot, null);
		mViewFoot4 = (RelativeLayout) ViewFoot4.findViewById(R.id.foot_view);
		mViewFoot4.setVisibility(View.GONE);
		mListView4.addFooterView(ViewFoot4);

		mListView4.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position < mAdapter4.mListEntity.size()) {
					showShare(false, null,
							mAdapter4.mListEntity.get(position).title,
							mAdapter4.mListEntity.get(position).url,
							mAdapter4.mListEntity.get(position).icon);
				}
			}
		});
		mListView4.setDividerHeight(0);
		// mAdapter = new NewsAdapter(mContext, mFid);

		mAdapter4 = new ArticleAdapter4(mContext, mFinalBitmap);
		mListView4.setAdapter(mAdapter4);

		mListView4.setOnScrollListener(mOnScrollListener4);

		mPullDownView4.enableAutoFetchMore(false, 1, false);

	}

	private AbsListView.OnScrollListener mOnScrollListener4 = new AbsListView.OnScrollListener() {

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			int itemsLastIndex = mAdapter4.getCount();

			if (itemsLastIndex >= 0) {
				Log.e("xxxxxxxx", visibleLastIndex4 + "///" + itemsLastIndex);
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& visibleLastIndex4 == itemsLastIndex
						&& isLoading4 == false && mFlagLoading4) {

					// 翻页请求
					isLoading4 = true;
					Log.e("OnScrollListener4", "yes");
					getList4();
				}

			}

		}

		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			// if (mFid.equals("1")) {
			visibleLastIndex4 = firstVisibleItem + visibleItemCount - 1;
			// } else {
			// visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
			// }
		}
	};

	public void getList4() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		p4 += 1;
		RemoteUtils.getArticleList(id, p4 + "", "20",
				new WHTTHttpRequestCallBack() {

					@Override
					public void result(Object obj) {
						// TODO Auto-generated method stub
						mDialog.dismissProgressDlg();
						ArticleListEntity entity = (ArticleListEntity) obj;
						isLoading4 = false;
						mPullDownView4.notifyDidLoad();
						try {
							mPullDownView4.notifyDidRefresh();
						} catch (Exception e) {
							// TODO: handle exception
						}
						if (!entity.success) {
							if (entity.resultCode.equals("1310")) {

							} else {

							}
						} else {
							mEntity4 = entity;
							mAdapter4.setData(mEntity4.list);
							if (mEntity4.list.size() == 20) {
								mFlagLoading4 = true;
								mViewFoot4.setVisibility(View.VISIBLE);

							} else {
								mFlagLoading4 = false;
								mViewFoot4.setVisibility(View.GONE);
							}
						}

					}
				});
	}

	private void showShare(boolean silent, String platform, String title,
			String url, String icon) {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		oks.setTitle(title);
		oks.setTitleUrl(url);
		oks.setText(title);
		oks.setImageUrl(icon);
		oks.setImagePath(Constants.TEST_IMAGE);// 确保SDcard下面存在此张图片
		oks.setUrl(url);
		oks.setSilent(silent);

		oks.setCallback(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				// TODO Auto-generated method stub

				Log.e("TAG", "complete");
				// isShare = true;
				mDialog.showAlertDialog("分享成功！");

			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				// TODO Auto-generated method stub
				Log.e("TAG", "cancel");
			}

		});

		oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {

			@Override
			public void onShare(Platform platform, ShareParams paramsToShare) {
				// TODO Auto-generated method stub
				Log.e("TAG", "complete2121212");

			}
		});

		oks.show(mContext);
	}
}
