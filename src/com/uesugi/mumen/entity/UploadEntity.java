package com.uesugi.mumen.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆信息
 * 
 * @author whtt
 * 
 */
public class UploadEntity extends HttpRequestEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<String> imgs = new ArrayList<String>();

}
