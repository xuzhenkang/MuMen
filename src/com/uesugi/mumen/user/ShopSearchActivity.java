package com.uesugi.mumen.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.uesugi.mumen.R;
import com.uesugi.mumen.adapter.MyShopSearchAdapter;
import com.uesugi.mumen.entity.TopEntity;
import com.uesugi.mumen.entity.TopListEntity;
import com.uesugi.mumen.pulldown.PullDownView;
import com.uesugi.mumen.pulldown.PullDownView.OnPullDownListener;
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

public class ShopSearchActivity extends FinalActivity {

	private final static String TAG = "ShopActivity";

	private Context mContext = null;
	@ViewInject(id = R.id.top_view_btn_left, click = "btnLeft")
	private ImageButton mTopBtnLeft = null;

	private TopListEntity mEntity4 = null;
	private MyShopSearchAdapter mAdapter4;
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

	@ViewInject(id = R.id.shop_layout_info_bg, click = "btnInfoBg")
	private LinearLayout mLayoutInfoBg;
	@ViewInject(id = R.id.shop_layout_info, click = "btnInfo")
	private LinearLayout mLayoutInfo;
	@ViewInject(id = R.id.shop_txt_x, click = "btnInfoBg")
	private TextView mTextX;

	@ViewInject(id = R.id.shop_txt_date)
	private TextView mTextDate;
	@ViewInject(id = R.id.shop_edit_search)
	private EditText mEditSearch;

	private String mKeyword = "";

	@ViewInject(id = R.id.shop_layout_info_main)
	private LinearLayout mLayoutInfoMain;
	@ViewInject(id = R.id.shop_layout_info_scr)
	private ScrollView mLayoutInfoScr;

	private List<TextView> mTextNameViews = new ArrayList<TextView>();
	private List<TextView> mTextInfoViews = new ArrayList<TextView>();
	private List<TextView> mTextUnitViews = new ArrayList<TextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shop_search);

		mContext = this;

		initView();
		// getList4();
	}

	public void btnLeft(View v) {
		finish();
	}

	private void initView() {
		// mTopBtnRight.setText("全部区域");
		mEditSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		mEditSearch
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {

						if (actionId == EditorInfo.IME_ACTION_SEARCH
								|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))

						{

							// do something;
							if (mEditSearch.getText().toString().trim()
									.length() > 0) {
								mKeyword = mEditSearch.getText().toString();
								mAdapter4.clearAll();
								mFlagLoading4 = false;
								p4 = 0;

								getList4();
							}
							return true;

						}

						return false;

					}

				});
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

		mAdapter4 = new MyShopSearchAdapter(mContext, mFinalBitmap, this);
		mListView4.setAdapter(mAdapter4);

		mListView4.setOnScrollListener(mOnScrollListener4);

		mPullDownView4.enableAutoFetchMore(false, 1, false);

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

		mPullDownView4.notifyDidLoad();

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
		RemoteUtils.getShopListKey(mKeyword, new WHTTHttpRequestCallBack() {

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

	public void btnInfoBg(View v) {

		mLayoutInfoBg.setVisibility(View.GONE);

	}

	public void showInfoBg(TopEntity entity) {

		mLayoutInfoBg.setVisibility(View.VISIBLE);

		if (mTextNameViews.size() == 0) {
			for (int i = 0; i < entity.report.size(); i++) {
				LinearLayout view = (LinearLayout) LayoutInflater
						.from(mContext).inflate(R.layout.row_shop_sxhb_list,
								null);
				TextView name = (TextView) view
						.findViewById(R.id.row_shop_xshb_txt_name);
				TextView info = (TextView) view
						.findViewById(R.id.row_shop_xshb_txt_info);
				TextView unit = (TextView) view
						.findViewById(R.id.row_shop_xshb_txt_unit);
				name.setText(entity.report.get(i).title);
				if (StringUtils.isBlank(entity.report.get(i).value)) {
					info.setText("0");
				} else {
					info.setText(entity.report.get(i).value);
				}
				unit.setText(entity.report.get(i).unit);

				mTextNameViews.add(name);
				mTextInfoViews.add(info);
				mTextUnitViews.add(unit);

				mLayoutInfoMain.addView(view);

			}
		} else {
			mLayoutInfoScr.scrollTo(0, 0);
			for (int i = 0; i < mTextNameViews.size(); i++) {
				mTextNameViews.get(i).setText(entity.report.get(i).title);
				if (StringUtils.isBlank(entity.report.get(i).value)) {
					mTextInfoViews.get(i).setText("0");
				} else {
					mTextInfoViews.get(i).setText(entity.report.get(i).value);
				}
				mTextUnitViews.get(i).setText(entity.report.get(i).unit);
			}
		}
	}

}
