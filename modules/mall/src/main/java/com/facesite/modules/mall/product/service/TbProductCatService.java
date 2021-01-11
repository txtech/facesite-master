/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.mall.product.service;

import com.facesite.modules.mall.product.entity.TbProductCat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.facesite.modules.mall.product.dao.TbProductCatDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 商品分类Service
 * @author nada
 * @version 2021-01-07
 */
@Service
@Transactional(readOnly=true)
public class TbProductCatService extends CrudService<TbProductCatDao, TbProductCat> {

	/**
	 * 获取单条数据
	 * @param tbProductCat
	 * @return
	 */
	@Override
	public TbProductCat get(TbProductCat tbProductCat) {
		return super.get(tbProductCat);
	}

	/**
	 * 查询分页数据
	 * @param tbProductCat 查询条件
	 * @return
	 */
	@Override
	public Page<TbProductCat> findPage(TbProductCat tbProductCat) {
		return super.findPage(tbProductCat);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param tbProductCat
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbProductCat tbProductCat) {
		super.save(tbProductCat);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbProductCat.getId(), "tbProductCat_image");
	}

	/**
	 * 更新状态
	 * @param tbProductCat
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbProductCat tbProductCat) {
		super.updateStatus(tbProductCat);
	}

	/**
	 * 删除数据
	 * @param tbProductCat
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbProductCat tbProductCat) {
		super.delete(tbProductCat);
	}

}
