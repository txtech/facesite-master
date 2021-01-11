/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.facesite.modules.mall.product.service;

import com.facesite.modules.mall.product.entity.TbProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.facesite.modules.mall.product.dao.TbProductDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 商品表Service
 * @author nada
 * @version 2021-01-07
 */
@Service
@Transactional(readOnly=true)
public class TbProductService extends CrudService<TbProductDao, TbProduct> {

	/**
	 * 获取单条数据
	 * @param tbProduct
	 * @return
	 */
	@Override
	public TbProduct get(TbProduct tbProduct) {
		return super.get(tbProduct);
	}

	/**
	 * 查询分页数据
	 * @param tbProduct 查询条件
	 * @return
	 */
	@Override
	public Page<TbProduct> findPage(TbProduct tbProduct) {
		return super.findPage(tbProduct);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param tbProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbProduct tbProduct) {
		super.save(tbProduct);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(tbProduct.getId(), "tbProduct_image");
	}

	/**
	 * 更新状态
	 * @param tbProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbProduct tbProduct) {
		super.updateStatus(tbProduct);
	}

	/**
	 * 删除数据
	 * @param tbProduct
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbProduct tbProduct) {
		super.delete(tbProduct);
	}

}
