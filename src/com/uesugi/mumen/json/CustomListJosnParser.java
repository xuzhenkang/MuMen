package com.uesugi.mumen.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.uesugi.mumen.entity.CustomEntity;
import com.uesugi.mumen.entity.CustomListEntity;

public class CustomListJosnParser {

	private String TAG = "BBSListJosnParser";
	public String json;

	public void setJson(String string) {
		this.json = string;
	}

	public CustomListEntity parser() {
		CustomListEntity entity = new CustomListEntity();
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
					String name = jsonObject2.getString("name");
					entity.name = name;
					for (int i = 0; i < listArray.length(); i++) {
						JSONObject itemOjbect = listArray.getJSONObject(i);
						CustomEntity itemEntity = new CustomEntity();

						itemEntity.title = itemOjbect.getString("title");
						itemEntity.content = itemOjbect.getString("content");
						itemEntity.icon = itemOjbect.getString("icon");
						itemEntity.short_ = itemOjbect.getString("short");
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
