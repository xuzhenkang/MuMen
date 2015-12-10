package com.uesugi.mumen.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * BBS实体列表
 * 
 * @author whtt
 * 
 */
public class PicListEntity extends HttpRequestEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<PicEntity> list = new ArrayList<PicEntity>();

}
