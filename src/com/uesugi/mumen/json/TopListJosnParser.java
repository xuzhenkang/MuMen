package com.uesugi.mumen.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.uesugi.mumen.entity.TopEntity;
import com.uesugi.mumen.entity.TopListEntity;

public class TopListJosnParser {

	private String TAG = "BBSListJosnParser";
	public String json;

	public void setJson(String string) {
		this.json = string;
	}

	public TopListEntity parser() {
		TopListEntity entity = new TopListEntity();
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
						TopEntity itemEntity = new TopEntity();

						itemEntity.id = itemOjbect.getString("id");
						itemEntity.name = itemOjbect.getString("name");
						itemEntity.icon = itemOjbect.getString("icon");
						itemEntity.province_id = itemOjbect
								.getString("province_id");
						itemEntity.city_id = itemOjbect.getString("city_id");
						itemEntity.area_id = itemOjbect.getString("area_id");
						itemEntity.address = itemOjbect.getString("address");
						itemEntity.jdkh = itemOjbect.getString("jdkh");
						itemEntity.yxkh = itemOjbect.getString("yxkh");
						itemEntity.cjkh = itemOjbect.getString("cjkh");
						itemEntity.fhb = itemOjbect.getString("fhb");
						itemEntity.proceeds = itemOjbect.getString("proceeds");
						itemEntity.date = itemOjbect.getString("date");
						itemEntity.posttime = itemOjbect.getString("posttime");

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
