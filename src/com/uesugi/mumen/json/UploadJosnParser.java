package com.uesugi.mumen.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.uesugi.mumen.entity.UploadEntity;

public class UploadJosnParser {

	private String TAG = "UploadJosnParser";
	public String json;

	public void setJson(String string) {
		this.json = string;
	}

	public UploadEntity parser() {
		UploadEntity entity = new UploadEntity();
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
						entity.imgs.add(listArray.getString(i));
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
