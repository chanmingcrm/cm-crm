package com.platform.mesh.crm.biz.modules.crm.allgrouprel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.vo.CrmAllGroupRelVO;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.po.CrmAllGroupRel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系分组关联
 * @author 蝉鸣
 */
@Service
public class CrmAllGroupRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前客户关系分组关联信息〉
     * @param crmAllGroupRel crmAllGroupRel 
     * @return 正常返回:{@link CrmAllGroupRelVO}
     * @author 蝉鸣
     */
    public CrmAllGroupRelVO getAllGroupRelInfoById(CrmAllGroupRel crmAllGroupRel) {
        CrmAllGroupRelVO crmAllGroupRelVO = new CrmAllGroupRelVO();
        if(ObjectUtil.isEmpty(crmAllGroupRelVO)){
            return crmAllGroupRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(crmAllGroupRel, crmAllGroupRelVO);
        return crmAllGroupRelVO;
    }

}