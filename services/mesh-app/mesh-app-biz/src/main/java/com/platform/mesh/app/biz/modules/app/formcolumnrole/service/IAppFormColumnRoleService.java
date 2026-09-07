package com.platform.mesh.app.biz.modules.app.formcolumnrole.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.dto.AppFormColumnRoleAddDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.po.AppFormColumnRole;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.vo.AppFormColumnRoleVO;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 表单字段权限信息
 * @author 蝉鸣
 */
public interface IAppFormColumnRoleService extends IService<AppFormColumnRole> {

    /**
     * 功能描述:
     * 【新增表单字段权限】
     * @param addDTO addDTO
     * @return 正常返回:{@link AppFormColumnRoleVO}
     * @author 蝉鸣
     */
    Boolean addFormColumnRole(AppFormColumnRoleAddDTO addDTO);

    /**
     * 功能描述:
     * 【删除表单字段权限】
     * @param roleId roleId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteFormColumnRole(Long moduleId,Long formId,Long roleId);

}
