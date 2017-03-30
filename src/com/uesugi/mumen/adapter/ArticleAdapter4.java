package com.uesugi.mumen.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uesugi.mumen.GuangGaoActivity;
import com.uesugi.mumen.R;
import com.uesugi.mumen.onTouchDownloadListener;
import com.uesugi.mumen.entity.ArticleEntity;
import com.uesugi.mumen.utils.StringUtils;

public class ArticleAdapter4 extends BaseAdapter {
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
	private onTouchDownloadListener mListener=null;

	class Holder1 {
		public ImageView icon = null;
		public TextView title = null;
		public LinearLayout eye = null;
		public LinearLayout download = null;
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

	public ArticleAdapter4(Context context, FinalBitmap fb) {

		mContext = context;
		mListEntity = new ArrayList<ArticleEntity>();
		mInflater = LayoutInflater.from(context);
		mFinalBitmap = fb;
		Resources res = mContext.getResources();
		mDefaultBitmap = BitmapFactory.decodeResource(res,
				R.drawable.bg_default_list);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListEntity.size();
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
		final int p=position;
		int type = getItemViewType(position);
		final ArticleEntity entity = (ArticleEntity) getItem(position);
		Holder1 holder1 = null;
		Holder2 holder2 = null;
		Holder3 holder3 = null;
		System.out.println("getView " + position + " " + convertView
				+ " type = " + type);
		if (convertView == null) {
			// 选择某一个样式。。
			switch (type) {
			case 0:
				// Log.e("show0", entity.show);
				convertView = mInflater.inflate(R.layout.row_article_list4,
						null);

				holder1 = new Holder1();

				holder1.icon = (ImageView) convertView
						.findViewById(R.id.row_article_imgv_icon);
				holder1.title = (TextView) convertView
						.findViewById(R.id.row_article_txt_title);
				holder1.eye = (LinearLayout) convertView
						.findViewById(R.id.row_article_layout_eye);
				holder1.download = (LinearLayout) convertView
						.findViewById(R.id.row_article_layout_download);
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
			// Log.e("convertView.getId()", convertView.getId() + "/" +
			// position);
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
		Log.e("shibushi", convertView.getId() + "/" + position);

		if (!StringUtils.isBlank(entity.icon)) {

			mFinalBitmap.display(holder1.icon, entity.icon);

		} else {
			holder1.icon.setImageBitmap(mDefaultBitmap);
		}

		holder1.title.setText(entity.title);
		holder1.eye.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, GuangGaoActivity.class);
				intent.putExtra("title", entity.title);
				intent.putExtra("url", entity.url);
				mContext.startActivity(intent);
			}
		});
		holder1.download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mListener!=null) {
					mListener.onTouch(p);
				}
			}
		});
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

	public void delItem(int index) {
		mListEntity.remove(index);
		notifyDataSetChanged();
	}

	public void setOnTouchDownloadListener(onTouchDownloadListener listener) {
		mListener = listener;
	}
}
