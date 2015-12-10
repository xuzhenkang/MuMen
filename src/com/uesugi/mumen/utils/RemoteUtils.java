package com.uesugi.mumen.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.uesugi.mumen.entity.AreaListEntity;
import com.uesugi.mumen.entity.ArticleListEntity;
import com.uesugi.mumen.entity.BaseEntity;
import com.uesugi.mumen.entity.HyEntity;
import com.uesugi.mumen.entity.LiveListEntity;
import com.uesugi.mumen.entity.LoginEntity;
import com.uesugi.mumen.entity.PicListEntity;
import com.uesugi.mumen.entity.TestEntity;
import com.uesugi.mumen.entity.TitleListEntity;
import com.uesugi.mumen.entity.TopListEntity;
import com.uesugi.mumen.entity.UploadEntity;
import com.uesugi.mumen.json.AreaListJosnParser;
import com.uesugi.mumen.json.ArticleListJosnParser;
import com.uesugi.mumen.json.BaseJosnParser;
import com.uesugi.mumen.json.HyJosnParser;
import com.uesugi.mumen.json.LiveListJosnParser;
import com.uesugi.mumen.json.LoginJosnParser;
import com.uesugi.mumen.json.PicListJosnParser;
import com.uesugi.mumen.json.TestJosnParser;
import com.uesugi.mumen.json.TitleListJosnParser;
import com.uesugi.mumen.json.TopListJosnParser;
import com.uesugi.mumen.json.UploadJosnParser;

/**
 * 此类中负责Http请求以及解析处理
 * 
 * @author whtt
 * 
 */

public class RemoteUtils {

