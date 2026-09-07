package com.platform.mesh.app.biz.modules.app.formcolumnrole.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.dto.AppFormColumnRoleAddDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.po.AppFormColumnRole;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.vo.AppFormColumnRoleVO;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.mapper.AppFormColumnRoleMapper;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.service.IAppFormColumnRoleService;
import com.platform.mesh.core.exception.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前Service 相关方法，所有封装转换方法在Manual中进行
 * @description 表单字段权限
 * @author 蝉鸣
 */
@Service
public class AppFormColumnRoleServiceImpl extends ServiceImpl<AppFormColumnRoleMapper, AppFormColumnRole> implements IAppFormColumnRoleService {


    /**
     * 功能描述:
     * 【新增】
     * @param addDTO addDTO
     * @return 正常返回:{@link AppFormColumnRoleVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public Boolean addFormColumnRole(AppFormColumnRoleAddDTO addDTO) {
        if(CollUtil.isEmpty(addDTO.getColumnIds())){
            return Boolean.FALSE;
        }
        //删除旧数据
        this.lambdaUpdate()
                .eq(AppFormColumnRole::getModuleId,addDTO.getModuleId())
                .eq(AppFormColumnRole::getFormId,addDTO.getFormId())
                .eq(AppFormColumnRole::getRoleId,addDTO.getRoleId())
                .remove();
        //组件新数据
        List<AppFormColumnRole> addList = CollUtil.newArrayList();
        for (Long columnId : addDTO.getColumnIds()) {
            AppFormColumnRole appFormColumnRole = BeanUtil.copyProperties(addDTO, AppFormColumnRole.class);
            appFormColumnRole.setColumnId(columnId);
            addList.add(appFormColumnRole);
        }
        this.saveBatch(addList);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 【删除】
     * @param roleId roleId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteFormColumnRole(Long moduleId,Long formId,Long roleId) {
        return this.lambdaUpdate()
                .eq(AppFormColumnRole::getModuleId,moduleId)
                .eq(AppFormColumnRole::getFormId,formId)
                .eq(AppFormColumnRole::getRoleId,roleId)
                .remove();
    }

}
