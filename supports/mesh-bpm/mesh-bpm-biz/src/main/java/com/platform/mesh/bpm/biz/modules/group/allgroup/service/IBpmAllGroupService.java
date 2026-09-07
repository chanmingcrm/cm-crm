package com.platform.mesh.bpm.biz.modules.group.allgroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.group.allgroup.domain.dto.BpmAllGroupDTO;
import com.platform.mesh.bpm.biz.modules.group.allgroup.domain.po.BpmAllGroup;
import com.platform.mesh.bpm.biz.modules.group.allgroup.domain.vo.BpmAllGroupVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块分组信息
 * @author 蝉鸣
 */
public interface IBpmAllGroupService extends IService<BpmAllGroup> {


    /**
     * 功能描述:
     * 〈获取当前模块分组信息〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link BpmAllGroupVO}
     * @author 蝉鸣
     */
    BpmAllGroupVO getAllGroupInfoById(Long allGroupId);

    /**
     * 功能描述:
     * 〈新增模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link BpmAllGroupVO}
     * @author 蝉鸣
     */
    BpmAllGroupVO addAllGroup(BpmAllGroupDTO allGroupDTO);

    /**
     * 功能描述:
     * 〈修改模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link BpmAllGroupVO}
     * @author 蝉鸣
     */
    BpmAllGroupVO editAllGroup(BpmAllGroupDTO allGroupDTO);

    /**
     * 功能描述:
     * 〈删除模块分组〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAllGroup(Long allGroupId);
}
