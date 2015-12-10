package com.uesugi.mumen.json;

import org.json.JSONException;
import org.json.JSONObject;

import com.uesugi.mumen.entity.BaseEntity;

public class BaseJosnParser {

	private String TAG = "BaseJosnParser";
	public String json;

	public void setJson(String string) {
		this.json = string;
	}

	public BaseEntity parser() {
		BaseEntity entity = new BaseEntity();
		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObject(json);
			String status = jsonObject.getString("status");
			String code = jsonObject.getString("code");

			entity.setState(status);
			// entity.setCode(code);
			entity.resultCode = jsonObject.getString("code");
			entity.msg = jsonObject.getString("msg");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity.error();
		}

		return entity;

	}
}
