package com.uesugi.mumen.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * BBS实体列表
 * 
 * @author whtt
 * 
 */
public class FieldListEntity extends HttpRequestEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<FieldEntity> list = new ArrayList<FieldEntity>();

}
