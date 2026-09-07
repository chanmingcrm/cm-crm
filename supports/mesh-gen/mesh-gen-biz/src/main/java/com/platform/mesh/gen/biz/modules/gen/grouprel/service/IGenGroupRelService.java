package com.platform.mesh.gen.biz.modules.gen.grouprel.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.dto.GenGroupRelDTO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.dto.GenGroupRelPageDTO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.po.GenGroupRel;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.vo.GenGroupRelVO;
import com.platform.mesh.mybatis.plus.extention.MPage;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 构建分组模板关系信息
 * @author 蝉鸣
 */
public interface IGenGroupRelService extends IService<GenGroupRel> {


    /**
     * 功能描述:
     * 〈新增模块分组关联〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link GenGroupRelVO}
     * @author 蝉鸣
     */
    MPage<GenGroupRelVO> selectPage(GenGroupRelPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈新增模块分组关联〉
     * @param groupRelDTO groupRelDTO
     * @return 正常返回:{@link GenGroupRelVO}
     * @author 蝉鸣
     */
    Boolean addAllGroupRel(GenGroupRelDTO groupRelDTO);

    /**
     * 功能描述:
     * 〈删除模块分组关联〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAllGroupRel(Long allGroupRelId);
}
