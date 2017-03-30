package com.uesugi.mumen.expand;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import android.R.integer;
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
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uesugi.mumen.CircleImageView;
import com.uesugi.mumen.GuangGaoActivity;
import com.uesugi.mumen.MyViewPager;
import com.uesugi.mumen.MyViewPager.OnSingleTouchListener;
import com.uesugi.mumen.R;
import com.uesugi.mumen.adapter.ExpandAdapter;
import com.uesugi.mumen.adapter.ExpandAdapter2;
import com.uesugi.mumen.entity.ArticleListEntity;
import com.uesugi.mumen.entity.BaseEntity;
import com.uesugi.mumen.entity.FieldListEntity;
import com.uesugi.mumen.entity.PicEntity;
import com.uesugi.mumen.entity.PicListEntity;
import com.uesugi.mumen.entity.TitleListEntity;
import com.uesugi.mumen.entity.UploadEntity;
import com.uesugi.mumen.promotion.PromotionActivity3;
import com.uesugi.mumen.pulldown.PullDownView;
import com.uesugi.mumen.pulldown.PullDownView.OnPullDownListener;
import com.uesugi.mumen.user.ImgAddActivity;
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

public class ExpandActivity extends FinalActivity {

	private final static String TAG = "ExpandActivity";

	private Context mContext = null;

	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;

	private ArticleListEntity mEntity1 = null;
	private ExpandAdapter mAdapter1;
	private ListView mListView1;
	private PullDownView mPullDownView1;
	private int visibleLastIndex1;
	private boolean isLoading1 = false;
	private int p1 = 0;
	private boolean mFlagLoading1 = false;

	private RelativeLayout mViewFoot1 = null;

	private ArticleListEntity mEntity3 = null;
	private ExpandAdapter2 mAdapter3;
	private ListView mListView3;
	private PullDownView mPullDownView3;
	private int visibleLastIndex3;
	private boolean isLoading3 = false;
	private int p3 = 0;
	private boolean mFlagLoading3 = false;

	private RelativeLayout mViewFoot3 = null;

	private FinalBitmap mFinalBitmap;

	private Bitmap mDefaultBitmap;
	private Bitmap mDefaultBitmap2;
	private Bitmap mDefaultBitmap3;
	private Bitmap mDefaultBitmap4;
	private ShowAlertDialog mDialog = null;

	@ViewInject(id = R.id.expand_txt_tab1, click = "btnTab1")
	private TextView mTextTab1;
	@ViewInject(id = R.id.expand_txt_tab3, click = "btnTab3")
	private TextView mTextTab3;
	@ViewInject(id = R.id.expand_txt_tab4, click = "btnTab4")
	private TextView mTextTab4;
	private String id1 = "";
	private String id3 = "";
	private String id4 = "";

	@ViewInject(id = R.id.expand_layout_1)
	private RelativeLayout mLayoutTab1;
	@ViewInject(id = R.id.expand_layout_3)
	private RelativeLayout mLayoutTab3;
	@ViewInject(id = R.id.expand_layout_4)
	private RelativeLayout mLayoutTab4;
	@ViewInject(id = R.id.expand_layout_4_main)
	private LinearLayout mLayoutTab4Main;
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

	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips2;
	/**
	 * 装ImageView数组
	 */
	public ImageView[] mImageViews2;
	private MyAdapter2 mMyAdapter2;

	private ViewGroup mGroup2;
	public List<PicEntity> mPicurlList2 = new ArrayList<PicEntity>();

	private MyViewPager mViewPager2;

	private RelativeLayout mLayoutHd2;
	private int picIndex2 = 0;

	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips4;
	/**
	 * 装ImageView数组
	 */
	public ImageView[] mImageViews4;
	private MyAdapter4 mMyAdapter4;

	private ViewGroup mGroup4;
	public List<PicEntity> mPicurlList4 = new ArrayList<PicEntity>();

	private MyViewPager mViewPager4;

	private RelativeLayout mLayoutHd4;
	private int picIndex4 = 0;

