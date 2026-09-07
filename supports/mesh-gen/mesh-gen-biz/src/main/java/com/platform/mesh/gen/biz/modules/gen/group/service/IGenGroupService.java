package com.platform.mesh.gen.biz.modules.gen.group.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.gen.biz.modules.gen.group.domain.dto.GenAllGroupDTO;
import com.platform.mesh.gen.biz.modules.gen.group.domain.po.GenGroup;
import com.platform.mesh.gen.biz.modules.gen.group.domain.vo.GenAllGroupVO;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 分组信息
 * @author 蝉鸣
 */
public interface IGenGroupService extends IService<GenGroup> {

    /**
     * 功能描述:
     * 〈新增模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link GenAllGroupVO}
     * @author 蝉鸣
     */
    GenAllGroupVO addAllGroup(GenAllGroupDTO allGroupDTO);

    /**
     * 功能描述:
     * 〈修改模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link GenAllGroupVO}
     * @author 蝉鸣
     */
    GenAllGroupVO editAllGroup(GenAllGroupDTO allGroupDTO);

    /**
     * 功能描述:
     * 〈删除模块分组〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAllGroup(Long allGroupId);
}
