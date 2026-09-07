package com.platform.mesh.app.biz.modules.app.allgrouprel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.dto.AppAllGroupRelDTO;
import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.po.AppAllGroupRel;
import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.vo.AppAllGroupRelVO;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块分组关联信息
 * @author 蝉鸣
 */
public interface IAppAllGroupRelService extends IService<AppAllGroupRel> {


    /**
     * 功能描述:
     * 〈获取当前模块分组关联信息〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link AppAllGroupRelVO}
     * @author 蝉鸣
     */
    AppAllGroupRelVO getAllGroupRelInfoById(Long allGroupRelId);

    /**
     * 功能描述:
     * 〈新增模块分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link AppAllGroupRelVO}
     * @author 蝉鸣
     */
    AppAllGroupRelVO addAllGroupRel(AppAllGroupRelDTO allGroupRelDTO);

    /**
     * 功能描述:
     * 〈修改模块分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link AppAllGroupRelVO}
     * @author 蝉鸣
     */
    AppAllGroupRelVO editAllGroupRel(AppAllGroupRelDTO allGroupRelDTO);

    /**
     * 功能描述:
     * 〈删除模块分组关联〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAllGroupRel(Long allGroupRelId);
}