	@ViewInject(id = R.id.expand_imgv_photo, click = "btnPhoto")
	private ImageView mImgVPhoto;

	private FieldListEntity mEntity;

	private List<String> mNameList = new ArrayList<String>();
	private List<String> mContentList = new ArrayList<String>();

	@ViewInject(id = R.id.wysj_layout_bg)
	private RelativeLayout mLayoutBg;
	@ViewInject(id = R.id.wysj_layout_main)
	private LinearLayout mLayoutMain;
	private List<EditText> mEditList = new ArrayList<EditText>();
	@ViewInject(id = R.id.expand_btn_ok, click = "btnOk")
	private ImageButton mBtnOk;

	private LinearLayout mLayoutHeaderMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_expand);

		mContext = this;
		initView();
		getTitleList();
		getTitleAndTypeList();
	}

	private void initView() {
		mDialog = new ShowAlertDialog(mContext);

		mTextTopTitle.setText("店装推广");

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
		mDefaultBitmap2 = BitmapFactory.decodeResource(res,
				R.drawable.bg_default_banner_dz);
		mDefaultBitmap3 = BitmapFactory.decodeResource(res,
				R.drawable.bg_default_photo);
		mDefaultBitmap4 = BitmapFactory.decodeResource(res,
				R.drawable.img__banner_dz);
		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView1 = (PullDownView) findViewById(R.id.expand_pull_down_view1);

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
		LinearLayout ViewHeader = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.layout_list_header, null);
		mGroup = (ViewGroup) ViewHeader.findViewById(R.id.viewGroup_hd);
		mViewPager = (MyViewPager) ViewHeader.findViewById(R.id.myviewpager_hd);
		mLayoutHeaderMain = (LinearLayout) ViewHeader
				.findViewById(R.id.header_layout_main);
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

		// mListView1.addHeaderView(ViewHeaderX);
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
				// if (position - 1 < mAdapter1.mListEntity.size()
				// && position != 0) {
				// Intent intent = new Intent();
				// intent.setClass(mContext, GuangGaoActivity.class);
				// intent.putExtra("title",
				// mAdapter1.mListEntity.get(position - 1).title);
				// intent.putExtra("url",
				// mAdapter1.mListEntity.get(position - 1).url);
				// startActivity(intent);
				// }
			}
		});
		mListView1.setDividerHeight(0);
		// mAdapter = new NewsAdapter(mContext, mFid);

		mAdapter1 = new ExpandAdapter(mContext, mFinalBitmap);
		mListView1.setAdapter(mAdapter1);

		mListView1.setOnScrollListener(mOnScrollListener1);

		mPullDownView1.enableAutoFetchMore(false, 1, false);

		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView3 = (PullDownView) findViewById(R.id.expand_pull_down_view3);

		mPullDownView3.setOnPullDownListener(new OnPullDownListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mAdapter3.clearAll();
				mFlagLoading3 = false;
				p3 = 0;
				mViewFoot3.setVisibility(View.GONE);
				getList3();
				getPic3();
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

		// 加载幻灯
		LinearLayout ViewHeader2 = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.layout_list_header, null);
		mGroup2 = (ViewGroup) ViewHeader2.findViewById(R.id.viewGroup_hd);
		mViewPager2 = (MyViewPager) ViewHeader2
				.findViewById(R.id.myviewpager_hd);
		mLayoutHd2 = (RelativeLayout) ViewHeader2
				.findViewById(R.id.header_layout_hd);
		ImageView bg2 = (ImageView) ViewHeader2
				.findViewById(R.id.header_imgv_bg);
		mMyAdapter2 = new MyAdapter2();
		mViewPager2.setOnSingleTouchListener(new OnSingleTouchListener() {

			@Override
			public void onSingleTouch(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, GuangGaoActivity.class);
				intent.putExtra("title", mPicurlList.get(picIndex2).title);
				intent.putExtra("url", mPicurlList.get(picIndex2).url);
				startActivity(intent);
			}

		});
		mViewPager2
				.setLayoutParams(new RelativeLayout.LayoutParams(
						Constants.width - DisplayUtil.dip2px(mContext, 20),
						(int) ((Constants.width - DisplayUtil.dip2px(mContext,
								20)) * 0.53f)));
		mLayoutHd2
				.setLayoutParams(new LinearLayout.LayoutParams(Constants.width
						- DisplayUtil.dip2px(mContext, 20),
						(int) ((Constants.width - DisplayUtil.dip2px(mContext,
								20)) * 0.53f)));
		bg2.setLayoutParams(new RelativeLayout.LayoutParams(
				Constants.width,
				(int) ((Constants.width - DisplayUtil.dip2px(mContext, 20)) * 0.53f)
						+ DisplayUtil.dip2px(mContext, 20)));
		// 设置Adapter

		// 设置监听，主要是设置点点的背景
		mViewPager2.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (mPicurlList2.size() > 0) {
					int x = (arg0 % mPicurlList2.size());
					picIndex2 = x;
					setImageBackground2(x);
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
		mListView3.addHeaderView(ViewHeader2);
		//
		LinearLayout more = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.layout_more_header, null);
		mListView3.addHeaderView(more);
		
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

		mAdapter3 = new ExpandAdapter2(mContext, mFinalBitmap);
		mListView3.setAdapter(mAdapter3);

		mListView3.setOnScrollListener(mOnScrollListener3);

		mPullDownView3.enableAutoFetchMore(false, 1, false);

		// 加载幻灯
		LinearLayout ViewHeader4 = (LinearLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.layout_list_header, null);
		mGroup4 = (ViewGroup) ViewHeader4.findViewById(R.id.viewGroup_hd);
		mViewPager4 = (MyViewPager) ViewHeader4
				.findViewById(R.id.myviewpager_hd);
		mLayoutHd4 = (RelativeLayout) ViewHeader4
				.findViewById(R.id.header_layout_hd);
		ImageView bg4 = (ImageView) ViewHeader4
				.findViewById(R.id.header_imgv_bg);
		mViewPager4.setBackgroundResource(R.drawable.img__banner_dz);
		mMyAdapter4 = new MyAdapter4();
		mViewPager4.setOnSingleTouchListener(new OnSingleTouchListener() {

			@Override
			public void onSingleTouch(View v) {
				// TODO Auto-generated method stub

			}

		});
		mViewPager4
				.setLayoutParams(new RelativeLayout.LayoutParams(
						Constants.width - DisplayUtil.dip2px(mContext, 20),
						(int) ((Constants.width - DisplayUtil.dip2px(mContext,
								20)) * 0.53f)));
		mLayoutHd4
				.setLayoutParams(new LinearLayout.LayoutParams(Constants.width
						- DisplayUtil.dip2px(mContext, 20),
						(int) ((Constants.width - DisplayUtil.dip2px(mContext,
								20)) * 0.53f)));
		bg4.setLayoutParams(new RelativeLayout.LayoutParams(
				Constants.width,
				(int) ((Constants.width - DisplayUtil.dip2px(mContext, 20)) * 0.53f)
						+ DisplayUtil.dip2px(mContext, 20)));
		// 设置Adapter

		// 设置监听，主要是设置点点的背景
		mViewPager4.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (mPicurlList4.size() > 0) {
					int x = (arg0 % mPicurlList4.size());
					picIndex4 = x;
					setImageBackground4(x);
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
		mLayoutTab4Main.addView(ViewHeader4);
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

	public void getPic3() {

		RemoteUtils.getPicList(id3, new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				PicListEntity entity = (PicListEntity) obj;

				if (!entity.success) {

				} else {
					mPicurlList2 = entity.list;
					setHD2();
				}

			}
		});
	}

	public void getPic4() {
		setHD4();
		// RemoteUtils.getPicList("21", new WHTTHttpRequestCallBack() {
		//
		// @Override
		// public void result(Object obj) {
		// // TODO Auto-generated method stub
		// PicListEntity entity = (PicListEntity) obj;
		//
		// if (!entity.success) {
		//
		// } else {
		// mPicurlList4 = entity.list;
		// setHD4();
		// }
		//
		// }
		// });
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

	public void btnTab1(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		for (int i = 0; i < mEditList.size(); i++) {
			imm.hideSoftInputFromWindow(mEditList.get(i).getWindowToken(), 0);
		}
		mTextTab1.setTextColor(Color.parseColor("#f5a671"));
		mTextTab3.setTextColor(Color.parseColor("#757575"));
		mTextTab4.setTextColor(Color.parseColor("#757575"));
		mLayoutTab1.setVisibility(View.VISIBLE);
		mLayoutTab3.setVisibility(View.GONE);
		mLayoutTab4.setVisibility(View.GONE);
		if (mEntity1 == null) {
			getList1();
			getPic();
		}
	}

	public void btnTab3(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		for (int i = 0; i < mEditList.size(); i++) {
			imm.hideSoftInputFromWindow(mEditList.get(i).getWindowToken(), 0);
		}
		mTextTab3.setTextColor(Color.parseColor("#f5a671"));
		mTextTab1.setTextColor(Color.parseColor("#757575"));
		mTextTab4.setTextColor(Color.parseColor("#757575"));
		mLayoutTab3.setVisibility(View.VISIBLE);
		mLayoutTab1.setVisibility(View.GONE);
		mLayoutTab4.setVisibility(View.GONE);

		if (mEntity3 == null) {
			getList3();
			getPic3();
		}

	}

	public void btnTab4(View v) {

		mTextTab4.setTextColor(Color.parseColor("#f5a671"));
		mTextTab3.setTextColor(Color.parseColor("#757575"));
		mTextTab1.setTextColor(Color.parseColor("#757575"));
		mLayoutTab4.setVisibility(View.VISIBLE);
		mLayoutTab3.setVisibility(View.GONE);
		mLayoutTab1.setVisibility(View.GONE);
		setHD4();
		if (mEntity == null || mEntity.list.size() == 0) {
			getField();
			// getPic4();
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

	private void setHD2() {
		// 加入幻灯
		if (mPicurlList2.size() > 0) {
			tips2 = new ImageView[mPicurlList2.size()];
			Log.e("kkkkk", tips2.length + "");
			mGroup2.removeAllViews();
			for (int i = 0; i < tips2.length; i++) {
				ImageView imageView = new ImageView(mContext);
				imageView.setLayoutParams(new LayoutParams(
						Constants.width / 60, Constants.width / 60));
				tips2[i] = imageView;
				if (i == 0) {
					tips2[i].setBackgroundResource(R.drawable.diandiandian_w);
				} else {
					tips2[i].setBackgroundResource(R.drawable.diandiandian_g);
				}

				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						new ViewGroup.LayoutParams(Constants.width / 60,
								Constants.width / 60));
				layoutParams.leftMargin = Constants.width / 120;
				layoutParams.rightMargin = Constants.width / 120;
				mGroup2.addView(imageView, layoutParams);

			}
			if (mPicurlList2.size() == 1) {
				mImageViews2 = new ImageView[mPicurlList2.size()];
				mViewPager2.setAdapter(mMyAdapter2);
				for (int i = 0; i < mPicurlList2.size(); i++) {

					PicEntity entity = mPicurlList2.get(i);

					ImageView imageView = new ImageView(mContext);
					imageView.setScaleType(ScaleType.FIT_XY);

					mFinalBitmap.display(imageView, entity.pic);

					mImageViews2[i] = imageView;

					System.out.println("pic : " + Constants.URL_IMAGE
							+ entity.pic);

				}
			} else {
				mImageViews2 = new ImageView[mPicurlList2.size() * 2];
				mViewPager2.setAdapter(mMyAdapter2);
				for (int i = 0; i < mPicurlList2.size(); i++) {

					PicEntity entity = mPicurlList2.get(i);
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
					mImageViews2[i] = imageView;
					mImageViews2[i + mPicurlList2.size()] = imageView2;

				}
			}
			Log.e("mImageViews2.length", mMyAdapter2.getCount() + "");

			if (mPicurlList2.size() == 1) {
				mViewPager2.setCurrentItem(0);
			} else {
				mViewPager2.setCurrentItem(mImageViews2.length * 100
						+ mPicurlList2.size());
			}
			mMyAdapter2.notifyDataSetChanged();
			mMyAdapter2.notifyDataSetChanged();
		}
	}

	private void setHD4() {
		// 加入幻灯

		tips4 = new ImageView[1];
		Log.e("kkkkk", tips4.length + "");
		mGroup4.removeAllViews();
		for (int i = 0; i < tips4.length; i++) {
			ImageView imageView = new ImageView(mContext);
			imageView.setLayoutParams(new LayoutParams(Constants.width / 60,
					Constants.width / 60));
			tips4[i] = imageView;
			if (i == 0) {
				tips4[i].setBackgroundResource(R.drawable.diandiandian_w);
			} else {
				tips4[i].setBackgroundResource(R.drawable.diandiandian_g);
			}

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(Constants.width / 60,
							Constants.width / 60));
			layoutParams.leftMargin = Constants.width / 120;
			layoutParams.rightMargin = Constants.width / 120;
			mGroup4.addView(imageView, layoutParams);

			mImageViews4 = new ImageView[1];

			for (int ii = 0; ii < 1; ii++) {

				ImageView imageViewx = new ImageView(mContext);
				imageViewx.setScaleType(ScaleType.FIT_XY);

				// mFinalBitmap.display(imageView, entity.pic);

				imageViewx.setImageBitmap(mDefaultBitmap4);
				mImageViews4[i] = imageViewx;

			}
			mViewPager4.setAdapter(mMyAdapter4);

			mViewPager4.setCurrentItem(0);

			mMyAdapter4.notifyDataSetChanged();
			mMyAdapter4.notifyDataSetChanged();
		}
	}

	/**
	 * 
	 * @author whtt
	 * 
	 */
	public class MyAdapter2 extends PagerAdapter {

		@Override
		public int getCount() {
			int count = 0;
			if (mImageViews2 != null && mImageViews2.length == 1) {
				count = 1;
			} else if (mImageViews2 != null) {
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
			if (mImageViews2 == null || mImageViews2.length == 0) {
				return;
			}
			((MyViewPager) container).removeView(mImageViews2[position
					% mImageViews2.length]);

		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			if (mImageViews2 == null || mImageViews2.length == 0) {
				return null;
			}
			if (position == 0) {
				return mImageViews2[0];
			} else {
				try {
					((MyViewPager) container).addView(mImageViews2[position
							% mImageViews2.length], 0);
				} catch (Exception e) {
					// TODO: handle exception
				}

				return mImageViews2[position % mImageViews2.length];
			}
		}

	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground2(int selectItems) {
		for (int i = 0; i < tips2.length; i++) {
			if (i == selectItems) {
				tips2[i].setBackgroundResource(R.drawable.diandiandian_w);
			} else {
				tips2[i].setBackgroundResource(R.drawable.diandiandian_g);
			}
		}
	}

	/**
	 * 
	 * @author whtt
	 * 
	 */
	public class MyAdapter4 extends PagerAdapter {

		@Override
		public int getCount() {
			int count = 0;
			if (mImageViews4 != null && mImageViews4.length == 1) {
				count = 1;
			} else if (mImageViews4 != null) {
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
			if (mImageViews4 == null || mImageViews4.length == 0) {
				return;
			}
			((MyViewPager) container).removeView(mImageViews4[position
					% mImageViews4.length]);

		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			if (mImageViews4 == null || mImageViews4.length == 0) {
				return null;
			}
			if (position == 0) {
				return mImageViews4[0];
			} else {
				try {
					((MyViewPager) container).addView(mImageViews4[position
							% mImageViews4.length], 0);
				} catch (Exception e) {
					// TODO: handle exception
				}

				return mImageViews4[position % mImageViews4.length];
			}
		}

	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground4(int selectItems) {
		for (int i = 0; i < tips4.length; i++) {
			if (i == selectItems) {
				tips4[i].setBackgroundResource(R.drawable.diandiandian_w);
			} else {
				tips4[i].setBackgroundResource(R.drawable.diandiandian_g);
			}
		}
	}

	public void btnPhoto(View v) {

		Intent intent = new Intent();
		intent.setClass(mContext, ImgAddActivity.class);
		startActivity(intent);
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
		if (Constants.uploadEntity == null) {
			Constants.uploadEntity = new UploadEntity();
		}
		RemoteUtils.upDateAdd(mContentList, mNameList,
				Constants.uploadEntity.imgs, new WHTTHttpRequestCallBack() {

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
							for (int i = 0; i < mEditList.size(); i++) {
								mEditList.get(i).setText("");
							}

							Constants.addBitmap1 = null;
							Constants.addBitmap2 = null;
							Constants.addBitmap3 = null;
							Constants.addBitmap4 = null;
							Constants.addBitmap5 = null;
							Constants.uploadEntity = null;

						}

					}
				});
	}

	public void getTitleList() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);

		RemoteUtils.getTitle("2", new WHTTHttpRequestCallBack() {

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

	public void getTitleAndTypeList() {

		RemoteUtils.getTitleAndType(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				TitleListEntity entity = (TitleListEntity) obj;

				if (!entity.success) {

				} else {
					initColumn(entity);

				}

			}
		});
	}

	public void initColumn(final TitleListEntity entity) {
		mLayoutHeaderMain.removeAllViews();
		int num = entity.list.size() / 3;
		if (entity.list.size() % 3 > 0) {
			num += 1;
		}
		for (int i = 0; i < num; i++) {
			final int ii = i;

			// 加载幻灯下面的那个
			LinearLayout ViewHeaderX = (LinearLayout) LayoutInflater.from(
					mContext).inflate(R.layout.row_expand_header, null);
			CircleImageView pict1 = (CircleImageView) ViewHeaderX
					.findViewById(R.id.row_expand_header_imgv_1);
			TextView t1name1 = (TextView) ViewHeaderX
					.findViewById(R.id.row_expand_header_txt_name_1);

			CircleImageView pict2 = (CircleImageView) ViewHeaderX
					.findViewById(R.id.row_expand_header_imgv_2);
			TextView t1name2 = (TextView) ViewHeaderX
					.findViewById(R.id.row_expand_header_txt_name_2);
			CircleImageView pict3 = (CircleImageView) ViewHeaderX
					.findViewById(R.id.row_expand_header_imgv_3);
			TextView t1name3 = (TextView) ViewHeaderX
					.findViewById(R.id.row_expand_header_txt_name_3);
			LinearLayout t1layout1 = (LinearLayout) ViewHeaderX
					.findViewById(R.id.row_expand_header_layout_1);
			LinearLayout t1layout2 = (LinearLayout) ViewHeaderX
					.findViewById(R.id.row_expand_header_layout_2);
			LinearLayout t1layout3 = (LinearLayout) ViewHeaderX
					.findViewById(R.id.row_expand_header_layout_3);
			if ((ii * 3) < entity.list.size()) {
				t1layout1.setVisibility(View.VISIBLE);
				pict1.setImageBitmap(mDefaultBitmap2);
				if (!StringUtils.isBlank(entity.list.get(ii * 3).icon)) {

					mFinalBitmap.display(pict1, entity.list.get(ii * 3).icon,
							mDefaultBitmap2, mDefaultBitmap2);

				} else {
					pict1.setImageBitmap(mDefaultBitmap2);
				}
				t1name1.setText(entity.list.get(ii * 3).title);
				t1layout1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(mContext, PromotionActivity3.class);
						intent.putExtra("id", entity.list.get(ii * 3).id);
						intent.putExtra("title", entity.list.get(ii * 3).title);
						startActivity(intent);
					}
				});
			} else {
				t1layout1.setVisibility(View.INVISIBLE);
			}
			if ((ii * 3) + 1 < entity.list.size()) {
				t1layout2.setVisibility(View.VISIBLE);
				pict2.setImageBitmap(mDefaultBitmap2);
				if (!StringUtils.isBlank(entity.list.get(ii * 3 + 1).icon)) {

					mFinalBitmap.display(pict2,
							entity.list.get(ii * 3 + 1).icon, mDefaultBitmap2,
							mDefaultBitmap2);

				} else {
					pict2.setImageBitmap(mDefaultBitmap2);
				}
				t1name2.setText(entity.list.get(ii * 3 + 1).title);
				t1layout2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(mContext, PromotionActivity3.class);
						intent.putExtra("id", entity.list.get(ii * 3 + 1).id);
						intent.putExtra("title",
								entity.list.get(ii * 3 + 1).title);
						startActivity(intent);
					}
				});
			} else {
				t1layout2.setVisibility(View.GONE);
			}
			if ((ii * 3) + 2 < entity.list.size()) {
				t1layout3.setVisibility(View.VISIBLE);
				pict3.setImageBitmap(mDefaultBitmap2);
				if (!StringUtils.isBlank(entity.list.get(ii * 3 + 2).icon)) {

					mFinalBitmap.display(pict3,
							entity.list.get(ii * 3 + 2).icon, mDefaultBitmap2,
							mDefaultBitmap2);

				} else {
					pict3.setImageBitmap(mDefaultBitmap2);
				}
				t1name3.setText(entity.list.get(ii * 3 + 2).title);
				t1layout3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(mContext, PromotionActivity3.class);
						intent.putExtra("id", entity.list.get(ii * 3 + 2).id);
						intent.putExtra("title",
								entity.list.get(ii * 3 + 2).title);
						startActivity(intent);
					}
				});
			} else {
				t1layout3.setVisibility(View.GONE);
			}
			// pict1.setImageBitmap(bitmap1);
			//
			// pict2.setImageBitmap(bitmap2);
			// t1layout1.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// Intent intent = new Intent();
			// intent.setClass(mContext, PromotionActivity3.class);
			// intent.putExtra("id", t1Id1);
			// intent.putExtra("title", "装饰画下载");
			// startActivity(intent);
			// }
			// });
			// t1layout2.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// Intent intent = new Intent();
			// intent.setClass(mContext, PromotionActivity3.class);
			// intent.putExtra("id", t1Id2);
			// intent.putExtra("title", "音乐下载");
			// startActivity(intent);
			// }
			// });
			mLayoutHeaderMain.addView(ViewHeaderX);
		}

	}

	public void getField() {

		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.getFieldListSJ(new WHTTHttpRequestCallBack() {

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

	private List<ImageView> mBgList = new ArrayList<ImageView>();
	private List<TextView> mTextList = new ArrayList<TextView>();

	private void initField() {

		Log.e("mEntity.list.size() * 50 + 110",
				(mEntity.list.size() * 50 + 110) + "");

		mLayoutBg.setLayoutParams(new LinearLayout.LayoutParams(Constants.width
				- DisplayUtil.dip2px(mContext, 20), DisplayUtil.dip2px(
				mContext, mEntity.list.size() * 50 + 110)));
		for (int i = 0; i < mEntity.list.size(); i++) {
			LinearLayout view = (LinearLayout) LayoutInflater.from(mContext)
					.inflate(R.layout.row_field_list, null);
			TextView name = (TextView) view
					.findViewById(R.id.row_field_txt_name);
			EditText content = (EditText) view
					.findViewById(R.id.row_field_edit_content);
			ImageView bg = (ImageView) view
					.findViewById(R.id.row_field_iv_content_bg);
			name.setText(mEntity.list.get(i).title + ":");
			content.setHint("请输入" + mEntity.list.get(i).title + "!");
			mTextList.add(name);
			mBgList.add(bg);
			mEditList.add(content);

			mLayoutMain.addView(view);

		}

		if (mEntity.list.size() > 0) {
			int x = 0;
			int p = 0;
			for (int i = 0; i < mEntity.list.size(); i++) {
				if (mEntity.list.get(i).title.length() > x) {
					p = i;
					x = mEntity.list.get(i).title.length();
				}
			}
			final TextView xTextView = mTextList.get(p);
			ViewTreeObserver mViewTreeObserver = xTextView
					.getViewTreeObserver();
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
}
