package com.uesugi.mumen.top;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uesugi.mumen.CircleImageView;
import com.uesugi.mumen.R;
import com.uesugi.mumen.adapter.ShopAdapter;
import com.uesugi.mumen.entity.TopListEntity;
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

public class TopActivity extends FinalActivity {

	private final static String TAG = "TopActivity";

	private Context mContext = null;

	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;

	@ViewInject(id = R.id.top_txt_tab1, click = "btnTab1")
	private TextView mTextTab1;
	@ViewInject(id = R.id.top_txt_tab2, click = "btnTab2")
	private TextView mTextTab2;
	@ViewInject(id = R.id.top_txt_tab3, click = "btnTab3")
	private TextView mTextTab3;
	@ViewInject(id = R.id.top_txt_tab4, click = "btnTab4")
	private TextView mTextTab4;
	@ViewInject(id = R.id.top_txt_tab5, click = "btnTab5")
	private TextView mTextTab5;

	@ViewInject(id = R.id.top_layout_1)
	private RelativeLayout mLayoutTab1;
	@ViewInject(id = R.id.top_layout_2)
	private RelativeLayout mLayoutTab2;

	@ViewInject(id = R.id.top_txt_paihang, click = "btnList")
	private TextView mTextPaihang;
	@ViewInject(id = R.id.top_txt_name)
	private TextView mTextName;
	@ViewInject(id = R.id.top_txt_rank)
	private TextView mTextRank;
	@ViewInject(id = R.id.top_imgv_icon)
	private CircleImageView mImgVIcon;

	private String index = "1";

	/**
	 * 装点点的ImageView数组
	 */
	private TextView[] num1;
	private TextView[] name1;
	private ImageView[] icon1;
	private ViewGroup mGroup1;

	private TopListEntity mEntity1 = null;

	private TopListEntity mEntity2 = null;

	private TopListEntity mEntity3 = null;

	private TopListEntity mEntity4 = null;

	private TopListEntity mEntity5 = null;

	private FinalBitmap mFinalBitmap;

	private Bitmap mDefaultBitmap;
	private Bitmap mDefaultBitmap2;

	private ShowAlertDialog mDialog = null;

	private ShopAdapter mAdapter3;
	private ListView mListView3;
	private PullDownView mPullDownView3;
	private int visibleLastIndex3;
	private boolean isLoading3 = false;
	private int p3 = 0;
	private boolean mFlagLoading3 = false;

