package com.uesugi.mumen.user;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uesugi.mumen.GuangGaoActivity;
import com.uesugi.mumen.R;
import com.uesugi.mumen.adapter.MsgAdapter;
import com.uesugi.mumen.entity.MsgDianListEntity;
import com.uesugi.mumen.pulldown.PullDownView;
import com.uesugi.mumen.pulldown.PullDownView.OnPullDownListener;
import com.uesugi.mumen.utils.Constants;
import com.uesugi.mumen.utils.RemoteUtils;
import com.uesugi.mumen.utils.UserPreferences;
import com.uesugi.mumen.utils.WHTTHttpRequestCallBack;

public class MsgActivity extends FinalActivity {

	private Context mContext = null;
	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;
	@ViewInject(id = R.id.top_view_btn_left, click = "btnLeft")
	private ImageButton mTopBtnLeft;

	private MsgAdapter mAdapter;
	private ListView mListView;
	private PullDownView mPullDownView;
	private int visibleLastIndex;
	private boolean isLoading = false;
	private int p = 0;
	private boolean mFlagLoading = false;

	private RelativeLayout mViewFoot = null;

	private MsgDianListEntity mEntity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_msg);
		mContext = this;
		initView();
		getList();
	}

	public void btnLeft(View v) {
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void initView() {
		mTopBtnLeft.setVisibility(View.VISIBLE);
		mTextTopTitle.setText("消息通知");

		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView = (PullDownView) findViewById(R.id.msg_listview);

		mPullDownView.setOnPullDownListener(new OnPullDownListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mAdapter.clearAll();
				mFlagLoading = false;
				p = 0;
				mViewFoot.setVisibility(View.GONE);
				getList();
			}

			@Override
			public void onMore() {
				// TODO Auto-generated method stub
				mPullDownView.notifyDidMore();
			}
		});
		mListView = mPullDownView.getListView();
		mPullDownView.setBackgroundResource(R.color.transparent);
		mListView.setBackgroundResource(R.color.transparent);

		mListView.setSelector(R.color.transparent);
		// 去掉ListView 边缘模糊
		mListView.setFadingEdgeLength(0);
		// 加载翻页样式
		RelativeLayout ViewFoot = (RelativeLayout) LayoutInflater
				.from(mContext).inflate(R.layout.layout_list_foot, null);
		mViewFoot = (RelativeLayout) ViewFoot.findViewById(R.id.foot_view);
		mViewFoot.setVisibility(View.GONE);
		mListView.addFooterView(ViewFoot);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position < mAdapter.getCount()) {
					Intent intent = new Intent();
					intent.setClass(mContext, GuangGaoActivity.class);
					intent.putExtra("title",
							mAdapter.mListEntity.get(position).title);
					intent.putExtra("url",
							mAdapter.mListEntity.get(position).url);
					startActivity(intent);
				}
			}
		});
		mListView.setDividerHeight(0);
		// mAdapter = new NewsAdapter(mContext, mFid);

		mAdapter = new MsgAdapter(mContext);
		mListView.setAdapter(mAdapter);

		mListView.setOnScrollListener(mOnScrollListener);

		mPullDownView.enableAutoFetchMore(false, 1, false);

	}

	public void getList() {
		p += 1;
		RemoteUtils.getMsg("", p + "", "20", new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				MsgDianListEntity entity = (MsgDianListEntity) obj;
				isLoading = false;
				mPullDownView.notifyDidLoad();
				try {
					mPullDownView.notifyDidRefresh();
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (!entity.success) {

				} else {
					long tsLong = System.currentTimeMillis() / 1000;
					String ts = tsLong + "";
					UserPreferences.saveMsgTimePref(mContext, ts,
							Constants.entityUser.id);
					mEntity=entity;
					mAdapter.setData(mEntity.list);
					if (mEntity.list.size() == 20) {
						mFlagLoading = true;
						mViewFoot.setVisibility(View.VISIBLE);

					} else {
						mFlagLoading = false;
						mViewFoot.setVisibility(View.GONE);
					}

				}

			}
		});
	}

	private AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			int itemsLastIndex = mAdapter.getCount();

			if (itemsLastIndex >= 0) {
				Log.e("xxxxxxxx", visibleLastIndex + "///" + itemsLastIndex);
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& visibleLastIndex == itemsLastIndex
						&& isLoading == false && mFlagLoading) {

					// 翻页请求
					isLoading = true;
					Log.e("OnScrollListener6", "yes");
					getList();
				}

			}

		}

		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			// if (mFid.equals("1")) {
			visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
			// } else {
			// visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
			// }
		}
	};

}
