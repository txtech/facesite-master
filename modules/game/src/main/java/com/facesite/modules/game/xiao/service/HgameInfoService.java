/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.facesite.modules.game.xiao.entity.HgameInfo;
import com.facesite.modules.game.xiao.dao.HgameInfoDao;

/**
 * 游戏信息Service
 * @author nada
 * @version 2021-01-11
 */
@Service
@Transactional(readOnly=true)
public class HgameInfoService extends CrudService<HgameInfoDao, HgameInfo> {

	/**
	 * 获取单条数据
	 * @param hgameInfo
	 * @return
	 */
	@Override
	public HgameInfo get(HgameInfo hgameInfo) {
		return super.get(hgameInfo);
	}

	/**
	 * 查询分页数据
	 * @param hgameInfo 查询条件
	 * @return
	 */
	@Override
	public Page<HgameInfo> findPage(HgameInfo hgameInfo) {
		return super.findPage(hgameInfo);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param hgameInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HgameInfo hgameInfo) {
		super.save(hgameInfo);
	}

	/**
	 * 更新状态
	 * @param hgameInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HgameInfo hgameInfo) {
		super.updateStatus(hgameInfo);
	}

	/**
	 * 删除数据
	 * @param hgameInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HgameInfo hgameInfo) {
		super.delete(hgameInfo);
	}

}
