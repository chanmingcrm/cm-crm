package com.platform.mesh.upms.biz.modules.org.postdatascope.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.dto.OrgPostDataScopeDTO;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.po.OrgPostDataScope;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 组织信息
 * @author 蝉鸣
 */
public interface IOrgPostDataScopeService extends IService<OrgPostDataScope> {

    /**
     * 功能描述:
     * 〈通过岗位Id查询岗位权限〉
     * @param postId postId
     * @return 正常返回:{@link List<OrgPostDataScope>}
     * @author 蝉鸣
     */
    List<OrgPostDataScope> selectByPostId(Long postId);

    /**
     * 功能描述:
     * 〈修改岗位-权限〉
     * @param orgPostDataScopeDTO orgPostDataScopeDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean editPostDataScope(List<OrgPostDataScopeDTO> orgPostDataScopeDTO);
}

