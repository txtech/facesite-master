package com.nabobsite.modules.nabob.api.pool.trigger;

import com.nabobsite.modules.nabob.api.pool.manager.TriggerOperation;
import com.nabobsite.modules.nabob.api.common.I18nUtils;
import com.nabobsite.modules.nabob.cms.sys.dao.SysI18nDao;
import com.nabobsite.modules.nabob.cms.sys.entity.SysI18n;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 用户注册触发器
 * @author nada
 * @create 2021/5/12 5:40 下午
*/
public class InitLoadDbDataTrigger extends TriggerOperation {

	private SysI18nDao sysI18nDao;
	private UserInfoDao userInfoDao;

	public InitLoadDbDataTrigger(SysI18nDao sysI18nDao,UserInfoDao userInfoDao) {
		super("",userInfoDao);
		this.sysI18nDao = sysI18nDao;
		this.userInfoDao = userInfoDao;
	}

	@Override
	public void execute() {
		logger.info("系统启动触发器");
		List<SysI18n> sysI18nList = sysI18nDao.findList(new SysI18n());
		if(sysI18nList == null){
			return;
		}
		final Map<String, String> enUSMap = new HashMap<>(sysI18nList.size());
		final Map<String, String> enINMap = new HashMap<>(sysI18nList.size());
		final Map<String, String> zhCNMap = new HashMap<>(sysI18nList.size());
		for (SysI18n sysI18n: sysI18nList) {
			String key = sysI18n.getKey();
			enUSMap.put(key,sysI18n.getEnValue());
			enINMap.put(key,sysI18n.getInValue());
			zhCNMap.put(key,sysI18n.getZhValue());
		}
		I18nUtils.LOCAL_CACHE.put(I18nUtils.LANG_EN,enUSMap);
		I18nUtils.LOCAL_CACHE.put(I18nUtils.LANG_IN,enINMap);
		I18nUtils.LOCAL_CACHE.put(I18nUtils.LANG_ZH,zhCNMap);
	}

	public SysI18nDao getSysI18nDao() {
		return sysI18nDao;
	}

	public void setSysI18nDao(SysI18nDao sysI18nDao) {
		this.sysI18nDao = sysI18nDao;
	}
}
