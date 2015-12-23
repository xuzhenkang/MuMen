package com.uesugi.mumen.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * BBS实体
 * 
 * @author whtt
 * 
 */

public class TopEntity extends Object implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String id = "";
	public String name = "";
	public String icon = "";
	public String province_id = "";
	public String city_id = "";
	public String area_id = "";
	public String address = "";
	public List<ShopDataEntity> report = new ArrayList<ShopDataEntity>();

}
