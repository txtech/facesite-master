package com.nabobsite.modules.nabob.cms.base;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.DataScope;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.nabobsite.modules.nabob.cms.user.entity.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class BaseDataScopeFilter<T extends DataEntity<?>> {

    /**
     * 添加数据权限过滤条件
     */
    public void addDataScopeFilter(T entity){
        // 添加数据权限过滤条件
        // entity.getSqlMap().getDataScope().addFilter("dsfOffice", "Office","'SGZSZ03'", "a.create_by", DataScope.CTRL_PERMI_HAVE,"");
        // SELECT 1 FROM js_sys_user_data_scope WHERE ctrl_permi = '1' AND ctrl_type = 'Office' AND user_code = 'face_he8m' AND ctrl_data = 'SGZSZ03'
        // 添加自定义权限过滤条件（或者包含所有的部门经理）
        User user =  UserUtils.getUser();
        String userCode = user.getUserCode();
        entity.getSqlMap().getDataScope().addFilter("dsfOffice",
                "EXISTS (SELECT 1 FROM js_sys_user_data_scope WHERE ctrl_permi = '1' AND ctrl_type = 'Office' AND user_code = '"+userCode+"' AND user_code = a.create_by AND ctrl_data = 'SGZSZ03')");
    }

}
