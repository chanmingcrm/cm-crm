package com.platform.mesh.crm.biz.modules.crm.allgroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.dto.CrmAllGroupDTO;
import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.po.CrmAllGroup;
import com.platform.mesh.crm.biz.modules.crm.allgroup.domain.vo.CrmAllGroupVO;



/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系分组信息
 * @author 蝉鸣
 */
public interface ICrmAllGroupService extends IService<CrmAllGroup> {

    /**
     * 功能描述:
     * 〈获取当前客户关系分组信息〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link CrmAllGroupVO}
     * @author 蝉鸣
     */
    CrmAllGroupVO getAllGroupInfoById(Long allGroupId);

    /**
     * 功能描述:
     * 〈新增客户关系分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link CrmAllGroupVO}
     * @author 蝉鸣
     */
    CrmAllGroupVO addAllGroup(CrmAllGroupDTO allGroupDTO);

    /**
     * 功能描述:
     * 〈修改客户关系分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link CrmAllGroupVO}
     * @author 蝉鸣
     */
    CrmAllGroupVO editAllGroup(CrmAllGroupDTO allGroupDTO);

    /**
     * 功能描述:
     * 〈删除客户关系分组〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAllGroup(Long allGroupId);
}
