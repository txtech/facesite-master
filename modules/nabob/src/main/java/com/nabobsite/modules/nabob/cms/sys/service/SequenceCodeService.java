/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.sys.entity.SequenceCode;
import com.nabobsite.modules.nabob.cms.sys.dao.SequenceCodeDao;

/**
 * t1_sequence_codeService
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class SequenceCodeService extends CrudService<SequenceCodeDao, SequenceCode> {

	@Transactional(readOnly=false)
	public synchronized Long getSequence() {
		dao.addSequence();
		return dao.getNextSequence();
	}

	/**
	 * 获取单条数据
	 * @param sequenceCode
	 * @return
	 */
	@Override
	public SequenceCode get(SequenceCode sequenceCode) {
		return super.get(sequenceCode);
	}
	
	/**
	 * 查询分页数据
	 * @param sequenceCode 查询条件
	 * @param sequenceCode.page 分页对象
	 * @return
	 */
	@Override
	public Page<SequenceCode> findPage(SequenceCode sequenceCode) {
		return super.findPage(sequenceCode);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param sequenceCode
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SequenceCode sequenceCode) {
		super.save(sequenceCode);
	}
	
	/**
	 * 更新状态
	 * @param sequenceCode
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SequenceCode sequenceCode) {
		super.updateStatus(sequenceCode);
	}
	
	/**
	 * 删除数据
	 * @param sequenceCode
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SequenceCode sequenceCode) {
		super.delete(sequenceCode);
	}


}