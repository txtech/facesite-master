/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.sys.entity.SysNotice;
import com.nabobsite.modules.nabob.cms.sys.dao.SysNoticeDao;

/**
 * 系统通知Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class SysNoticeService extends CrudService<SysNoticeDao, SysNotice> {
	
	/**
	 * 获取单条数据
	 * @param sysNotice
	 * @return
	 */
	@Override
	public SysNotice get(SysNotice sysNotice) {
		return super.get(sysNotice);
	}
	
	/**
	 * 查询分页数据
	 * @param sysNotice 查询条件
	 * @param sysNotice.page 分页对象
	 * @return
	 */
	@Override
	public Page<SysNotice> findPage(SysNotice sysNotice) {
		return super.findPage(sysNotice);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param sysNotice
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SysNotice sysNotice) {
		super.save(sysNotice);
	}
	
	/**
	 * 更新状态
	 * @param sysNotice
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SysNotice sysNotice) {
		super.updateStatus(sysNotice);
	}
	
	/**
	 * 删除数据
	 * @param sysNotice
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SysNotice sysNotice) {
		super.delete(sysNotice);
	}
	
}