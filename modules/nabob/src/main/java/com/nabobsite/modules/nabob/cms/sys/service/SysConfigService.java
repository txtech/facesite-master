/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.sys.entity.SysConfig;
import com.nabobsite.modules.nabob.cms.sys.dao.SysConfigDao;

/**
 * 系统配置Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class SysConfigService extends CrudService<SysConfigDao, SysConfig> {
	
	/**
	 * 获取单条数据
	 * @param sysConfig
	 * @return
	 */
	@Override
	public SysConfig get(SysConfig sysConfig) {
		return super.get(sysConfig);
	}
	
	/**
	 * 查询分页数据
	 * @param sysConfig 查询条件
	 * @param sysConfig.page 分页对象
	 * @return
	 */
	@Override
	public Page<SysConfig> findPage(SysConfig sysConfig) {
		return super.findPage(sysConfig);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param sysConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SysConfig sysConfig) {
		super.save(sysConfig);
	}
	
	/**
	 * 更新状态
	 * @param sysConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SysConfig sysConfig) {
		super.updateStatus(sysConfig);
	}
	
	/**
	 * 删除数据
	 * @param sysConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SysConfig sysConfig) {
		super.delete(sysConfig);
	}
	
}