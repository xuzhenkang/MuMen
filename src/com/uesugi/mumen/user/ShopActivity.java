package com.uesugi.mumen.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uesugi.mumen.R;
import com.uesugi.mumen.adapter.MyShopAdapter;
import com.uesugi.mumen.adapter.SearchAdapter;
import com.uesugi.mumen.entity.AreaEntity;
import com.uesugi.mumen.entity.AreaListEntity;
import com.uesugi.mumen.entity.TopEntity;
import com.uesugi.mumen.entity.TopListEntity;
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

public class ShopActivity extends FinalActivity {

	private final static String TAG = "ShopActivity";

	private Context mContext = null;
	@ViewInject(id = R.id.top_view_btn_left, click = "btnLeft")
	private ImageButton mTopBtnLeft = null;
	@ViewInject(id = R.id.top_view_textbtn_right, click = "btnRight")
	private Button mTopBtnRight;

	private TopListEntity mEntity4 = null;
	private MyShopAdapter mAdapter4;
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
	private String mId = "";

	@ViewInject(id = R.id.shop_layout_area_bg, click = "btnAreaBg")
	private LinearLayout mLayoutAreaBg;
	@ViewInject(id = R.id.shop_listview_area)
	private ListView mListViewArea;
	private SearchAdapter mAdapterArea;
	private AreaListEntity mAreaEntity = null;

	@ViewInject(id = R.id.shop_layout_info_bg, click = "btnInfoBg")
	private LinearLayout mLayoutInfoBg;
	@ViewInject(id = R.id.shop_layout_info, click = "btnInfo")
	private LinearLayout mLayoutInfo;
	@ViewInject(id = R.id.shop_txt_x, click = "btnInfoBg")
	private TextView mTextX;
	@ViewInject(id = R.id.shop_txt_info1)
	private TextView mTextInfo1;
	@ViewInject(id = R.id.shop_txt_info2)
	private TextView mTextInfo2;
	@ViewInject(id = R.id.shop_txt_info3)
	private TextView mTextInfo3;
	@ViewInject(id = R.id.shop_txt_info4)
	private TextView mTextInfo4;
	@ViewInject(id = R.id.shop_txt_info5)
	private TextView mTextInfo5;

	@ViewInject(id = R.id.shop_txt_search, click = "btnSearch")
	private TextView mTextSearch;

	@ViewInject(id = R.id.shop_txt_date)
	private TextView mTextDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop);

		mContext = this;

		initView();
		getList4();
	}

	public void btnLeft(View v) {
		finish();
	}

	public void btnSearch(View v) {
		Intent intent = new Intent();
		intent.setClass(mContext, ShopSearchActivity.class);
		startActivity(intent);
	}

	public void btnRight(View v) {
		if (mLayoutAreaBg.isShown()) {
			mLayoutAreaBg.setVisibility(View.GONE);
		} else {
			mLayoutAreaBg.setVisibility(View.VISIBLE);
			if (mAreaEntity == null) {
				getArea();
			}
		}
	}

	private void initView() {
		mTopBtnRight.setText("全部区域");

		mTopBtnLeft.setVisibility(View.VISIBLE);
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

		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView4 = (PullDownView) findViewById(R.id.shop_pull_down_view4);

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
				// if (position < mAdapter4.mListEntity.size()) {
				// Intent intent = new Intent();
				// intent.setClass(mContext, GuangGaoActivity.class);
				// intent.putExtra("title",
				// mAdapter4.mListEntity.get(position).title);
				// intent.putExtra("url",
				// mAdapter4.mListEntity.get(position).url);
				// startActivity(intent);
				// }
			}
		});
		mListView4.setDividerHeight(0);
		// mAdapter = new NewsAdapter(mContext, mFid);

		mAdapter4 = new MyShopAdapter(mContext, mFinalBitmap, this);
		mListView4.setAdapter(mAdapter4);

		mListView4.setOnScrollListener(mOnScrollListener4);

		mPullDownView4.enableAutoFetchMore(false, 1, false);

		mListViewArea.setBackgroundResource(R.color.transparent);
		mListViewArea.setSelector(R.color.transparent);
		mListViewArea.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mId = mAdapterArea.mListEntity.get(position).id;
				mTopBtnRight.setText(mAdapterArea.mListEntity.get(position).area);
				mLayoutAreaBg.setVisibility(View.GONE);
				mAdapter4.clearAll();
				mFlagLoading4 = false;
				p4 = 0;
				mViewFoot4.setVisibility(View.GONE);
				getList4();
			}
		});
		mListViewArea.setDividerHeight(0);
		mAdapterArea = new SearchAdapter(mContext, mFinalBitmap);
		mListViewArea.setAdapter(mAdapterArea);

		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd");
		String m = formatter.format(date);
		String d = formatter2.format(date);

		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy.MM.dd");

		String ddd = formatter3.format(date);
		mTextDate.setText(m + "月" + d + "日    数据更新日期：" + ddd);

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
		RemoteUtils.getShopList(mId, new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mDialog.dismissProgressDlg();
				TopListEntity entity = (TopListEntity) obj;
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

	public void btnAreaBg(View v) {

		mLayoutAreaBg.setVisibility(View.GONE);

	}

	public void btnInfoBg(View v) {

		mLayoutInfoBg.setVisibility(View.GONE);

	}

	public void showInfoBg(TopEntity entity) {

		mLayoutInfoBg.setVisibility(View.VISIBLE);
		mTextInfo1.setText(entity.jdkh);
		mTextInfo2.setText(entity.yxkh);
		mTextInfo3.setText(entity.cjkh);
		mTextInfo4.setText(entity.fhb);
		mTextInfo5.setText(entity.proceeds);
	}

	public void getArea() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);

		RemoteUtils.getArea(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mDialog.dismissProgressDlg();
				AreaListEntity entity = (AreaListEntity) obj;

				if (!entity.success) {
					if (entity.resultCode.equals("1310")) {

					} else {

					}
				} else {
					AreaEntity entity2 = new AreaEntity();
					entity2.id = "";
					entity2.area = "全部区域";
					entity.list.add(entity2);
					mAreaEntity = entity;
					mAdapterArea.setData(mAreaEntity.list);

				}

			}
		});
	}
}
