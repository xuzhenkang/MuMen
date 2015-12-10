package com.uesugi.mumen.entity;

import java.io.Serializable;

import android.util.Log;

public class HttpRequestEntity extends Object implements Serializable {

	public static String STATUS_SUCCESS = "1";

	public static String CODE_NODATA = "1001"; // 无数�? public static String
												// CODE_SUCCESS = "1000"; // 成功
	public static String CODE_LOGIN_ERROR = "1210"; // 重新登陆
	public static String CODE_ACTION_ERROR = "1400"; // 操作失败
	public static String CODE_PARA_ERROR = "1401"; // 参数验证失败
	public static String CODE_HTTP_REQUEST = "0000";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Boolean success = false;
	public String msg = "";
	public String resultCode = "";
	public int pageTotal = 0;

	public String icon_path = null;

	public void setState(String state) {
		if (state.equals(STATUS_SUCCESS)) {
			success = true;
		} else {
			success = false;
		}
	}

	public void setCode(String code) {
		resultCode = code;

		// if (code.equals(CODE_NODATA))
		// {
		// msg = "无数�?";
		// }else if (code.equals(CODE_SUCCESS))
		// {
		// msg = "请求成功!";
		// }else if (code.equals(CODE_LOGIN_ERROR))
		// {
		// msg = "重新登陆!";
		// }else if (code.equals(CODE_ACTION_ERROR))
		// {
		// msg = "操作失败!";
		// }else if (code.equals(CODE_PARA_ERROR))
		// {
		// msg = "参数验证失效!";
		// }else if (code.equals(CODE_HTTP_REQUEST))
		// {
		// msg = "请求失败，请稍后重试!";
		// }
	}

	public void error() {
		Log.e("tttttttttttttttttt", "entity error");
		msg = "请求失败，请稍后重试!";
		success = false;
	}

}
