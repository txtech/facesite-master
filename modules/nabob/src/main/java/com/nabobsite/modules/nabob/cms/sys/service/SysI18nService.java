/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.sys.entity.SysI18n;
import com.nabobsite.modules.nabob.cms.sys.dao.SysI18nDao;

/**
 * 用户任务Service
 * @author face
 * @version 2021-05-14
 */
@Service
@Transactional(readOnly=true)
public class SysI18nService extends CrudService<SysI18nDao, SysI18n> {
	
	/**
	 * 获取单条数据
	 * @param sysI18n
	 * @return
	 */
	@Override
	public SysI18n get(SysI18n sysI18n) {
		return super.get(sysI18n);
	}
	
	/**
	 * 查询分页数据
	 * @param sysI18n 查询条件
	 * @param sysI18n.page 分页对象
	 * @return
	 */
	@Override
	public Page<SysI18n> findPage(SysI18n sysI18n) {
		return super.findPage(sysI18n);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param sysI18n
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SysI18n sysI18n) {
		super.save(sysI18n);
	}
	
	/**
	 * 更新状态
	 * @param sysI18n
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SysI18n sysI18n) {
		super.updateStatus(sysI18n);
	}
	
	/**
	 * 删除数据
	 * @param sysI18n
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SysI18n sysI18n) {
		super.delete(sysI18n);
	}
	
}