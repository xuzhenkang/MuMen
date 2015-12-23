package com.uesugi.mumen.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * BBS实体列表
 * 
 * @author whtt
 * 
 */
public class ShopDataListEntity extends HttpRequestEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<ShopDataEntity> list = new ArrayList<ShopDataEntity>();

}
