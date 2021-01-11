/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.facesite.modules.game.xiao.entity.HgameUserRef;
import com.facesite.modules.game.xiao.dao.HgameUserRefDao;

/**
 * 用户游戏记录Service
 * @author nada
 * @version 2021-01-11
 */
@Service
@Transactional(readOnly=true)
public class HgameUserRefService extends CrudService<HgameUserRefDao, HgameUserRef> {
	
	/**
	 * 获取单条数据
	 * @param hgameUserRef
	 * @return
	 */
	@Override
	public HgameUserRef get(HgameUserRef hgameUserRef) {
		return super.get(hgameUserRef);
	}
	
	/**
	 * 查询分页数据
	 * @param hgameUserRef 查询条件
	 * @param hgameUserRef.page 分页对象
	 * @return
	 */
	@Override
	public Page<HgameUserRef> findPage(HgameUserRef hgameUserRef) {
		return super.findPage(hgameUserRef);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param hgameUserRef
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HgameUserRef hgameUserRef) {
		super.save(hgameUserRef);
	}
	
	/**
	 * 更新状态
	 * @param hgameUserRef
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HgameUserRef hgameUserRef) {
		super.updateStatus(hgameUserRef);
	}
	
	/**
	 * 删除数据
	 * @param hgameUserRef
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HgameUserRef hgameUserRef) {
		super.delete(hgameUserRef);
	}
	
}