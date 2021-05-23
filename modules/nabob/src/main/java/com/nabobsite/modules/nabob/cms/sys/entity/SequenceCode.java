/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * t1_sequence_codeEntity
 * @author face
 * @version 2021-05-23
 */
@Table(name="t1_sequence_code", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
	}, orderBy="a.id DESC"
)
public class SequenceCode extends DataEntity<SequenceCode> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	
	public SequenceCode() {
		this(null);
	}

	public SequenceCode(String id){
		super(id);
	}
	
	@NotBlank(message="name不能为空")
	@Length(min=0, max=15, message="name长度不能超过 15 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}