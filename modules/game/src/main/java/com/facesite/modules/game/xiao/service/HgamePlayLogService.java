/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.facesite.modules.game.xiao.entity.HgamePlayLog;
import com.facesite.modules.game.xiao.dao.HgamePlayLogDao;

/**
 * 消消乐游戏日志Service
 * @author nada
 * @version 2021-01-16
 */
@Service
@Transactional(readOnly=true)
public class HgamePlayLogService extends CrudService<HgamePlayLogDao, HgamePlayLog> {
	
	/**
	 * 获取单条数据
	 * @param hgamePlayLog
	 * @return
	 */
	@Override
	public HgamePlayLog get(HgamePlayLog hgamePlayLog) {
		return super.get(hgamePlayLog);
	}
	
	/**
	 * 查询分页数据
	 * @param hgamePlayLog 查询条件
	 * @param hgamePlayLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<HgamePlayLog> findPage(HgamePlayLog hgamePlayLog) {
		return super.findPage(hgamePlayLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param hgamePlayLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HgamePlayLog hgamePlayLog) {
		super.save(hgamePlayLog);
	}
	
	/**
	 * 更新状态
	 * @param hgamePlayLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HgamePlayLog hgamePlayLog) {
		super.updateStatus(hgamePlayLog);
	}
	
	/**
	 * 删除数据
	 * @param hgamePlayLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HgamePlayLog hgamePlayLog) {
		super.delete(hgamePlayLog);
	}
	
}