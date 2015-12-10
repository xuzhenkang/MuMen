package com.uesugi.mumen.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnect {

	

	public static Boolean checkConnectivity(Context mContext) {

		Boolean ret = true;

		ConnectivityManager connMan = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileInfo = connMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiInfo = connMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		if (!mobileInfo.isConnectedOrConnecting()
				&& !wifiInfo.isConnectedOrConnecting()) 
		{
			ret = false;
		}

		return ret;
	}
	

}
