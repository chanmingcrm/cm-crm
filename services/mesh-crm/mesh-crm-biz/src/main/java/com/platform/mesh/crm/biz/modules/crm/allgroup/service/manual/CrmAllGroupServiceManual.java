package com.platform.mesh.crm.biz.modules.crm.allgroup.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.vo.CrmAllGroupVO;
import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.po.CrmAllGroup;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系分组
 * @author 蝉鸣
 */
@Service
public class CrmAllGroupServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前客户关系分组信息〉
     * @param crmAllGroup crmAllGroup 
     * @return 正常返回:{@link CrmAllGroupVO}
     * @author 蝉鸣
     */
    public CrmAllGroupVO getAllGroupInfoById(CrmAllGroup crmAllGroup) {
        CrmAllGroupVO crmAllGroupVO = new CrmAllGroupVO();
        if(ObjectUtil.isEmpty(crmAllGroupVO)){
            return crmAllGroupVO;
        }
        //转换VO
        BeanUtil.copyProperties(crmAllGroup, crmAllGroupVO);
        return crmAllGroupVO;
    }

}