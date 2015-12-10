package com.uesugi.mumen.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.uesugi.mumen.entity.LiveEntity;
import com.uesugi.mumen.entity.LiveListEntity;

public class LiveListJosnParser {

	private String TAG = "BBSListJosnParser";
	public String json;

	public void setJson(String string) {
		this.json = string;
	}

	public LiveListEntity parser() {
		LiveListEntity entity = new LiveListEntity();
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
						LiveEntity itemEntity = new LiveEntity();

						itemEntity.id = itemOjbect.getString("id");
						itemEntity.title = itemOjbect.getString("title");
						itemEntity.icon = itemOjbect.getString("icon");
						itemEntity.content = itemOjbect.getString("content");
						itemEntity.start_time = itemOjbect.getString("start_time");
						itemEntity.end_time = itemOjbect.getString("end_time");
						itemEntity.posttime = itemOjbect.getString("posttime");
						itemEntity.cloud_id = itemOjbect.getString("cloud_id");
						itemEntity.url = itemOjbect.getString("url");

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
