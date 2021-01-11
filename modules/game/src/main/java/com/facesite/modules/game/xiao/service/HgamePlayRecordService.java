/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.game.xiao.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.facesite.modules.game.xiao.entity.HgamePlayRecord;
import com.facesite.modules.game.xiao.dao.HgamePlayRecordDao;

/**
 * 玩游戏记录Service
 * @author nada
 * @version 2021-01-11
 */
@Service
@Transactional(readOnly=true)
public class HgamePlayRecordService extends CrudService<HgamePlayRecordDao, HgamePlayRecord> {
	
	/**
	 * 获取单条数据
	 * @param hgamePlayRecord
	 * @return
	 */
	@Override
	public HgamePlayRecord get(HgamePlayRecord hgamePlayRecord) {
		return super.get(hgamePlayRecord);
	}
	
	/**
	 * 查询分页数据
	 * @param hgamePlayRecord 查询条件
	 * @param hgamePlayRecord.page 分页对象
	 * @return
	 */
	@Override
	public Page<HgamePlayRecord> findPage(HgamePlayRecord hgamePlayRecord) {
		return super.findPage(hgamePlayRecord);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param hgamePlayRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(HgamePlayRecord hgamePlayRecord) {
		super.save(hgamePlayRecord);
	}
	
	/**
	 * 更新状态
	 * @param hgamePlayRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(HgamePlayRecord hgamePlayRecord) {
		super.updateStatus(hgamePlayRecord);
	}
	
	/**
	 * 删除数据
	 * @param hgamePlayRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(HgamePlayRecord hgamePlayRecord) {
		super.delete(hgamePlayRecord);
	}
	
}