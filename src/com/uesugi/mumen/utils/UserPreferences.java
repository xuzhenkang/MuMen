package com.uesugi.mumen.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.uesugi.mumen.entity.LoginEntity;
import com.uesugi.mumen.entity.UserEntity;
import com.uesugi.mumen.json.LoginJosnParser;

public class UserPreferences {
	private static final String PREFS_NAME = "com.uesugi.mumen.userPreference";

	private static final String PREF_ID_KEY = "prefix_id_";

	// // 保存用户数据
	public static void saveUserPref(Context context, UserEntity entity) {
		SharedPreferences.Editor prefs = context.getSharedPreferences(
				PREFS_NAME, 0).edit();
		String jsonresult = "";
		JSONObject object = new JSONObject();

		JSONObject objectData = new JSONObject();
		try {
			object.put("status", "1");
			object.put("code", "1000");
			object.put("msg", "登录成功!");
			object.put("time", "0");

			JSONArray jsonarrayList = new JSONArray();// json数组，里面包含的内容为pet的所有对象
			JSONObject jsonObj = new JSONObject();// list对象，json形式
			jsonObj.put("id", entity.id);
			jsonObj.put("phone", entity.phone);
			jsonObj.put("name", entity.name);
			jsonObj.put("sex", entity.sex);
			jsonObj.put("icon", entity.icon);
			jsonObj.put("reg_time", entity.reg_time);
			jsonObj.put("login_time", entity.login_time);
			jsonObj.put("login_ip", entity.login_ip);
			jsonObj.put("push_id", entity.push_id);
			jsonObj.put("token", entity.token);
			jsonObj.put("integral", entity.integral);
			jsonObj.put("qquid", entity.qquid);
			jsonObj.put("wxuid", entity.wxuid);
			jsonObj.put("role", entity.role);
			jsonObj.put("store_id", entity.store_id);
			jsonObj.put("factory_id", entity.factory_id);
			jsonObj.put("factory_name", entity.factory_name);
			jsonObj.put("factory_icon", entity.factory_icon);
			jsonObj.put("store_name", entity.store_name);
			jsonObj.put("store_icon", entity.store_icon);

			// 把每个数据当作一对象添加到数组里
			jsonarrayList.put(jsonObj);// 向json数组里面添加pet对象

			objectData.put("list", jsonarrayList);// 向总对象里面添加包含pet的数组
			object.put("data", objectData);

			jsonresult = object.toString();
			Log.e("jsonresult", jsonresult);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		prefs.putString(PREF_ID_KEY + "userJson", jsonresult);

		prefs.commit();
	}

	// 装入用户数据
	public static UserEntity loadUserPref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

		String userJson = prefs.getString(PREF_ID_KEY + "userJson", null);

		if (StringUtils.isBlank(userJson)) {
			return null;
		}
		Log.e("jsonresult", userJson);
		LoginJosnParser json = new LoginJosnParser();
		json.setJson(userJson);
		LoginEntity entity = json.parser();

		return entity.l_user;
	}

	// 删除用户数据
	public static void delUserPref(Context context) {
		SharedPreferences.Editor prefs = context.getSharedPreferences(
				PREFS_NAME, 0).edit();

		prefs.remove(PREF_ID_KEY + "userJson");

		prefs.commit();
	}

	// // 保存用户数据
	public static void saveTimePref(Context context, String time,
			String username) {
		SharedPreferences.Editor prefs = context.getSharedPreferences(
				PREFS_NAME, 0).edit();

		prefs.putString(PREF_ID_KEY + "time_" + username, time);

		prefs.commit();
	}

	// 装入用户数据
	public static String loadTimePref(Context context, String username) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

		String time = prefs.getString(PREF_ID_KEY + "time_" + username, null);

		if (StringUtils.isBlank(time)) {
			return null;
		}

		return time;
	}

	// // 保存用户数据
	public static void saveMsgTimePref(Context context, String time,
			String username) {
		SharedPreferences.Editor prefs = context.getSharedPreferences(
				PREFS_NAME, 0).edit();

		prefs.putString(PREF_ID_KEY + "msg_" + username, time);

		prefs.commit();
	}

	// 装入用户数据
	public static String loadMsgTimePref(Context context, String username) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

		String time = prefs.getString(PREF_ID_KEY + "msg_" + username, null);

		if (StringUtils.isBlank(time)) {
			return null;
		}

		return time;
	}

	// // 保存用户数据
	public static void saveUserNamePref(Context context, String username) {
		SharedPreferences.Editor prefs = context.getSharedPreferences(
				PREFS_NAME, 0).edit();

		prefs.putString(PREF_ID_KEY + "name_" + username, username);

		prefs.commit();
	}

	// 装入用户数据
	public static String loadUserNamePref(Context context, String username) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

		String name = prefs.getString(PREF_ID_KEY + "name_" + username, null);

		if (StringUtils.isBlank(name)) {
			return null;
		}

		return name;
	}
	
	
	public static void saveDownloadPref(Context context, String state) {
		SharedPreferences.Editor prefs = context.getSharedPreferences(
				PREFS_NAME, 0).edit();

		prefs.putString(PREF_ID_KEY + "hy_state", state);

		prefs.commit();
	}

	// 装入用户数据
	public static String loadDownloadPref(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

		String state = prefs.getString(PREF_ID_KEY + "hy_state", null);

		if (StringUtils.isBlank(state)) {
			return null;
		}

		return state;
	}
}
