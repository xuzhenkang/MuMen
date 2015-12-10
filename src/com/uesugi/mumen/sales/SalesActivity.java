package com.uesugi.mumen.sales;

import java.util.ArrayList;
import java.util.List;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uesugi.mumen.GuangGaoActivity;
import com.uesugi.mumen.MyViewPager;
import com.uesugi.mumen.MyViewPager.OnSingleTouchListener;
import com.uesugi.mumen.R;
import com.uesugi.mumen.adapter.ArticleAdapter;
import com.uesugi.mumen.adapter.ArticleAdapter3;
import com.uesugi.mumen.entity.ArticleListEntity;
import com.uesugi.mumen.entity.PicEntity;
import com.uesugi.mumen.entity.PicListEntity;
import com.uesugi.mumen.entity.TitleListEntity;
import com.uesugi.mumen.pulldown.PullDownView;
import com.uesugi.mumen.pulldown.PullDownView.OnPullDownListener;
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

public class SalesActivity extends FinalActivity {

	private final static String TAG = "SalesActivity";

	private Context mContext = null;

	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;

	private ArticleListEntity mEntity1 = null;
	private ArticleAdapter mAdapter1;
	private ListView mListView1;
	private PullDownView mPullDownView1;
	private int visibleLastIndex1;
	private boolean isLoading1 = false;
	private int p1 = 0;
	private boolean mFlagLoading1 = false;

	private RelativeLayout mViewFoot1 = null;

	private ArticleListEntity mEntity3 = null;
	private ArticleAdapter3 mAdapter3;
	private ListView mListView3;
	private PullDownView mPullDownView3;
	private int visibleLastIndex3;
	private boolean isLoading3 = false;
	private int p3 = 0;
	private boolean mFlagLoading3 = false;

	private RelativeLayout mViewFoot3 = null;

	private ArticleListEntity mEntity4 = null;
	private ArticleAdapter mAdapter4;
	private ListView mListView4;
	private PullDownView mPullDownView4;
	private int visibleLastIndex4;
	private boolean isLoading4 = false;
	private int p4 = 0;
	private boolean mFlagLoading4 = false;

	private RelativeLayout mViewFoot4 = null;

	private FinalBitmap mFinalBitmap;

	private Bitmap mDefaultBitmap;
	private Bitmap mDefaultBitmap2;
	private Bitmap mDefaultBitmap3;
	private Bitmap mDefaultBitmap4;
	private ShowAlertDialog mDialog = null;

	@ViewInject(id = R.id.sales_txt_tab1, click = "btnTab1")
	private TextView mTextTab1;
	@ViewInject(id = R.id.sales_txt_tab3, click = "btnTab3")
	private TextView mTextTab3;
	@ViewInject(id = R.id.sales_txt_tab4, click = "btnTab4")
	private TextView mTextTab4;
	private String id1 = "";
	private String id3 = "";
	private String id4 = "";
	@ViewInject(id = R.id.sales_layout_1)
	private RelativeLayout mLayoutTab1;
	@ViewInject(id = R.id.sales_layout_3)
	private RelativeLayout mLayoutTab3;
	@ViewInject(id = R.id.sales_layout_4)
	private RelativeLayout mLayoutTab4;

	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips;
	/**
	 * 装ImageView数组
	 */
	public ImageView[] mImageViews;
	private MyAdapter mMyAdapter;

	private ViewGroup mGroup;
	public List<PicEntity> mPicurlList = new ArrayList<PicEntity>();

	private MyViewPager mViewPager;

