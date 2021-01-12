/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.facesite.modules.game.xiao.entity.HgameUserInfo;
import com.facesite.modules.game.xiao.dao.HgameUserInfoDao;

/**
 * 用户信息Service
 * @author nada
 * @version 2021-01-11
 */
@Service
@Transactional(readOnly=true)
public class HgameUserInfoService extends CrudService<HgameUserInfoDao, HgameUserInfo> {

	/**
	 * 获取单条数据
	 * @param hgameUserInfo
	 * @return
	 */
	@Override
	public HgameUserInfo get(HgameUserInfo hgameUserInfo) {
		return super.get(hgameUserInfo);
	}

	/**
	 * 查询分页数据
	 * @param hgameUserInfo 查询条件
	 * @return
	 */
	@Override
	public Page<HgameUserInfo> findPage(HgameUserInfo hgameUserInfo) {
		return super.findPage(hgameUserInfo);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param hgameUserInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HgameUserInfo hgameUserInfo) {
		super.save(hgameUserInfo);
	}

	/**
	 * 更新状态
	 * @param hgameUserInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HgameUserInfo hgameUserInfo) {
		super.updateStatus(hgameUserInfo);
	}

	/**
	 * 删除数据
	 * @param hgameUserInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HgameUserInfo hgameUserInfo) {
		super.delete(hgameUserInfo);
	}

}
