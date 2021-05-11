/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.base.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.base.entity.Sequence;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;

/**
 * 会员用户DAO接口
 * @author face
 * @version 2021-05-11
 */
@MyBatisDao
public interface SequenceDao extends CrudDao<Sequence> {

    int addSequence();

    Long getNextSequence();
}