	/**
	 * 获取欢迎页
	 */
	public static void getHY(final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Public/get_pic?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				HyEntity entity = new HyEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				HyJosnParser json = new HyJosnParser();
				json.setJson(t.toString());
				HyEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取文章
	 */
	public static void getArticleList(String column_id, String p, String r,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Article/lists?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN + "&column_id="
				+ column_id + "&p=" + p + "&r=" + r;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				ArticleListEntity entity = new ArticleListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				ArticleListJosnParser json = new ArticleListJosnParser();
				json.setJson(t.toString());
				ArticleListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取文章
	 */
	public static void getPicList(String column_id,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Article/pic?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN + "&column_id="
				+ column_id;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				PicListEntity entity = new PicListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				PicListJosnParser json = new PicListJosnParser();
				json.setJson(t.toString());
				PicListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取验证码
	 */
	public static void doVerify(String phone,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/User/Public/verify?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN + "&phone="
				+ phone;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				TestEntity entity = new TestEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				TestJosnParser json = new TestJosnParser();
				json.setJson(t.toString());
				TestEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 登陆
	 */
	public static void doLogin(String phone, String verify, String push_id,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/User/Public/login?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN + "&phone="
				+ phone + "&verify=" + verify;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				System.out.println("t:" + t);
				LoginEntity entity = new LoginEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				LoginJosnParser json = new LoginJosnParser();
				json.setJson(t.toString());
				LoginEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取TOP week
	 */
	public static void getPk(final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Pk/get?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				TopListEntity entity = new TopListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				TopListJosnParser json = new TopListJosnParser();
				json.setJson(t.toString());
				TopListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取TOP week
	 */
	public static void getTopW(final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Top/weeks?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				TopListEntity entity = new TopListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				TopListJosnParser json = new TopListJosnParser();
				json.setJson(t.toString());
				TopListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取TOPmonth
	 */
	public static void getTopM(final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Top/month?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				TopListEntity entity = new TopListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				TopListJosnParser json = new TopListJosnParser();
				json.setJson(t.toString());
				TopListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取TOPquarter
	 */
	public static void getTopQ(final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Top/quarter?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				TopListEntity entity = new TopListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				TopListJosnParser json = new TopListJosnParser();
				json.setJson(t.toString());
				TopListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取TOPyears
	 */
	public static void getTopY(final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Top/years?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				TopListEntity entity = new TopListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				TopListJosnParser json = new TopListJosnParser();
				json.setJson(t.toString());
				TopListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取排行
	 * 
	 * @param category
	 *            (weeks | month | quarter | years)
	 * @param page
	 * @param limit
	 * @param callBack
	 */

	public static void getTopList(String category, String page, String limit,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Top/" + category + "?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN + "&p=" + page
				+ "&r=" + limit;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				TopListEntity entity = new TopListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				TopListJosnParser json = new TopListJosnParser();
				json.setJson(t.toString());
				TopListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 修改头像
	 */

	public static void upDateIcon(File file, File file2, File file3,
			File file4, File file5, final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Upload/img?d="
				+ Constants.APP_DEBUG;

		System.out.println(url);

		try {
			AjaxParams params = new AjaxParams();

			if (file != null) {
				params.put("img[0]", file);
			}
			if (file2 != null) {
				params.put("img[1]", file2);
			}
			if (file3 != null) {
				params.put("img[2]", file3);
			}
			if (file4 != null) {
				params.put("img[3]", file4);
			}
			if (file5 != null) {
				params.put("img[4]", file5);
			}

			params.put("token", Constants.TOKEN);
			FinalHttp fh = new FinalHttp();
			fh.post(url, params, new AjaxCallBack<Object>() {

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					// TODO Auto-generated method stub
					super.onFailure(t, errorNo, strMsg);
					System.out.println("upDateIcon t:" + t);
					UploadEntity entity = new UploadEntity();
					entity.error();
					callBack.result(entity);
				}

				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);

					System.out.println("upDateIcon t:" + t);

					UploadJosnParser json = new UploadJosnParser();
					json.setJson(t.toString());
					UploadEntity entity = json.parser();
					callBack.result(entity);

				}

			});

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

			UploadEntity entity = new UploadEntity();
			entity.error();
			callBack.result(entity);
		}
	}

	/**
	 * 修改头像
	 */

	public static void setShopIcon(File file,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/User/Personal/set?d="
				+ Constants.APP_DEBUG;

		System.out.println(url);

		try {
			AjaxParams params = new AjaxParams();
			if (file != null) {
				if (file != null) {
					params.put("store_icon", file);
				}

			}
			params.put("t", Constants.TOKEN);
			FinalHttp fh = new FinalHttp();
			fh.post(url, params, new AjaxCallBack<Object>() {

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					// TODO Auto-generated method stub
					super.onFailure(t, errorNo, strMsg);
					System.out.println("setShopIcon t:" + t);
					LoginEntity entity = new LoginEntity();
					entity.error();
					callBack.result(entity);
				}

				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);

					System.out.println("setShopIcon t:" + t);

					LoginJosnParser json = new LoginJosnParser();
					json.setJson(t.toString());
					LoginEntity entity = json.parser();
					callBack.result(entity);

				}

			});

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

			LoginEntity entity = new LoginEntity();
			entity.error();
			callBack.result(entity);
		}
	}

	/**
	 * 获取TOP week
	 */
	public static void getShopList(String area_id,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/User/Personal/get_store?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN + "&area_id="
				+ area_id;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				TopListEntity entity = new TopListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				TopListJosnParser json = new TopListJosnParser();
				json.setJson(t.toString());
				TopListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取TOP week
	 */
	public static void getShopListKey(String keyword,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/User/Personal/get_store?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN + "&key="
				+ keyword;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				TopListEntity entity = new TopListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				TopListJosnParser json = new TopListJosnParser();
				json.setJson(t.toString());
				TopListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取TOP week
	 */
	public static void getTitle(String pid,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Column/get?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN + "&pid=" + pid;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				TitleListEntity entity = new TitleListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				TitleListJosnParser json = new TitleListJosnParser();
				json.setJson(t.toString());
				TitleListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 获取TOP week
	 */
	public static void getArea(final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Public/get_area?d="
				+ Constants.APP_DEBUG + "&t=" + Constants.TOKEN;

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				AreaListEntity entity = new AreaListEntity();
				entity.error();
				callBack.result(entity);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("t:" + t);

				AreaListJosnParser json = new AreaListJosnParser();
				json.setJson(t.toString());
				AreaListEntity entity = json.parser();
				callBack.result(entity);

			}

		});

	}

	/**
	 * 提交我要升级
	 */

	public static void upDateAdd(String name, String address, String area,
			String remark, String phone, List<String> imgs,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Upgrade/add?d="
				+ Constants.APP_DEBUG;

		System.out.println(url);

		AjaxParams params = new AjaxParams();
		if (imgs != null) {
			for (int i = 0; i < imgs.size(); i++) {
				if (!StringUtils.isBlank(imgs.get(i))) {
					params.put("imgs[" + i + "]", imgs.get(i));
				}
			}
		}
		params.put("name", name);
		params.put("address", address);
		params.put("area", area);
		params.put("remark", remark);
		params.put("phone", phone);

		params.put("t", Constants.TOKEN);
		FinalHttp fh = new FinalHttp();
		fh.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				System.out.println("upDateAdd t:" + t);
				BaseEntity entity = new BaseEntity();
				entity.error();
				callBack.result(entity);
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("upDateAdd t:" + t);

				BaseJosnParser json = new BaseJosnParser();
				json.setJson(t.toString());
				BaseEntity entity = json.parser();
				callBack.result(entity);

			}

		});
	}

	/**
	 * 获取活动
	 */

	public static void getLive(final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/Api/Live/get?d="
				+ Constants.APP_DEBUG;

		System.out.println(url);

		AjaxParams params = new AjaxParams();

		params.put("t", Constants.TOKEN);
		FinalHttp fh = new FinalHttp();
		fh.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				System.out.println("getLive t:" + t);
				LiveListEntity entity = new LiveListEntity();
				entity.error();
				callBack.result(entity);
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("getLive t:" + t);

				LiveListJosnParser json = new LiveListJosnParser();
				json.setJson(t.toString());
				LiveListEntity entity = json.parser();
				callBack.result(entity);

			}

		});
	}

	/**
	 * 提交我要升级
	 */

	public static void setXshb(String jdkh, String yxkh, String cjkh,
			String fhb, String proceeds, final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/User/Personal/report?d="
				+ Constants.APP_DEBUG;

		System.out.println(url);

		AjaxParams params = new AjaxParams();
		params.put("jdkh", jdkh);
		params.put("yxkh", yxkh);
		params.put("cjkh", cjkh);
		params.put("fhb", fhb);
		params.put("proceeds", proceeds);

		params.put("t", Constants.TOKEN);
		FinalHttp fh = new FinalHttp();
		fh.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				System.out.println("setXshb t:" + t);
				BaseEntity entity = new BaseEntity();
				entity.error();
				callBack.result(entity);
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("setXshb t:" + t);

				BaseJosnParser json = new BaseJosnParser();
				json.setJson(t.toString());
				BaseEntity entity = json.parser();
				callBack.result(entity);

			}

		});
	}

	/**
	 * 发布我要分享
	 */

	public static void setWyfx(String title, String content, String position,
			String name, String address, final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/User/Personal/share?d="
				+ Constants.APP_DEBUG;

		System.out.println(url);

		AjaxParams params = new AjaxParams();
		params.put("title", title);
		params.put("content", content);
		params.put("position", position);
		params.put("name", name);
		params.put("address", address);

		params.put("t", Constants.TOKEN);
		FinalHttp fh = new FinalHttp();
		fh.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				System.out.println("setWyfx t:" + t);
				BaseEntity entity = new BaseEntity();
				entity.error();
				callBack.result(entity);
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("setWyfx t:" + t);

				BaseJosnParser json = new BaseJosnParser();
				json.setJson(t.toString());
				BaseEntity entity = json.parser();
				callBack.result(entity);

			}

		});
	}

	/**
	 * 提交我要升级
	 */

	public static void setDzys(String content, List<String> imgs,
			final WHTTHttpRequestCallBack callBack) {

		String url = Constants.URL_BASE + "/User/Personal/acceptance?d="
				+ Constants.APP_DEBUG;

		System.out.println(url);

		AjaxParams params = new AjaxParams();
		if (imgs != null) {
			for (int i = 0; i < imgs.size(); i++) {
				if (!StringUtils.isBlank(imgs.get(i))) {
					params.put("imgs[" + i + "]", imgs.get(i));
				}
			}
		}
		params.put("content", content);

		params.put("t", Constants.TOKEN);
		FinalHttp fh = new FinalHttp();
		fh.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				System.out.println("setDzys t:" + t);
				BaseEntity entity = new BaseEntity();
				entity.error();
				callBack.result(entity);
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("setDzys t:" + t);

				BaseJosnParser json = new BaseJosnParser();
				json.setJson(t.toString());
				BaseEntity entity = json.parser();
				callBack.result(entity);

			}

		});
	}

	/**
	 * 优酷视频从定向
	 */

	public static void getYouku(String url,
			final WHTTHttpRequestCallBack callBack) {

		System.out.println(url);

		final FinalHttp httpRequest = new FinalHttp();
		httpRequest.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);

				callBack.result(strMsg);

			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);

				System.out.println("getYouku:" + t.toString());
				callBack.result(t.toString());

			}

		});
	}
}
