package com.uesugi.mumen.utils;

import android.graphics.Bitmap;

import com.uesugi.mumen.MainActivity;
import com.uesugi.mumen.entity.UploadEntity;
import com.uesugi.mumen.entity.UserEntity;
import com.uesugi.mumen.user.UserActivity;

public class Constants {

	public static boolean Inspection() {
		if (entityUser == null) {
			return true;
		} else {
			return false;
		}
	}

	// ========省份城市 全局变量========
	public static String province = "1";
	public static String city = "345";
	public static String cityName = "朝阳区";

	public static String pwd;
	public static String name;

	public static double lon = 0;
	public static double lat = 0;

	// public static UserCityEntity userCityEntity = null;

	public static Bitmap testBitmap = null;

	public static Bitmap addBitmap1 = null;
	public static Bitmap addBitmap2 = null;
	public static Bitmap addBitmap3 = null;
	public static Bitmap addBitmap4 = null;
	public static Bitmap addBitmap5 = null;
	public static UploadEntity uploadEntity = null;

	public static Bitmap wyfxBitmap1 = null;
	public static Bitmap wyfxBitmap2 = null;
	public static Bitmap wyfxBitmap3 = null;
	public static Bitmap wyfxBitmap4 = null;
	public static Bitmap wyfxBitmap5 = null;
	public static UploadEntity wyfxUploadEntity = null;

	public static Bitmap dzysBitmap1 = null;
	public static Bitmap dzysBitmap2 = null;
	public static Bitmap dzysBitmap3 = null;
	public static Bitmap dzysBitmap4 = null;
	public static Bitmap dzysBitmap5 = null;
	public static UploadEntity dzysUploadEntity = null;

	public static final String APP_DEBUG = "0";
	public static String TOKEN = "";

	public static int width;
	public static int width5_1;
	public static int height;
	public static String MESSAGE_REQUEST_ERROR2 = "努力加载中...";
	public static String MESSAGE_PROGRESS = "请稍后...";
	public static String MESSAGE_REQUEST_ERROR = "请求失败，请稍后重试!";
	// http://182.92.228.30:8080/Yiht/index.php
	// http://192.168.1.102/Yiht/index.php
	public static final String URL_BASE = "http://app.a3xy.com/zztianhua/index.php";
	public static final String URL_IMAGE = "http://app.a3xy.com/zztianhua/index.php";
	public static String URL_TOKEN = null;

	public static UserEntity entityUser = null;

	// Sdcard相关变量
	public final static String SDCARD_STORAGE_PATH = FileUtils.getSdCardPath();
	public final static String APP_BASE_PATH = SDCARD_STORAGE_PATH + "MengTian";
	public static String TEST_IMAGE = APP_BASE_PATH + "/pic_love_city.jpg";
	// 图片缓存使用常量
	public final static String IMAGE_CACHE_PATH = APP_BASE_PATH + "/imageCache";
	public final static String IMAGE_EDIT_PATH = APP_BASE_PATH + "/imageEdit";

	// token过期
	public static final String TAG_TOKEN_ERROR = "5000";
	// 重新登录
	public static final String TAG_LOGIN_ERROR = "1210";
	public static final String INTENT_LOGIN_PUSH = "login_push";
	public static final String INTENT_LOGIN_VERSION = "login_version";
	public static final String INTENT_LOGIN_PUSH_PAGE = "login_push_page";

	public static final String INTENT_REG1_INFO_PAGE = "reg1_info_page";
	public static final String INTENT_REG1_INFO_PAGE_STRING = "reg1_info_page_string";
	public static final String INTENT_REG1_INFO_PAGE_ID = "reg1_info_page_id";
	public static final String INTENT_FIELD_ID = "field_id";
	public static final String INTENT_FIELD_NAME = "field_name";
	public static final String INTENT_FIELD_AREA_NAME = "field_area_name";

	public static final String INTENT_SHOP_INFO = "shop_info";
	public static final String INTENT_GOODS_INFO_ID = "goods_info_id";

	public static final String INTENT_PIC_MAP = "pic_map";
	public static final String INTENT_PIC_MAP_LOC = "pic_map_loc";
	public static String Location_lat = "41.750821";
	public static String Location_lon = "123.330997";

	public final static int RESULT_RLOGIN = 9999;

	// 返回相关
	public final static int REQUEST_USER_LOGIN = 111;
	public final static int REQUEST_USER_INFO = 114;
	public final static int REQUEST_USER_PWD = 115;
	public final static int REQUEST_USER_FORGET = 116;

	public final static int REQUEST_ADDRESS_ADD = 201;
	public final static int REQUEST_ADDRESS = 202;
	public final static int REQUEST_ADDRESS_DEL = 203;
	public final static int REQUEST_SHOP_COMMENT = 301;
	public final static int REQUEST_SHOPING_ORDER = 401;
	public final static int REQUEST_SHOPING_ORDER_INFO = 402;
	public final static int REQUEST_USER_INFO1 = 5001;
	public final static int REQUEST_USER_INFO2 = 5002;
	public final static int REQUEST_RETURN_INFO = 1102;
	public final static int REQUEST_RETURN_INFO_BANK = 1103;
	public final static int REQUEST_BBS_SET_REPLY = 1103;

	public final static int REQUEST_CITY = 2001;

	public final static int REQUEST_AREA = 4002;
	public final static int REQUEST_ROLE = 4003;
	public final static int REQUEST_AREA2 = 4004;
	public final static int REQUEST_MORE_CAMERA = 1203;
	public final static int REQUEST_MORE_PHOTO_CROP = 1206;
	public final static int REQUEST_MORE_PICTURE = 1204;
	public final static int REQUEST_BLOG_INFO = 301;
	public final static int REQUEST_BLOG_SEND = 302;
	public final static int REQUEST_BLOG_SEND_COMMENT = 303;

	public final static int REQUEST_DISEASE_SEND = 312;
	public final static int REQUEST_TO_DOCTOR_SEND = 2001;
	public final static int REQUEST_TO_DOCTOR_MORE = 2002;
	public final static int REQUEST_MY_INFO = 3001;
	public final static int REQUEST_MY_AREA = 3003;
	public final static int REQUEST_DOCTOR_ADD = 3304;

	public final static int REQUEST_MY_INFO_INFO = 3002;

	public final static int REQUEST_MSG_INFO2 = 4002;

	public final static String FILTEL_LOGIN = "filtel_login";
	public final static String FILTEL_LOGIN_STATE = "filtel_login_state";
	public final static String FILTEL_LOGIN_MESSAGE = "filtel_login_message";

	// public static MyMsgActivity mma = null;
	// public static MyMsgInfoActivity mmia = null;
	public static MainActivity mainActivity = null;
	public static UserActivity userActivity = null;
	// public static MeActivity mea = null;
	// public static String uid = null;

	public static String WD_MSG = "0";
	public static String WD_PMSG = "0";
	public static String WD_COMMENT = "0";

	// public static MeActivity me = null;

}
