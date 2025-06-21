package com.platform.mesh.bpm.biz.modules.group.allgrouprel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.dto.BpmAllGroupRelDTO;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.po.BpmAllGroupRel;
import com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.vo.BpmAllGroupRelVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块分组关联信息
 * @author 蝉鸣
 */
public interface IBpmAllGroupRelService extends IService<BpmAllGroupRel> {


    /**
     * 功能描述:
     * 〈获取当前模块分组关联信息〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link BpmAllGroupRelVO}
     * @author 蝉鸣
     */
    BpmAllGroupRelVO getAllGroupRelInfoById(Long allGroupRelId);

    /**
     * 功能描述:
     * 〈新增模块分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link BpmAllGroupRelVO}
     * @author 蝉鸣
     */
    BpmAllGroupRelVO addAllGroupRel(BpmAllGroupRelDTO allGroupRelDTO);

    /**
     * 功能描述:
     * 〈修改模块分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link BpmAllGroupRelVO}
     * @author 蝉鸣
     */
    BpmAllGroupRelVO editAllGroupRel(BpmAllGroupRelDTO allGroupRelDTO);

    /**
     * 功能描述:
     * 〈删除模块分组关联〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAllGroupRel(Long allGroupRelId);
}