	private RelativeLayout mViewFoot3 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_top);

		mContext = this;
		initView();
		mTextPaihang.setText("周度销量排行榜");
		getList1();
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

		mTextTopTitle.setText("Top排行榜");
		mGroup1 = (ViewGroup) findViewById(R.id.viewGroup_top_1);

		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView3 = (PullDownView) findViewById(R.id.top_pull_down_view2);

		mPullDownView3.setOnPullDownListener(new OnPullDownListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mAdapter3.clearAll();
				mFlagLoading3 = false;
				p3 = 0;
				mViewFoot3.setVisibility(View.GONE);
				getList5();
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
		mListView3.addFooterView(ViewFoot3);

		mListView3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

			}
		});
		mListView3.setDividerHeight(0);
		// mAdapter = new NewsAdapter(mContext, mFid);

		mAdapter3 = new ShopAdapter(mContext, mFinalBitmap);
		mListView3.setAdapter(mAdapter3);

		mListView3.setOnScrollListener(mOnScrollListener3);

		mPullDownView3.enableAutoFetchMore(false, 1, false);

	}

	private void setGroup1(TopListEntity entity) {
		mGroup1.removeAllViews();
		num1 = new TextView[entity.list.size()];
		name1 = new TextView[entity.list.size()];
		icon1 = new ImageView[entity.list.size()];
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				new ViewGroup.LayoutParams(
						(int) ((Constants.width - DisplayUtil.dip2px(mContext,
								20)) / (float) (entity.list.size())),
						DisplayUtil.dip2px(mContext, 60)));
		for (int i = 0; i < entity.list.size(); i++) {
			final int ii = i;
			LinearLayout view1 = (LinearLayout) LayoutInflater.from(mContext)
					.inflate(R.layout.row_top_h, null);
			TextView namex1 = (TextView) view1
					.findViewById(R.id.top_h_txt_name);
			TextView numx1 = (TextView) view1.findViewById(R.id.top_h_txt_num);
			ImageView iconx1 = (ImageView) view1
					.findViewById(R.id.top_h_imgv_icon);
			view1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					setImageBackground1(ii);
				}
			});
			if (i == 0) {
				namex1.setText("one");
			} else if (i == 1) {
				namex1.setText("two");
			} else if (i == 2) {
				namex1.setText("three");
			} else if (i == 3) {
				namex1.setText("four");
			} else if (i == 4) {
				namex1.setText("five");
			} else if (i == 5) {
				namex1.setText("six");
			} else if (i == 6) {
				namex1.setText("seven");
			} else if (i == 7) {
				namex1.setText("eight");
			} else if (i == 8) {
				namex1.setText("nine");
			} else if (i == 9) {
				namex1.setText("ten");
			}

			numx1.setText((i + 1) + "");
			num1[i] = numx1;
			name1[i] = namex1;
			icon1[i] = iconx1;

			mGroup1.addView(view1, layoutParams);
		}

		setImageBackground1(0);
	}

	public void setImageBackground1(int selectItems) {
		for (int i = 0; i < name1.length; i++) {
			if (i == selectItems) {
				num1[i].setTextColor(Color.parseColor("#f5a671"));
				name1[i].setVisibility(View.VISIBLE);
				icon1[i].setVisibility(View.VISIBLE);
			} else {
				num1[i].setTextColor(Color.parseColor("#757575"));
				name1[i].setVisibility(View.GONE);
				icon1[i].setVisibility(View.GONE);
			}
		}
		mImgVIcon.setImageBitmap(null);
		mTextName.setText("");
		if (index.equals("1")) {
			mImgVIcon.setImageBitmap(mDefaultBitmap);
			mTextName.setText(mEntity1.list.get(selectItems).name);

			if (!StringUtils.isBlank(mEntity1.list.get(selectItems).icon)) {

				mFinalBitmap.display(mImgVIcon,
						mEntity1.list.get(selectItems).icon, mDefaultBitmap,
						mDefaultBitmap);

			} else {
				mImgVIcon.setImageBitmap(mDefaultBitmap);
			}
		} else if (index.equals("2")) {
			mImgVIcon.setImageBitmap(mDefaultBitmap);
			mTextName.setText(mEntity2.list.get(selectItems).name);

			if (!StringUtils.isBlank(mEntity2.list.get(selectItems).icon)) {

				mFinalBitmap.display(mImgVIcon,
						mEntity2.list.get(selectItems).icon, mDefaultBitmap,
						mDefaultBitmap);

			} else {
				mImgVIcon.setImageBitmap(mDefaultBitmap);
			}
		} else if (index.equals("3")) {
			mImgVIcon.setImageBitmap(mDefaultBitmap);
			mTextName.setText(mEntity3.list.get(selectItems).name);

			if (!StringUtils.isBlank(mEntity3.list.get(selectItems).icon)) {

				mFinalBitmap.display(mImgVIcon,
						mEntity3.list.get(selectItems).icon, mDefaultBitmap,
						mDefaultBitmap);

			} else {
				mImgVIcon.setImageBitmap(mDefaultBitmap);
			}
		} else if (index.equals("4")) {
			mImgVIcon.setImageBitmap(mDefaultBitmap);
			mTextName.setText(mEntity4.list.get(selectItems).name);

			if (!StringUtils.isBlank(mEntity4.list.get(selectItems).icon)) {

				mFinalBitmap.display(mImgVIcon,
						mEntity4.list.get(selectItems).icon, mDefaultBitmap,
						mDefaultBitmap);

			} else {
				mImgVIcon.setImageBitmap(mDefaultBitmap);
			}
		}

		if (selectItems == 0) {
			mTextRank.setText("冠军");
		} else if (selectItems == 1) {
			mTextRank.setText("亚军");
		} else if (selectItems == 2) {
			mTextRank.setText("季军");
		} else if (selectItems == 3) {
			mTextRank.setText("第4名");
		} else if (selectItems == 4) {
			mTextRank.setText("第5名");
		} else if (selectItems == 5) {
			mTextRank.setText("第6名");
		} else if (selectItems == 6) {
			mTextRank.setText("第7名");
		} else if (selectItems == 7) {
			mTextRank.setText("第8名");
		} else if (selectItems == 8) {
			mTextRank.setText("第9名");
		} else if (selectItems == 9) {
			mTextRank.setText("第10名");
		}
	}

	public void btnTab1(View v) {
		index = "1";
		mTextTab1.setTextColor(Color.parseColor("#f5a671"));
		mTextTab2.setTextColor(Color.parseColor("#757575"));
		mTextTab3.setTextColor(Color.parseColor("#757575"));
		mTextTab4.setTextColor(Color.parseColor("#757575"));
		mTextTab5.setTextColor(Color.parseColor("#757575"));
		mLayoutTab1.setVisibility(View.VISIBLE);
		mLayoutTab2.setVisibility(View.GONE);
		mTextPaihang.setText("周度销量排行榜");
		if (mEntity1 == null) {
			getList1();
		} else {

			setGroup1(mEntity1);
		}
	}

	public void btnTab2(View v) {
		index = "2";
		mTextTab2.setTextColor(Color.parseColor("#f5a671"));
		mTextTab1.setTextColor(Color.parseColor("#757575"));
		mTextTab3.setTextColor(Color.parseColor("#757575"));
		mTextTab4.setTextColor(Color.parseColor("#757575"));
		mTextTab5.setTextColor(Color.parseColor("#757575"));
		mLayoutTab1.setVisibility(View.VISIBLE);
		mLayoutTab2.setVisibility(View.GONE);
		mTextPaihang.setText("月度销量排行榜");
		if (mEntity2 == null) {
			getList2();
		} else {

			setGroup1(mEntity2);
		}
	}

	public void btnTab3(View v) {
		index = "3";
		mTextTab3.setTextColor(Color.parseColor("#f5a671"));
		mTextTab2.setTextColor(Color.parseColor("#757575"));
		mTextTab1.setTextColor(Color.parseColor("#757575"));
		mTextTab4.setTextColor(Color.parseColor("#757575"));
		mTextTab5.setTextColor(Color.parseColor("#757575"));
		mLayoutTab1.setVisibility(View.VISIBLE);
		mLayoutTab2.setVisibility(View.GONE);
		mTextPaihang.setText("季度销量排行榜");
		if (mEntity3 == null) {
			getList3();
		} else {

			setGroup1(mEntity3);
		}
	}

	public void btnTab4(View v) {
		index = "4";
		mTextTab4.setTextColor(Color.parseColor("#f5a671"));
		mTextTab2.setTextColor(Color.parseColor("#757575"));
		mTextTab3.setTextColor(Color.parseColor("#757575"));
		mTextTab1.setTextColor(Color.parseColor("#757575"));
		mTextTab5.setTextColor(Color.parseColor("#757575"));
		mLayoutTab1.setVisibility(View.VISIBLE);
		mLayoutTab2.setVisibility(View.GONE);
		mTextPaihang.setText("年度销量排行榜");
		if (mEntity4 == null) {
			getList4();
		} else {

			setGroup1(mEntity4);
		}
	}

	public void btnList(View v) {
		Intent intent = new Intent();
		intent.setClass(mContext, TopListActivity.class);
		intent.putExtra("index", index);
		startActivity(intent);
	}

	public void btnTab5(View v) {

		mTextTab5.setTextColor(Color.parseColor("#f5a671"));
		mTextTab2.setTextColor(Color.parseColor("#757575"));
		mTextTab3.setTextColor(Color.parseColor("#757575"));
		mTextTab4.setTextColor(Color.parseColor("#757575"));
		mTextTab1.setTextColor(Color.parseColor("#757575"));
		mLayoutTab1.setVisibility(View.GONE);
		mLayoutTab2.setVisibility(View.VISIBLE);
		if (mEntity5 == null) {
			getList5();
		}
	}

	public void getList1() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.getTopW(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mDialog.dismissProgressDlg();
				TopListEntity entity = (TopListEntity) obj;

				if (!entity.success) {
					if (entity.resultCode.equals("1310")) {

					} else {

					}
				} else {
					mEntity1 = entity;
					setGroup1(mEntity1);
				}

			}
		});
	}

	public void getList2() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.getTopM(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mDialog.dismissProgressDlg();
				TopListEntity entity = (TopListEntity) obj;

				if (!entity.success) {
					if (entity.resultCode.equals("1310")) {

					} else {

					}
				} else {
					mEntity2 = entity;
					setGroup1(mEntity2);
				}

			}
		});
	}

	public void getList3() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.getTopQ(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mDialog.dismissProgressDlg();
				TopListEntity entity = (TopListEntity) obj;

				if (!entity.success) {
					if (entity.resultCode.equals("1310")) {

					} else {

					}
				} else {
					mEntity3 = entity;
					setGroup1(mEntity3);
				}

			}
		});
	}

	public void getList4() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.getTopY(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mDialog.dismissProgressDlg();
				TopListEntity entity = (TopListEntity) obj;

				if (!entity.success) {
					if (entity.resultCode.equals("1310")) {

					} else {

					}
				} else {
					mEntity4 = entity;
					setGroup1(mEntity4);
				}

			}
		});
	}

	public void getList5() {
		mDialog.showProgressDlg(Constants.MESSAGE_PROGRESS);
		RemoteUtils.getTopW(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mDialog.dismissProgressDlg();
				TopListEntity entity = (TopListEntity) obj;

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
					mEntity5 = entity;
					mAdapter3.setData(mEntity5.list);
					if (mEntity5.list.size() == 20) {
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
