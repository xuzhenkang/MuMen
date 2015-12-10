package com.uesugi.mumen.top;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uesugi.mumen.R;
import com.uesugi.mumen.adapter.TopListAdapter;
import com.uesugi.mumen.entity.TopListEntity;
import com.uesugi.mumen.utils.RemoteUtils;
import com.uesugi.mumen.utils.WHTTHttpRequestCallBack;

public class TopListActivity extends FinalActivity implements
		OnItemClickListener {

	private Context mContext = null;
	@ViewInject(id = R.id.top_view_title)
	private TextView mTextTopTitle;
	@ViewInject(id = R.id.top_view_btn_left, click = "btnLeft")
	private ImageButton mTopBtnLeft;

	@ViewInject(id = R.id.data_loading)
	RelativeLayout mLayoutLoading;
	@ViewInject(id = R.id.data_loading_img)
	ImageView mImgVLoading;
	private AnimationDrawable animaition;

	@ViewInject(id = R.id.toplist_listview)
	ListView mListView;
	private TopListAdapter mAdapter = null;

	private int pageTotal = 0;
	private int pageCurrent = 0;
	private boolean isLoading = false;

	private String index;
	private TopListEntity mEntity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_toplist);
		mContext = this;
		index = getIntent().getStringExtra("index");
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
		if (index.equals("1")) {
			mTextTopTitle.setText("周度销量排行");
		} else if (index.equals("2")) {
			mTextTopTitle.setText("月度销量排行");
		} else if (index.equals("3")) {
			mTextTopTitle.setText("季度销量排行");
		} else if (index.equals("4")) {
			mTextTopTitle.setText("年度销量排行");
		}
		mImgVLoading.setBackgroundResource(R.anim.anim_loading);
		animaition = (AnimationDrawable) mImgVLoading.getBackground();
		animaition.setOneShot(false);
		if (animaition.isRunning())// 是否正在运行？

		{
			animaition.stop();// 停止

		}
		animaition.start();// 启动

		mListView.setBackgroundResource(R.color.transparent);
		mListView.setSelector(R.color.transparent);
		mListView.setOnItemClickListener(this);
		mListView.setDividerHeight(0);
		mAdapter = new TopListAdapter(mContext);
		mListView.setAdapter(mAdapter);

	}

	public void getList() {
		if (index.equals("1")) {
			getList1();
		} else if (index.equals("2")) {
			getList2();
		} else if (index.equals("3")) {
			getList3();
		} else if (index.equals("4")) {
			getList4();
		}
	}

	public void getList1() {
		mLayoutLoading.setVisibility(View.VISIBLE);
		RemoteUtils.getTopW(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mLayoutLoading.setVisibility(View.GONE);
				TopListEntity entity = (TopListEntity) obj;

				if (!entity.success) {
					if (entity.resultCode.equals("1310")) {

					} else {

					}
				} else {
					mEntity = entity;
					mAdapter.setData(mEntity.list);
				}

			}
		});
	}

	public void getList2() {
		mLayoutLoading.setVisibility(View.VISIBLE);
		RemoteUtils.getTopM(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mLayoutLoading.setVisibility(View.GONE);
				TopListEntity entity = (TopListEntity) obj;

				if (!entity.success) {
					if (entity.resultCode.equals("1310")) {

					} else {

					}
				} else {
					mEntity = entity;
					mAdapter.setData(mEntity.list);
				}

			}
		});
	}

	public void getList3() {
		mLayoutLoading.setVisibility(View.VISIBLE);
		RemoteUtils.getTopQ(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mLayoutLoading.setVisibility(View.GONE);
				TopListEntity entity = (TopListEntity) obj;

				if (!entity.success) {
					if (entity.resultCode.equals("1310")) {

					} else {

					}
				} else {
					mEntity = entity;
					mAdapter.setData(mEntity.list);
				}

			}
		});
	}

	public void getList4() {
		mLayoutLoading.setVisibility(View.VISIBLE);
		RemoteUtils.getTopY(new WHTTHttpRequestCallBack() {

			@Override
			public void result(Object obj) {
				// TODO Auto-generated method stub
				mLayoutLoading.setVisibility(View.GONE);
				TopListEntity entity = (TopListEntity) obj;

				if (!entity.success) {
					if (entity.resultCode.equals("1310")) {

					} else {

					}
				} else {
					mEntity = entity;
					mAdapter.setData(mEntity.list);
				}

			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

}
