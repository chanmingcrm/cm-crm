package com.platform.mesh.crm.biz.modules.crm.allgrouprel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.dto.CrmAllGroupRelDTO;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.po.CrmAllGroupRel;
import com.platform.mesh.crm.biz.modules.crm.allgrouprel.domain.vo.CrmAllGroupRelVO;



/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系分组关联信息
 * @author 蝉鸣
 */
public interface ICrmAllGroupRelService extends IService<CrmAllGroupRel> {

    /**
     * 功能描述:
     * 〈获取当前客户关系分组关联信息〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link CrmAllGroupRelVO}
     * @author 蝉鸣
     */
    CrmAllGroupRelVO getAllGroupRelInfoById(Long allGroupRelId);

    /**
     * 功能描述:
     * 〈新增客户关系分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link CrmAllGroupRelVO}
     * @author 蝉鸣
     */
    CrmAllGroupRelVO addAllGroupRel(CrmAllGroupRelDTO allGroupRelDTO);

    /**
     * 功能描述:
     * 〈修改客户关系分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link CrmAllGroupRelVO}
     * @author 蝉鸣
     */
    CrmAllGroupRelVO editAllGroupRel(CrmAllGroupRelDTO allGroupRelDTO);

    /**
     * 功能描述:
     * 〈删除客户关系分组关联〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAllGroupRel(Long allGroupRelId);
}
