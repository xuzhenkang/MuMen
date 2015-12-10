package com.uesugi.mumen.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uesugi.mumen.R;
import com.uesugi.mumen.entity.TopEntity;

public class TopListAdapter extends BaseAdapter {

	private LayoutInflater mInflater = null;
	private Context mContext;

	private List<TopEntity> mListEntity = null;

	private Bitmap mDefaultBitmap = null;
	private FinalBitmap mFinalBitmap = null;

	public TopListAdapter(Context context) {

		mContext = context;
		mListEntity = new ArrayList<TopEntity>();

		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

			}
		});

		Resources res = mContext.getResources();
		mDefaultBitmap = BitmapFactory.decodeResource(res, R.drawable.bg_head);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count = mListEntity.size();
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub

		Object obj = mListEntity.get(position);

		return obj;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (mListEntity != null && position < mListEntity.size()) {

			TopEntity entity = (TopEntity) getItem(position);

			ViewHolder vh = null;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.layout_toplist_item,
						null);
				vh = new ViewHolder();

				vh.title = (TextView) convertView
						.findViewById(R.id.toplist_text_shopname);
				vh.header = (ImageView) convertView
						.findViewById(R.id.toplist_image_header);
				vh.numberBg = (RelativeLayout) convertView
						.findViewById(R.id.toplist_layout_numberbg);
				vh.number = (TextView) convertView
						.findViewById(R.id.toplist_text_number);

				convertView.setTag(vh);

			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			vh.title.setText(entity.name);
			vh.number.setText("" + (position + 1));

			if (position == 0) {
				vh.numberBg.setBackgroundResource(R.drawable.bg_number_01);
			} else if (position == 1) {
				vh.numberBg.setBackgroundResource(R.drawable.bg_number_02);
			} else if (position == 2) {
				vh.numberBg.setBackgroundResource(R.drawable.bg_number_03);
			} else {
				vh.numberBg.setBackgroundResource(R.drawable.bg_number_04);
			}

			mFinalBitmap.display(vh.header, entity.icon, mDefaultBitmap);

		}

		return convertView;

	}

	public class ViewHolder {

		public int position = -1;

		public TextView title = null;
		public ImageView header = null;
		public RelativeLayout numberBg = null;
		public TextView number = null;

	}

	public void setData(List<TopEntity> list) {
		mListEntity.addAll(list);
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
}
