package com.uesugi.mumen.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uesugi.mumen.CircleImageView;
import com.uesugi.mumen.GuangGaoActivity;
import com.uesugi.mumen.R;
import com.uesugi.mumen.entity.ArticleEntity;
import com.uesugi.mumen.utils.StringUtils;

public class ExpandAdapter extends BaseAdapter {
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
		public CircleImageView pict1 = null;
		public TextView name1 = null;

		public CircleImageView pict2 = null;
		public TextView name2 = null;

		public CircleImageView pict3 = null;
		public TextView name3 = null;

		public LinearLayout layout1 = null;
		public LinearLayout layout2 = null;
		public LinearLayout layout3 = null;

	}

	class Holder2 {

	}

	class Holder3 {

	}

	private LayoutInflater mInflater = null;
	private Context mContext;
	public List<ArticleEntity> mListEntity = null;
	private Bitmap mDefaultBitmap = null;
	private FinalBitmap mFinalBitmap = null;

	public ExpandAdapter(Context context, FinalBitmap fb) {
		mContext = context;
		mListEntity = new ArrayList<ArticleEntity>();
		mInflater = LayoutInflater.from(context);
		mFinalBitmap = fb;
		Resources res = mContext.getResources();
		mDefaultBitmap = BitmapFactory.decodeResource(res,
				R.drawable.bg_default_banner_dz);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int x = mListEntity.size() / 3;
		int y = mListEntity.size() % 3;
		if (y > 0) {
			x += 1;
		}

		return x;
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
				convertView = mInflater.inflate(R.layout.row_expand_list, null);

				holder1 = new Holder1();

				holder1.pict1 = (CircleImageView) convertView
						.findViewById(R.id.row_expand_imgv_1);
				holder1.name1 = (TextView) convertView
						.findViewById(R.id.row_expand_txt_name_1);

				holder1.layout1 = (LinearLayout) convertView
						.findViewById(R.id.row_expand_layout_1);
				holder1.layout2 = (LinearLayout) convertView
						.findViewById(R.id.row_expand_layout_2);
				holder1.pict2 = (CircleImageView) convertView
						.findViewById(R.id.row_expand_imgv_2);
				holder1.name2 = (TextView) convertView
						.findViewById(R.id.row_expand_txt_name_2);

				holder1.layout3 = (LinearLayout) convertView
						.findViewById(R.id.row_expand_layout_3);
				holder1.pict3 = (CircleImageView) convertView
						.findViewById(R.id.row_expand_imgv_3);
				holder1.name3 = (TextView) convertView
						.findViewById(R.id.row_expand_txt_name_3);

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

		if (pp * 3 < mListEntity.size()) {
			holder1.layout1.setVisibility(View.VISIBLE);
			final ArticleEntity entity1 = (ArticleEntity) getItem(pp * 3);
			holder1.pict1.setImageBitmap(mDefaultBitmap);
			holder1.name1.setText(entity1.title);

			if (!StringUtils.isBlank(entity1.icon)) {

				mFinalBitmap.display(holder1.pict1, entity1.icon,
						mDefaultBitmap, mDefaultBitmap);

			} else {
				holder1.pict1.setImageBitmap(mDefaultBitmap);
			}
			holder1.layout1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(mContext, GuangGaoActivity.class);
					intent.putExtra("title", entity1.title);
					intent.putExtra("url", entity1.url);
					mContext.startActivity(intent);
				}
			});
		} else {
			holder1.pict1.setImageBitmap(null);
			holder1.layout1.setVisibility(View.INVISIBLE);
		}
		if ((pp * 3) + 1 < mListEntity.size()) {
			holder1.layout2.setVisibility(View.VISIBLE);
			final ArticleEntity entity2 = (ArticleEntity) getItem((pp * 3) + 1);
			holder1.pict2.setImageBitmap(mDefaultBitmap);
			holder1.name2.setText(entity2.title);

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
					Intent intent = new Intent();
					intent.setClass(mContext, GuangGaoActivity.class);
					intent.putExtra("title", entity2.title);
					intent.putExtra("url", entity2.url);
					mContext.startActivity(intent);
				}
			});
		} else {
			holder1.pict2.setImageBitmap(null);
			holder1.layout2.setVisibility(View.INVISIBLE);
		}
		if ((pp * 3) + 2 < mListEntity.size()) {
			holder1.layout3.setVisibility(View.VISIBLE);
			final ArticleEntity entity3 = (ArticleEntity) getItem((pp * 3) + 2);
			holder1.pict3.setImageBitmap(mDefaultBitmap);
			holder1.name3.setText(entity3.title);

			if (!StringUtils.isBlank(entity3.icon)) {

				mFinalBitmap.display(holder1.pict3, entity3.icon,
						mDefaultBitmap, mDefaultBitmap);

			} else {
				holder1.pict3.setImageBitmap(mDefaultBitmap);
			}
			holder1.layout3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(mContext, GuangGaoActivity.class);
					intent.putExtra("title", entity3.title);
					intent.putExtra("url", entity3.url);
					mContext.startActivity(intent);
				}
			});
		} else {
			holder1.pict3.setImageBitmap(null);
			holder1.layout3.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	public void setData(List<ArticleEntity> list) {

		mListEntity.addAll(list);
		notifyDataSetChanged();
	}

	public void setData2(List<ArticleEntity> list) {
		for (int i = 0; i < list.size(); i++) {
			ArticleEntity entity = new ArticleEntity();
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
