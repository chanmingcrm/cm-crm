package com.platform.mesh.app.biz.modules.app.allgroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.allgroup.domain.dto.AppAllGroupDTO;
import com.platform.mesh.app.biz.modules.app.allgroup.domain.po.AppAllGroup;
import com.platform.mesh.app.biz.modules.app.allgroup.domain.vo.AppAllGroupVO;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块分组信息
 * @author 蝉鸣
 */
public interface IAppAllGroupService extends IService<AppAllGroup> {


    /**
     * 功能描述:
     * 〈获取当前模块分组信息〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link AppAllGroupVO}
     * @author 蝉鸣
     */
    AppAllGroupVO getAllGroupInfoById(Long allGroupId);

    /**
     * 功能描述:
     * 〈新增模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link AppAllGroupVO}
     * @author 蝉鸣
     */
    AppAllGroupVO addAllGroup(AppAllGroupDTO allGroupDTO);

    /**
     * 功能描述:
     * 〈修改模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link AppAllGroupVO}
     * @author 蝉鸣
     */
    AppAllGroupVO editAllGroup(AppAllGroupDTO allGroupDTO);

    /**
     * 功能描述:
     * 〈删除模块分组〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAllGroup(Long allGroupId);
}