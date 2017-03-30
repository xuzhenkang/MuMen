package com.uesugi.mumen.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.uesugi.mumen.entity.LoginEntity;
import com.uesugi.mumen.entity.UserEntity;

public class LoginJosnParser {

	private String TAG = "LoginJosnParser";
	public String json;

	public void setJson(String string) {
		this.json = string;
	}

	public LoginEntity parser() {
		LoginEntity entity = new LoginEntity();
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

					JSONObject itemOjbect = listArray.getJSONObject(0);
					
					entity.l_user = new UserEntity();
					
					entity.l_user.id = itemOjbect.getString("id");
					entity.l_user.phone = itemOjbect.getString("phone");
					entity.l_user.name = itemOjbect.getString("name");
					entity.l_user.sex = itemOjbect.getString("sex");
					entity.l_user.icon = itemOjbect.getString("icon");
					
					entity.l_user.token = itemOjbect.getString("token");
					
					entity.l_user.role = itemOjbect.getString("role");
					entity.l_user.store_id = itemOjbect.getString("store_id");
					entity.l_user.store_name = itemOjbect.getString("store_name");
					entity.l_user.store_icon = itemOjbect.getString("store_icon");
					entity.l_user.factory_email = itemOjbect.getString("factory_email");
					entity.l_user.factory_id = itemOjbect.getString("factory_id");
					entity.l_user.factory_name = itemOjbect.getString("factory_name");
					entity.l_user.factory_icon = itemOjbect.getString("factory_icon");
					entity.l_user.report_content = itemOjbect.getString("report_content");
					entity.l_user.share_content = itemOjbect.getString("share_content");
					entity.l_user.assep_content = itemOjbect.getString("assep_content");
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
