/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.nabobsite.modules.nabob.cms.product.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.nabobsite.modules.nabob.cms.product.entity.ProductUserBotAistart;

import java.util.List;

/**
 * 用户产品机器人智能任务DAO接口
 * @author face
 * @version 2021-05-27
 */
@MyBatisDao
public interface ProductUserBotAistartDao extends CrudDao<ProductUserBotAistart> {

    long updateUserBotAistart(ProductUserBotAistart productUserBotAistart);

    List<ProductUserBotAistart> getProductUserBotAistartList(ProductUserBotAistart productUserBotAistart);
}