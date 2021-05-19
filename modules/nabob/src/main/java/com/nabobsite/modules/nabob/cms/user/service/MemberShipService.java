/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.user.entity.MemberShip;
import com.nabobsite.modules.nabob.cms.user.dao.MemberShipDao;

/**
 * 任务管理Service
 * @author face
 * @version 2021-05-19
 */
@Service
@Transactional(readOnly=true)
public class MemberShipService extends CrudService<MemberShipDao, MemberShip> {
	
	/**
	 * 获取单条数据
	 * @param memberShip
	 * @return
	 */
	@Override
	public MemberShip get(MemberShip memberShip) {
		return super.get(memberShip);
	}
	
	/**
	 * 查询分页数据
	 * @param memberShip 查询条件
	 * @param memberShip.page 分页对象
	 * @return
	 */
	@Override
	public Page<MemberShip> findPage(MemberShip memberShip) {
		return super.findPage(memberShip);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param memberShip
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(MemberShip memberShip) {
		super.save(memberShip);
	}
	
	/**
	 * 更新状态
	 * @param memberShip
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(MemberShip memberShip) {
		super.updateStatus(memberShip);
	}
	
	/**
	 * 删除数据
	 * @param memberShip
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(MemberShip memberShip) {
		super.delete(memberShip);
	}
	
}