/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.base.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.nabobsite.modules.nabob.cms.base.dao.SequenceDao;
import com.nabobsite.modules.nabob.cms.base.entity.Sequence;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员用户Service
 * @author face
 * @version 2021-05-11
 */
@Service
@Transactional(readOnly=true)
public class SequenceService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SequenceDao sequenceDao;

	/**
	 * @desc 创建序列
	 * @author nada
	 * @create 2021/5/11 8:30 下午
	*/
	@Transactional(readOnly=false)
	public synchronized long getSequence() {
		try {
			Long seqId = sequenceDao.getNextSequence();
			sequenceDao.addSequence();
			return seqId;
		} catch (Exception e) {
			logger.error("创建序列异常",e);
			 throw new IllegalArgumentException("创建序列异常");
		}
	}
}
