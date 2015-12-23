package com.uesugi.mumen.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.uesugi.mumen.entity.PicEntity;
import com.uesugi.mumen.entity.PicListEntity;

public class PicListJosnParser {

	private String TAG = "BBSListJosnParser";
	public String json;

	public void setJson(String string) {
		this.json = string;
	}

	public PicListEntity parser() {
		PicListEntity entity = new PicListEntity();
		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObject(json);
			String status = jsonObject.getString("status");
			String code = jsonObject.getString("code");
			Log.e("status", status);
			entity.setState(status);
			// entity.setCode(code);
			entity.resultCode = jsonObject.getString("code");
			entity.msg = jsonObject.getString("msg");
			if (entity.success) {
				Log.e("xxxx", "11111");
				try {
					JSONObject jsonObject2 = jsonObject.getJSONObject("data");
					// 获取用户信息
					JSONArray listArray = jsonObject2.getJSONArray("list");

					for (int i = 0; i < listArray.length(); i++) {
						JSONObject itemOjbect = listArray.getJSONObject(i);
						PicEntity itemEntity = new PicEntity();

						itemEntity.id = itemOjbect.getString("id");
						itemEntity.title = itemOjbect.getString("title");
						itemEntity.pic = itemOjbect.getString("pic");
						try {
						itemEntity.url = itemOjbect.getString("url");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							// Log.e(TAG, "parser:" + e.toString());
						}

						
						entity.list.add(itemEntity);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					Log.e(TAG, "parser:" + e.toString());
				}

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity.error();
		}

		return entity;

	}
}
