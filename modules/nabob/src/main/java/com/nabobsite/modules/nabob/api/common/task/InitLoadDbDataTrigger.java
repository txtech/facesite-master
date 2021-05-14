package com.nabobsite.modules.nabob.api.common.task;

import com.nabobsite.modules.nabob.api.common.trigger.TriggerOperation;
import com.nabobsite.modules.nabob.api.common.trigger.TriggerPoolManagerImpl;
import com.nabobsite.modules.nabob.api.common.trigger.TriggerThread;
import com.nabobsite.modules.nabob.api.entity.CommonContact;
import com.nabobsite.modules.nabob.api.entity.LogicStaticContact;
import com.nabobsite.modules.nabob.api.entity.NabobI18n;
import com.nabobsite.modules.nabob.api.service.UserAccountApiService;
import com.nabobsite.modules.nabob.cms.sys.dao.SysI18nDao;
import com.nabobsite.modules.nabob.cms.sys.entity.SysI18n;
import com.nabobsite.modules.nabob.cms.user.dao.UserInfoDao;
import com.nabobsite.modules.nabob.cms.user.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.Lint;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
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

	public InitLoadDbDataTrigger(SysI18nDao sysI18nDao) {
		super("");
		this.sysI18nDao = sysI18nDao;
	}

	@Override
	public void execute() {
		LOG.info("系统启动触发器");
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
		NabobI18n.LOCAL_CACHE.put(NabobI18n.LANG_EN,enUSMap);
		NabobI18n.LOCAL_CACHE.put(NabobI18n.LANG_IN,enINMap);
		NabobI18n.LOCAL_CACHE.put(NabobI18n.LANG_ZH,zhCNMap);
	}

	public SysI18nDao getSysI18nDao() {
		return sysI18nDao;
	}

	public void setSysI18nDao(SysI18nDao sysI18nDao) {
		this.sysI18nDao = sysI18nDao;
	}
}
