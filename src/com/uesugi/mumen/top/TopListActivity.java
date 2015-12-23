package com.uesugi.mumen.top;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.graphics.Color;
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

	private String city_type = "1";

	@ViewInject(id = R.id.top_list_txt_city1, click = "btnCity1")
	private TextView mTextCity1;
	@ViewInject(id = R.id.top_list_txt_city2, click = "btnCity2")
	private TextView mTextCity2;
	@ViewInject(id = R.id.top_list_txt_city3, click = "btnCity3")
	private TextView mTextCity3;
	@ViewInject(id = R.id.top_list_txt_city4, click = "btnCity4")
	private TextView mTextCity4;

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

		mAdapter.clearAll();

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
		RemoteUtils.getTopW(city_type, new WHTTHttpRequestCallBack() {

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
		RemoteUtils.getTopM(city_type, new WHTTHttpRequestCallBack() {

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
		RemoteUtils.getTopQ(city_type, new WHTTHttpRequestCallBack() {

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
		RemoteUtils.getTopY(city_type, new WHTTHttpRequestCallBack() {

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

	public void btnCity1(View v) {
		if (city_type.equals("1")) {
			return;
		}

		mTextCity1.setTextColor(Color.parseColor("#f5a671"));
		mTextCity2.setTextColor(Color.parseColor("#757575"));
		mTextCity3.setTextColor(Color.parseColor("#757575"));
		mTextCity4.setTextColor(Color.parseColor("#757575"));
		city_type = "1";

		getList();

	}

	public void btnCity2(View v) {
		if (city_type.equals("2")) {
			return;
		}

		mTextCity2.setTextColor(Color.parseColor("#f5a671"));
		mTextCity1.setTextColor(Color.parseColor("#757575"));
		mTextCity3.setTextColor(Color.parseColor("#757575"));
		mTextCity4.setTextColor(Color.parseColor("#757575"));
		city_type = "2";

		getList();

	}

	public void btnCity3(View v) {
		if (city_type.equals("3")) {
			return;
		}

		mTextCity3.setTextColor(Color.parseColor("#f5a671"));
		mTextCity2.setTextColor(Color.parseColor("#757575"));
		mTextCity1.setTextColor(Color.parseColor("#757575"));
		mTextCity4.setTextColor(Color.parseColor("#757575"));
		city_type = "3";

		getList();
	}

	public void btnCity4(View v) {
		if (city_type.equals("4")) {
			return;
		}

		mTextCity4.setTextColor(Color.parseColor("#f5a671"));
		mTextCity2.setTextColor(Color.parseColor("#757575"));
		mTextCity3.setTextColor(Color.parseColor("#757575"));
		mTextCity1.setTextColor(Color.parseColor("#757575"));
		city_type = "4";

		getList();

	}

}
