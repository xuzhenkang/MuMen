package com.uesugi.mumen.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uesugi.mumen.R;
import com.uesugi.mumen.entity.TopEntity;
import com.uesugi.mumen.user.ShopSearchActivity;
import com.uesugi.mumen.utils.StringUtils;

public class MyShopSearchAdapter extends BaseAdapter {
	// /**
	// * 标题的item
	// */
	// public static final int ITEM_TITLE = 0;
	// /**
	// * 二级菜单的item
	// */
	// public static final int ITEM_INTRODUCE = 1;
	// private List<ViewItem> mList;
	// private Context context;
	//
	// private LayoutInflater inflater;

	// 两个样式 两个holder。100就写100holder。。当然你何以把他抽离出来这里先只为了说明问题
	class Holder1 {
		public ImageView pict1 = null;
		public TextView name1 = null;

		public ImageView pict2 = null;
		public TextView name2 = null;

		public LinearLayout layout1 = null;
		public LinearLayout layout2 = null;

	}

	class Holder2 {

	}

	class Holder3 {

	}

	private LayoutInflater mInflater = null;
	private Context mContext;
	public List<TopEntity> mListEntity = null;
	private Bitmap mDefaultBitmap = null;
	private FinalBitmap mFinalBitmap = null;
	private DecimalFormat mDecimalFormat = null;
	private ShopSearchActivity mActivity;

	public MyShopSearchAdapter(Context context, FinalBitmap fb, ShopSearchActivity activity) {
		mActivity = activity;
		mContext = context;
		mDecimalFormat = new DecimalFormat(".00");
		mListEntity = new ArrayList<TopEntity>();
		mInflater = LayoutInflater.from(context);
		mFinalBitmap = fb;
		Resources res = mContext.getResources();
		mDefaultBitmap = BitmapFactory.decodeResource(res, R.drawable.bg_head);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListEntity.size() / 2 + mListEntity.size() % 2;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		Object obj = null;

		if (mListEntity != null && arg0 < mListEntity.size()) {
			obj = mListEntity.get(arg0);
		}

		return obj;
	}

	// 返回 代表某一个样式 的 数值
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		// if (mListEntity.get(position).show.equals("0")) {
		return 0;
		// } else if (mListEntity.get(position).show.equals("1")) {
		// return 1;
		// } else
		// return 2;

	}

	// 两个样式 返回2
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int pp = position;
		int type = getItemViewType(position);

		Holder1 holder1 = null;
		Holder2 holder2 = null;
		Holder3 holder3 = null;

		if (convertView == null) {
			// 选择某一个样式。。
			switch (type) {
			case 0:
				// Log.e("show0", entity.show);
				convertView = mInflater.inflate(R.layout.row_pk_list, null);

				holder1 = new Holder1();

				holder1.pict1 = (ImageView) convertView
						.findViewById(R.id.row_pk_imgv_icon1);
				holder1.name1 = (TextView) convertView
						.findViewById(R.id.row_pk_txt_name1);
				holder1.layout1 = (LinearLayout) convertView
						.findViewById(R.id.row_pk_layout_1);
				holder1.layout2 = (LinearLayout) convertView
						.findViewById(R.id.row_pk_layout_2);
				holder1.pict2 = (ImageView) convertView
						.findViewById(R.id.row_pk_imgv_icon2);
				holder1.name2 = (TextView) convertView
						.findViewById(R.id.row_pk_txt_name2);

				convertView.setId(position);
				convertView.setTag(holder1);
				break;
			case 1:

				break;
			case 2:

				break;
			default:
				break;
			}
		} else {

			switch (type) {
			case 0:
				holder1 = (Holder1) convertView.getTag();
				break;
			case 1:
				holder2 = (Holder2) convertView.getTag();
				break;
			case 2:
				holder3 = (Holder3) convertView.getTag();
				break;
			default:
				holder1 = (Holder1) convertView.getTag();
				break;
			}

		}

		if (pp * 2 < mListEntity.size()) {
			holder1.layout1.setVisibility(View.VISIBLE);
			final TopEntity entity1 = (TopEntity) getItem(pp * 2);
			holder1.pict1.setImageBitmap(mDefaultBitmap);
			holder1.name1.setText(entity1.name);
			if (!StringUtils.isBlank(entity1.icon)) {

				mFinalBitmap.display(holder1.pict1, entity1.icon,
						mDefaultBitmap, mDefaultBitmap);

			} else {
				holder1.pict1.setImageBitmap(mDefaultBitmap);
			}
			holder1.pict1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mActivity.showInfoBg(entity1);
				}
			});
		} else {
			holder1.pict1.setImageBitmap(null);
			holder1.layout1.setVisibility(View.INVISIBLE);
		}
		if ((pp * 2) + 1 < mListEntity.size()) {
			holder1.layout2.setVisibility(View.VISIBLE);
			final TopEntity entity2 = (TopEntity) getItem((pp * 2) + 1);
			holder1.pict2.setImageBitmap(mDefaultBitmap);
			holder1.name2.setText(entity2.name);

			if (!StringUtils.isBlank(entity2.icon)) {

				mFinalBitmap.display(holder1.pict2, entity2.icon,
						mDefaultBitmap, mDefaultBitmap);

			} else {
				holder1.pict2.setImageBitmap(mDefaultBitmap);
			}
			holder1.layout2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mActivity.showInfoBg(entity2);
				}
			});
		} else {
			holder1.pict2.setImageBitmap(null);
			holder1.layout2.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	public void setData(List<TopEntity> list) {

		mListEntity.addAll(list);
		notifyDataSetChanged();
	}

	public void setData2(List<TopEntity> list) {
		for (int i = 0; i < list.size(); i++) {
			TopEntity entity = new TopEntity();
			entity = list.get(i);
			mListEntity.add(0, entity);
		}
		notifyDataSetChanged();
	}

	public void clearAll() {
		mListEntity.clear();
		notifyDataSetChanged();
	}

}
