package com.uesugi.mumen.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

public class StringUtils {

	private static long SECOND = 1000;

	// 解析文件名。
	public static String parseFile(String url) {
		String file = "";

		if (StringUtils.isBlank(url)) {
			return file;
		}

		int index = url.lastIndexOf("/");
		if (index >= 0) {
			file = url.substring(index + 1);
		}

		return file;
	}

	// 判断是否为null或""
	public static boolean isBlank(String strSource) {
		if (null == strSource || "".equals(strSource)) {
			return true;
		}

		return false;
	}

	// 判断是否不为为null或""
	public static boolean isNotBlank(String strSource) {
		if (null == strSource || "".equals(strSource)) {
			return false;
		}

		return true;
	}
	
	public static String getTimeByFormateMMDD (String string) {
		String str = null;
		
		long timeLong = Long.parseLong(string) * SECOND;
		Date jsondate = new Date(timeLong);
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
		str = sdf2.format(jsondate);
		
		
		return str;
	}

	public static String checkTime(String time) {
		String str = null;

		long timeLong = Long.parseLong(time) * SECOND;

		Date jsondate = new Date(timeLong);
		Date nowdate = new Date(System.currentTimeMillis());

		long mTime = nowdate.getTime() - jsondate.getTime();

		if (mTime < 60 * SECOND) {

			str = "刚刚";

		} else if (mTime > 60 * SECOND && mTime < 60 * 60 * SECOND) {

			str = Math.abs(mTime / (60 * SECOND)) + "分钟前";

		} else if (mTime > 60 * 60 * SECOND && mTime < 24 * 60 * 60 * SECOND) {

			int jsonday = jsondate.getDay();
			int currentday = nowdate.getDay();
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

			if (jsonday == currentday) {

				str = "今天: " + sdf2.format(jsondate);

			} else {

				str = "昨天: " + sdf2.format(jsondate);

			}

		} else {

			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
			str = sdf3.format(jsondate);

		}

		return str;
	}

	public static String getTimeByFormate(String string) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long timeLong = Long.parseLong(string) * SECOND;
		result = sdf.format(new Date(timeLong));
		return result;
	}

	/**
	 * bit to base64
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {
		String result = null;
		ByteArrayOutputStream baos = null;

		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}

		} catch (IOException e) {

		} finally {

			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {

			}
		}

		return result;
	}
	
	/**
	 * MD5加密  
	 * @param str
	 * @return
	 */
    public static String MD5(String str)  
    {  
        MessageDigest md5 = null;  
        try  
        {  
            md5 = MessageDigest.getInstance("MD5"); 
        }catch(Exception e)  
        {  
        	Log.e("StringUtils", "md5 error:"+e.toString());
            //e.printStackTrace();  
            return null;  
        }  
          
        char[] charArray = str.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
          
        for(int i = 0; i < charArray.length; i++)  
        {  
            byteArray[i] = (byte)charArray[i];  
        }  
        byte[] md5Bytes = md5.digest(byteArray);  
          
        StringBuffer hexValue = new StringBuffer();  
        for( int i = 0; i < md5Bytes.length; i++)  
        {  
            int val = ((int)md5Bytes[i])&0xff;  
            if(val < 16)  
            {  
                hexValue.append("0");  
            }  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }
    
    //检查是否为手机
    public static boolean isPhone (String phone) {
    	
    	boolean ret = false;
    	
    	Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
    	Matcher m = p.matcher(phone);
    	ret = m.matches();
    	
    	return ret;
    }
    
    //检查车牌号是否正确
    public static boolean isCarNum (String number) {
    	
    	boolean ret = false;
    	
    	String match = "^辽[A-Za-z]{1}[A-Za-z_0-9]{5}$";
    	
    	Pattern p = Pattern.compile(match);
    	Matcher m = p.matcher(number);
    	
    	ret = m.matches();
    	
    	return ret;
    }
    
    //获取数字

    public static String number (String number) {
    	
    	String ret = "";
    	
    	String match = "[^0-9]";
    	
    	Pattern p = Pattern.compile(match);
    	Matcher m = p.matcher(number);
    	
    	ret = m.replaceAll("");
    	
    	return ret;
    }
    

    

}