	private RelativeLayout mLayoutHd;
	private int picIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sales);

		mContext = this;
		initView();
		getTitleList();
	}

	private void initView() {
		mDialog = new ShowAlertDialog(mContext);

		mTextTopTitle.setText("产品销售");

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
		mDefaultBitmap3 = BitmapFactory.decodeResource(res,
				R.drawable.bg_default_photo);
		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView1 = (PullDownView) findViewById(R.id.sales_pull_down_view1);

		mPullDownView1.setOnPullDownListener(new OnPullDownListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mAdapter1.clearAll();
				mFlagLoading1 = false;
				p1 = 0;
				mViewFoot1.setVisibility(View.GONE);
				getList1();
				getPic();
			}

			@Override
			public void onMore() {
				// TODO Auto-generated method stub
				mPullDownView1.notifyDidMore();
			}
		});
		mListView1 = mPullDownView1.getListView();
		mPullDownView1.setBackgroundResource(R.color.transparent);
		mListView1.setBackgroundResource(R.color.transparent);

		mListView1.setSelector(R.color.transparent);
		// 去掉ListView 边缘模糊
		mListView1.setFadingEdgeLength(0);

		// 加载幻灯
		LinearLayout ViewHeader = (LinearLayout) LayoutInflater.from(
				mContext).inflate(R.layout.layout_list_header, null);
		mGroup = (ViewGroup) ViewHeader.findViewById(R.id.viewGroup_hd);
		mViewPager = (MyViewPager) ViewHeader.findViewById(R.id.myviewpager_hd);
		mLayoutHd = (RelativeLayout) ViewHeader
				.findViewById(R.id.header_layout_hd);
		ImageView bg = (ImageView) ViewHeader.findViewById(R.id.header_imgv_bg);
		mMyAdapter = new MyAdapter();
		mViewPager.setOnSingleTouchListener(new OnSingleTouchListener() {

			@Override
			public void onSingleTouch(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, GuangGaoActivity.class);
				intent.putExtra("title", mPicurlList.get(picIndex).title);
				intent.putExtra("url", mPicurlList.get(picIndex).url);
				startActivity(intent);
			}

		});
		mViewPager
				.setLayoutParams(new RelativeLayout.LayoutParams(
						Constants.width - DisplayUtil.dip2px(mContext, 20),
						(int) ((Constants.width - DisplayUtil.dip2px(mContext,
								20)) * 0.53f)));
		mLayoutHd
				.setLayoutParams(new LinearLayout.LayoutParams(Constants.width
						- DisplayUtil.dip2px(mContext, 20),
						(int) ((Constants.width - DisplayUtil.dip2px(mContext,
								20)) * 0.53f)));
		bg.setLayoutParams(new RelativeLayout.LayoutParams(
				Constants.width,
				(int) ((Constants.width - DisplayUtil.dip2px(mContext, 20)) * 0.53f)
						+ DisplayUtil.dip2px(mContext, 20)));
		// 设置Adapter

		// 设置监听，主要是设置点点的背景
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (mPicurlList.size() > 0) {
					int x = (arg0 % mPicurlList.size());
					picIndex = x;
					setImageBackground(x);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		mListView1.addHeaderView(ViewHeader);

		// 加载翻页样式
		RelativeLayout ViewFoot = (RelativeLayout) LayoutInflater
				.from(mContext).inflate(R.layout.layout_list_foot, null);
		mViewFoot1 = (RelativeLayout) ViewFoot.findViewById(R.id.foot_view);
		mViewFoot1.setVisibility(View.GONE);
		mListView1.addFooterView(ViewFoot);

		mListView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position - 1 < mAdapter1.mListEntity.size()
						&& position != 0) {
					Intent intent = new Intent();
					intent.setClass(mContext, GuangGaoActivity.class);
					intent.putExtra("title",
							mAdapter1.mListEntity.get(position - 1).title);
					intent.putExtra("url",
							mAdapter1.mListEntity.get(position - 1).url);
					startActivity(intent);
				}
			}
		});
		mListView1.setDividerHeight(0);
		// mAdapter = new NewsAdapter(mContext, mFid);

		mAdapter1 = new ArticleAdapter(mContext, mFinalBitmap);
		mListView1.setAdapter(mAdapter1);

		mListView1.setOnScrollListener(mOnScrollListener1);

		mPullDownView1.enableAutoFetchMore(false, 1, false);

		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView3 = (PullDownView) findViewById(R.id.sales_pull_down_view3);

		mPullDownView3.setOnPullDownListener(new OnPullDownListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mAdapter3.clearAll();
				mFlagLoading3 = false;
				p3 = 0;
				mViewFoot3.setVisibility(View.GONE);
				getList3();
			}

			@Override
			public void onMore() {
				// TODO Auto-generated method stub
				mPullDownView3.notifyDidMore();
			}
		});
		mListView3 = mPullDownView3.getListView();
		mPullDownView3.setBackgroundResource(R.color.transparent);
		mListView3.setBackgroundResource(R.color.transparent);

		mListView3.setSelector(R.color.transparent);
		// 去掉ListView 边缘模糊
		mListView3.setFadingEdgeLength(0);
		// 加载翻页样式
		RelativeLayout ViewFoot3 = (RelativeLayout) LayoutInflater.from(
				mContext).inflate(R.layout.layout_list_foot, null);
		mViewFoot3 = (RelativeLayout) ViewFoot3.findViewById(R.id.foot_view);
		mViewFoot3.setVisibility(View.GONE);
		mListView3.addFooterView(ViewFoot);

		mListView3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position < mAdapter3.mListEntity.size()) {
					Intent intent = new Intent();
					intent.setClass(mContext, GuangGaoActivity.class);
					intent.putExtra("title",
							mAdapter3.mListEntity.get(position).title);
					intent.putExtra("url",
							mAdapter3.mListEntity.get(position).url);
					startActivity(intent);
				}
			}
		});
		mListView3.setDividerHeight(0);
		// mAdapter = new NewsAdapter(mContext, mFid);

		mAdapter3 = new ArticleAdapter3(mContext, mFinalBitmap);
		mListView3.setAdapter(mAdapter3);

		mListView3.setOnScrollListener(mOnScrollListener3);

		mPullDownView3.enableAutoFetchMore(false, 1, false);

		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView4 = (PullDownView) findViewById(R.id.sales_pull_down_view4);

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
		mListView4.addFooterView(ViewFoot);

		mListView4.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position < mAdapter4.mListEntity.size()) {
					Intent intent = new Intent();
					intent.setClass(mContext, GuangGaoActivity.class);
					intent.putExtra("title",
							mAdapter4.mListEntity.get(position).title);
					intent.putExtra("url",
							mAdapter4.mListEntity.get(position).url);
					startActivity(intent);
				}
			}
		});
		mListView4.setDividerHeight(0);
		// mAdapter = new NewsAdapter(mContext, mFid);

		mAdapter4 = new ArticleAdapter(mContext, mFinalBitmap);
		mListView4.setAdapter(mAdapter4);

		mListView4.setOnScrollListener(mOnScrollListener4);

		mPullDownView4.enableAutoFetchMore(false, 1, false);
	}

	private AbsListView.OnScrollListener mOnScrollListener1 = new AbsListView.OnScrollListener() {

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			int itemsLastIndex = mAdapter1.getCount();

			if (itemsLastIndex >= 0) {
				Log.e("xxxxxxxx", visibleLastIndex1 + "///" + itemsLastIndex);
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& visibleLastIndex1 == itemsLastIndex
						&& isLoading1 == false && mFlagLoading1) {

					// 翻页请求
					isLoading1 = true;
					Log.e("OnScrollListener", "yes");
					getList1();
				}

			}

		}

		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			// if (mFid.equals("1")) {
			visibleLastIndex1 = firstVisibleItem + visibleItemCount - 2;
			// } else {
			// visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
			// }
		}
	};
	private AbsListView.OnScrollListener mOnScrollListener3 = new AbsListView.OnScrollListener() {

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			int itemsLastIndex = mAdapter3.getCount();

			if (itemsLastIndex >= 0) {
				Log.e("xxxxxxxx", visibleLastIndex3 + "///" + itemsLastIndex);
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& visibleLastIndex3 == itemsLastIndex
						&& isLoading3 == false && mFlagLoading3) {

					// 翻页请求
					isLoading3 = true;
					Log.e("OnScrollListener3", "yes");
					getList1();
				}

			}

		}

		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			// if (mFid.equals("1")) {
			visibleLastIndex3 = firstVisibleItem + visibleItemCount - 1;
			// } else {
			// visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
			// }
		}
	};
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

	public void getList1() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		p1 += 1;
		RemoteUtils.getArticleList(id1, p1 + "", "20",
				new WHTTHttpRequestCallBack() {

					@Override
					public void result(Object obj) {
						// TODO Auto-generated method stub
						mDialog.dismissProgressDlg();
						ArticleListEntity entity = (ArticleListEntity) obj;
						isLoading1 = false;
						mPullDownView1.notifyDidLoad();
						try {
							mPullDownView1.notifyDidRefresh();
						} catch (Exception e) {
							// TODO: handle exception
						}
						if (!entity.success) {
							if (entity.resultCode.equals("1310")) {

							} else {

							}
						} else {
							mEntity1 = entity;
							mAdapter1.setData(mEntity1.list);
							if (mEntity1.list.size() == 20) {
								mFlagLoading1 = true;
								mViewFoot1.setVisibility(View.VISIBLE);

							} else {
								mFlagLoading1 = false;
								mViewFoot1.setVisibility(View.GONE);
							}
						}

					}
				});
	}

	public void getPic() {

		RemoteUtils.getPicList(id1, new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				PicListEntity entity = (PicListEntity) obj;

				if (!entity.success) {

				} else {
					mPicurlList = entity.list;
					setHD();
				}

			}
		});
	}

	public void getList3() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		p3 += 1;
		RemoteUtils.getArticleList(id3, p3 + "", "20",
				new WHTTHttpRequestCallBack() {

					@Override
					public void result(Object obj) {
						// TODO Auto-generated method stub
						mDialog.dismissProgressDlg();
						ArticleListEntity entity = (ArticleListEntity) obj;
						isLoading3 = false;
						mPullDownView3.notifyDidLoad();
						try {
							mPullDownView3.notifyDidRefresh();
						} catch (Exception e) {
							// TODO: handle exception
						}
						if (!entity.success) {
							if (entity.resultCode.equals("1310")) {

							} else {

							}
						} else {
							mEntity3 = entity;
							mAdapter3.setData(mEntity3.list);
							if (mEntity3.list.size() == 20) {
								mFlagLoading3 = true;
								mViewFoot3.setVisibility(View.VISIBLE);

							} else {
								mFlagLoading3 = false;
								mViewFoot3.setVisibility(View.GONE);
							}
						}

					}
				});
	}

	public void getList4() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		p4 += 1;
		RemoteUtils.getArticleList(id4, p4 + "", "20",
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

	public void btnTab1(View v) {

		mTextTab1.setTextColor(Color.parseColor("#f5a671"));
		mTextTab3.setTextColor(Color.parseColor("#757575"));
		mTextTab4.setTextColor(Color.parseColor("#757575"));
		mLayoutTab1.setVisibility(View.VISIBLE);
		mLayoutTab3.setVisibility(View.GONE);
		mLayoutTab4.setVisibility(View.GONE);

	}

	public void btnTab3(View v) {

		mTextTab3.setTextColor(Color.parseColor("#f5a671"));
		mTextTab1.setTextColor(Color.parseColor("#757575"));
		mTextTab4.setTextColor(Color.parseColor("#757575"));
		mLayoutTab3.setVisibility(View.VISIBLE);
		mLayoutTab1.setVisibility(View.GONE);
		mLayoutTab4.setVisibility(View.GONE);

		if (mEntity3 == null) {
			getList3();
		}

	}

	public void btnTab4(View v) {

		mTextTab4.setTextColor(Color.parseColor("#f5a671"));
		mTextTab3.setTextColor(Color.parseColor("#757575"));
		mTextTab1.setTextColor(Color.parseColor("#757575"));
		mLayoutTab4.setVisibility(View.VISIBLE);
		mLayoutTab3.setVisibility(View.GONE);
		mLayoutTab1.setVisibility(View.GONE);

		if (mEntity4 == null) {
			getList4();
		}
	}

	/**
	 * 
	 * @author whtt
	 * 
	 */
	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			int count = 0;
			if (mImageViews != null && mImageViews.length == 1) {
				count = 1;
			} else if (mImageViews != null) {
				count = Integer.MAX_VALUE;
			}
			return count;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			if (mImageViews == null || mImageViews.length == 0) {
				return;
			}
			((MyViewPager) container).removeView(mImageViews[position
					% mImageViews.length]);

		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			if (mImageViews == null || mImageViews.length == 0) {
				return null;
			}
			if (position == 0) {
				return mImageViews[0];
			} else {
				try {
					((MyViewPager) container).addView(mImageViews[position
							% mImageViews.length], 0);
				} catch (Exception e) {
					// TODO: handle exception
				}

				return mImageViews[position % mImageViews.length];
			}
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

	private void setHD() {
		// 加入幻灯
		if (mPicurlList.size() > 0) {
			tips = new ImageView[mPicurlList.size()];
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
			if (mPicurlList.size() == 1) {
				mImageViews = new ImageView[mPicurlList.size()];
				mViewPager.setAdapter(mMyAdapter);
				for (int i = 0; i < mPicurlList.size(); i++) {

					PicEntity entity = mPicurlList.get(i);

					ImageView imageView = new ImageView(mContext);
					imageView.setScaleType(ScaleType.FIT_XY);

					mFinalBitmap.display(imageView, entity.pic);

					mImageViews[i] = imageView;

					System.out.println("pic : " + Constants.URL_IMAGE
							+ entity.pic);

				}
			} else {
				mImageViews = new ImageView[mPicurlList.size() * 2];
				mViewPager.setAdapter(mMyAdapter);
				for (int i = 0; i < mPicurlList.size(); i++) {

					PicEntity entity = mPicurlList.get(i);
					ImageView imageView = new ImageView(mContext);
					ImageView imageView2 = new ImageView(mContext);
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView2.setScaleType(ScaleType.FIT_XY);

					if (!StringUtils.isBlank(entity.pic)) {
						mFinalBitmap.display(imageView, entity.pic);
						mFinalBitmap.display(imageView2, entity.pic);
					} else {
						imageView.setImageBitmap(mDefaultBitmap);
						imageView2.setImageBitmap(mDefaultBitmap);
					}
					mImageViews[i] = imageView;
					mImageViews[i + mPicurlList.size()] = imageView2;

				}
			}
			Log.e("mImageViews.length", mMyAdapter.getCount() + "");

			if (mPicurlList.size() == 1) {
				mViewPager.setCurrentItem(0);
			} else {
				mViewPager.setCurrentItem(mImageViews.length * 100
						+ mPicurlList.size());
			}
			mMyAdapter.notifyDataSetChanged();
			mMyAdapter.notifyDataSetChanged();
		}
	}
	public void getTitleList() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);

		RemoteUtils.getTitle("3", new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mDialog.dismissProgressDlg();
				TitleListEntity entity = (TitleListEntity) obj;

				if (!entity.success) {

				} else {
					mTextTab1.setText(entity.list.get(0).title);
					mTextTab3.setText(entity.list.get(1).title);
					mTextTab4.setText(entity.list.get(2).title);
					id1 = entity.list.get(0).id;
					id3 = entity.list.get(1).id;
					id4 = entity.list.get(2).id;
					getList1();
					getPic();
				}

			}
		});
	}
}
