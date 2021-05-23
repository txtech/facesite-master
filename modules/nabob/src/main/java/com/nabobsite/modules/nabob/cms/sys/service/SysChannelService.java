/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.sys.entity.SysChannel;
import com.nabobsite.modules.nabob.cms.sys.dao.SysChannelDao;

/**
 * 通道配置Service
 * @author face
 * @version 2021-05-23
 */
@Service
@Transactional(readOnly=true)
public class SysChannelService extends CrudService<SysChannelDao, SysChannel> {
	
	/**
	 * 获取单条数据
	 * @param sysChannel
	 * @return
	 */
	@Override
	public SysChannel get(SysChannel sysChannel) {
		return super.get(sysChannel);
	}
	
	/**
	 * 查询分页数据
	 * @param sysChannel 查询条件
	 * @param sysChannel.page 分页对象
	 * @return
	 */
	@Override
	public Page<SysChannel> findPage(SysChannel sysChannel) {
		return super.findPage(sysChannel);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param sysChannel
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SysChannel sysChannel) {
		super.save(sysChannel);
	}
	
	/**
	 * 更新状态
	 * @param sysChannel
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SysChannel sysChannel) {
		super.updateStatus(sysChannel);
	}
	
	/**
	 * 删除数据
	 * @param sysChannel
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SysChannel sysChannel) {
		super.delete(sysChannel);
	}
	
}