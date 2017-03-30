package com.uesugi.mumen.json;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.uesugi.mumen.entity.DianEntity;

public class DianJosnParser {

	private String TAG = "DianJosnParser";
	public String json;

	public void setJson(String string) {
		this.json = string;
	}

	public DianEntity parser() {
		DianEntity entity = new DianEntity();
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
					JSONObject jsonObject3 = jsonObject2.getJSONObject("list");

					entity.column1 = jsonObject3.getString("column1");
					entity.column2 = jsonObject3.getString("column2");
					entity.column3 = jsonObject3.getString("column3");

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
