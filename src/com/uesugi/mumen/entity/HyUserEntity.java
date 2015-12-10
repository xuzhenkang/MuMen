package com.uesugi.mumen.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * 
 * 
 * @author whtt
 * 
 */
@Table(name = "com_mumen_entity")
// 自定义创建表名 否则 表名默认为 com_memoir_entity_memoir
public class HyUserEntity {

	@Id(column = "id")
	// 自定义主键名称
	private String id;
	private String name = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
