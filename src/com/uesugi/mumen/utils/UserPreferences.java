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
	private static final String PREFS_NAME = "com.uesugi.lovecitywide.userPreference";

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
	//
	// // // // 保存城市数据
	// // public static void saveCityPref(Context context, UserCityEntity
	// entity) {
	// // SharedPreferences.Editor prefs = context.getSharedPreferences(
	// // PREFS_NAME, 0).edit();
	// //
	// // prefs.putString(PREF_ID_KEY + "city", entity.city);
	// // prefs.putString(PREF_ID_KEY + "cityid", entity.id);
	// // prefs.putString(PREF_ID_KEY + "area", entity.area);
	// // prefs.putString(PREF_ID_KEY + "areaid", entity.areaid);
	// // prefs.commit();
	// // }
	// //
	// // // 装入用户数据
	// // public static UserCityEntity loadCityPref(Context context) {
	// // SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
	// //
	// // String city = prefs.getString(PREF_ID_KEY + "city", null);
	// // String cityid = prefs.getString(PREF_ID_KEY + "cityid", null);
	// // String area = prefs.getString(PREF_ID_KEY + "area", null);
	// // String areaid = prefs.getString(PREF_ID_KEY + "areaid", null);
	// // UserCityEntity entity = new UserCityEntity();
	// // if (StringUtils.isBlank(city) || StringUtils.isBlank(cityid)) {
	// // entity.city = "沈阳市";
	// // entity.id = "1";
	// // entity.areaid = "";
	// // entity.area = "";
	// // } else {
	// // entity.city = city;
	// // entity.id = cityid;
	// // entity.areaid = areaid;
	// // entity.area = area;
	// // }
	// //
	// // return entity;
	// // }
	// //
	// // // 删除用户数据
	// // public static void delCityPref(Context context) {
	// // SharedPreferences.Editor prefs = context.getSharedPreferences(
	// // PREFS_NAME, 0).edit();
	// //
	// // prefs.remove(PREF_ID_KEY + "city");
	// // prefs.remove(PREF_ID_KEY + "cityid");
	// // prefs.remove(PREF_ID_KEY + "area");
	// // prefs.remove(PREF_ID_KEY + "areaid");
	// // prefs.commit();
	// // }
	//
	// // // 保存购物车
	// public static void saveGoodsPref(Context context, List<ShoppingEntity>
	// list) {
	// SharedPreferences.Editor prefs = context.getSharedPreferences(
	// PREFS_NAME, 0).edit();
	// String jsonresult = "";
	// JSONObject object = new JSONObject();
	//
	// // JSONObject objectData = new JSONObject();
	// try {
	// object.put("status", "success");
	// object.put("code", "1000");
	// object.put("error_message", "成功!");
	//
	// JSONArray jsonarrayList = new JSONArray();// json数组，里面包含的内容为pet的所有对象
	// for (int i = 0; i < list.size(); i++) {
	// JSONObject jsonObj = new JSONObject();// list对象，json形式
	// jsonObj.put("productID", list.get(i).productID);
	// jsonObj.put("title", list.get(i).title);
	// jsonObj.put("shopID", list.get(i).shopID);
	// jsonObj.put("shopname", list.get(i).shopname);
	// jsonObj.put("price", list.get(i).price);
	// jsonObj.put("discount", list.get(i).discount);
	// jsonObj.put("minbuy", list.get(i).minbuy);
	// jsonObj.put("litpic", list.get(i).litpic);
	// jsonObj.put("counts", list.get(i).counts);
	// jsonObj.put("volume", list.get(i).volume);
	// jsonObj.put("Net_content", list.get(i).Net_content);
	// jsonObj.put("des", list.get(i).des);
	// jsonObj.put("star", list.get(i).star);
	// jsonObj.put("pubdate", list.get(i).pubdate);
	// jsonObj.put("packets", list.get(i).packets);
	// jsonObj.put("box", list.get(i).box);
	// jsonObj.put("true_price", list.get(i).true_price);
	// jsonObj.put("one_price", list.get(i).one_price);
	// jsonObj.put("num", list.get(i).num);
	// jsonObj.put("is_select", list.get(i).is_select);
	// // 把每个数据当作一对象添加到数组里
	// jsonarrayList.put(jsonObj);// 向json数组里面添加pet对象
	// }
	//
	// object.put("data", jsonarrayList);
	//
	// jsonresult = object.toString();
	// // Log.e("jsonresult", jsonresult);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	//
	// System.out.println("jsonexception error : " + e.toString());
	//
	// }
	//
	// System.out.println("jsonresult : " + jsonresult);
	//
	// prefs.putString(PREF_ID_KEY + "goodsJson", jsonresult);
	//
	// prefs.commit();
	// }
	//
	// // 装入用户数据
	// public static ShoppingListEntity loadGoodsPref(Context context) {
	// SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
	//
	// String userJson = prefs.getString(PREF_ID_KEY + "goodsJson", null);
	//
	// if (StringUtils.isBlank(userJson)) {
	// return null;
	// }
	// // Log.e("jsonresult", userJson);
	// ShoppingListJosnParser json = new ShoppingListJosnParser();
	// json.setJson(userJson);
	// ShoppingListEntity entity = json.parser();
	//
	// return entity;
	// }
	//
	// // 删除用户数据
	// public static void delGoodsPref(Context context) {
	// SharedPreferences.Editor prefs = context.getSharedPreferences(
	// PREFS_NAME, 0).edit();
	//
	// prefs.remove(PREF_ID_KEY + "goodsJson");
	//
	// prefs.commit();
	// }
	//
	// // // 保存购物车
	// public static void saveJiluPref(Context context, List<ShoppingEntity>
	// list) {
	// SharedPreferences.Editor prefs = context.getSharedPreferences(
	// PREFS_NAME, 0).edit();
	// String jsonresult = "";
	// JSONObject object = new JSONObject();
	//
	// // JSONObject objectData = new JSONObject();
	// try {
	// object.put("status", "success");
	// object.put("code", "1000");
	// object.put("error_message", "成功!");
	//
	// JSONArray jsonarrayList = new JSONArray();// json数组，里面包含的内容为pet的所有对象
	// for (int i = 0; i < list.size(); i++) {
	// if (i > 19) {
	// break;
	// }
	//
	// JSONObject jsonObj = new JSONObject();// list对象，json形式
	// jsonObj.put("productID", list.get(i).productID);
	// jsonObj.put("title", list.get(i).title);
	// jsonObj.put("shopID", list.get(i).shopID);
	// jsonObj.put("shopname", list.get(i).shopname);
	// jsonObj.put("price", list.get(i).price);
	// jsonObj.put("discount", list.get(i).discount);
	// jsonObj.put("litpic", list.get(i).litpic);
	// jsonObj.put("counts", list.get(i).counts);
	// jsonObj.put("volume", list.get(i).volume);
	// jsonObj.put("minbuy", list.get(i).minbuy);
	// jsonObj.put("Net_content", list.get(i).Net_content);
	// jsonObj.put("des", list.get(i).des);
	// jsonObj.put("star", list.get(i).star);
	// jsonObj.put("pubdate", list.get(i).pubdate);
	// jsonObj.put("packets", list.get(i).packets);
	// jsonObj.put("box", list.get(i).box);
	// jsonObj.put("true_price", list.get(i).true_price);
	// jsonObj.put("num", list.get(i).num);
	// jsonObj.put("is_select", list.get(i).is_select);
	// // 把每个数据当作一对象添加到数组里
	// jsonarrayList.put(jsonObj);// 向json数组里面添加pet对象
	// }
	//
	// object.put("data", jsonarrayList);
	//
	// jsonresult = object.toString();
	// // Log.e("jsonresult", jsonresult);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// prefs.putString(PREF_ID_KEY + "JiluJson", jsonresult);
	//
	// prefs.commit();
	// }
	//
	// // 装入用户数据
	// public static ShoppingListEntity loadJiluPref(Context context) {
	// SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
	//
	// String userJson = prefs.getString(PREF_ID_KEY + "JiluJson", null);
	//
	// if (StringUtils.isBlank(userJson)) {
	// return null;
	// }
	// // Log.e("jsonresult", userJson);
	// ShoppingListJosnParser json = new ShoppingListJosnParser();
	// json.setJson(userJson);
	// ShoppingListEntity entity = json.parser();
	//
	// return entity;
	// }
	//
	// // 删除用户数据
	// public static void delJiluPref(Context context) {
	// SharedPreferences.Editor prefs = context.getSharedPreferences(
	// PREFS_NAME, 0).edit();
	//
	// prefs.remove(PREF_ID_KEY + "JiluJson");
	//
	// prefs.commit();
	// }
}
