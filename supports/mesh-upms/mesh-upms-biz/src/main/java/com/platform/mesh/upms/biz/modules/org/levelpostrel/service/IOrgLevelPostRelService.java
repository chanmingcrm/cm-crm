package com.platform.mesh.upms.biz.modules.org.levelpostrel.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.dto.OrgLevelPostRelAddDTO;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.dto.OrgLevelPostRelEditDTO;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.dto.OrgLevelPostRelPageDTO;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.vo.OrgLevelPostRelVO;
import com.platform.mesh.utils.result.Result;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 组织信息
 * @author 蝉鸣
 */
public interface IOrgLevelPostRelService extends IService<OrgLevelPostRel> {


    /**
     * 功能描述:
     * 〈添加层级岗位关系〉
     * @param levelPostRelDTO levelPostRelDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addLevelPost(OrgLevelPostRelAddDTO levelPostRelDTO);

    /**
     * 功能描述:
     * 〈获取层级岗位关系〉
     * @param postId postId
     * @return 正常返回:{@link OrgLevelPostRelVO}
     * @author 蝉鸣
     */
    OrgLevelPostRelVO getLevelPostRelByPostId(Long postId);
}

