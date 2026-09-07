package com.platform.mesh.app.biz.modules.app.formcolumnsetaction.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.dto.AppFormColumnSetActionDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.po.AppFormColumnSetAction;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.vo.AppFormColumnSetActionVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.exception.AppFormColumnSetActionExceptionEnum;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.mapper.AppFormColumnSetActionMapper;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.service.IAppFormColumnSetActionService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.service.manual.AppFormColumnSetActionServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段动作
 * @author 蝉鸣
 */
@Service
public class AppFormColumnSetActionServiceImpl extends ServiceImpl<AppFormColumnSetActionMapper, AppFormColumnSetAction> implements IAppFormColumnSetActionService  {

    @Autowired
    private AppFormColumnSetActionServiceManual appFormColumnSetActionServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param formColumnSetActionId formColumnSetActionId  
     * @return 正常返回:{@link AppFormColumnSetActionVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnSetActionVO getFormColumnSetActionInfoById(Long formColumnSetActionId) {
        AppFormColumnSetAction appFormColumnSetAction = this.getById(formColumnSetActionId);
        return appFormColumnSetActionServiceManual.getFormColumnSetActionInfoById(appFormColumnSetAction);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param formColumnSetActionDTO formColumnSetActionDTO
     * @return 正常返回:{@link AppFormColumnSetActionVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnSetActionVO addFormColumnSetAction(AppFormColumnSetActionDTO formColumnSetActionDTO) {
        AppFormColumnSetAction AppFormColumnSetAction = BeanUtil.copyProperties(formColumnSetActionDTO, AppFormColumnSetAction.class);
        this.save(AppFormColumnSetAction);
        return BeanUtil.copyProperties(AppFormColumnSetAction, AppFormColumnSetActionVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param formColumnSetActionDTO formColumnSetActionDTO
     * @return 正常返回:{@link AppFormColumnSetActionVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnSetActionVO editFormColumnSetAction(AppFormColumnSetActionDTO formColumnSetActionDTO) {
        if(ObjectUtil.isEmpty(formColumnSetActionDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AppFormColumnSetActionDTO::getId);
            throw AppFormColumnSetActionExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AppFormColumnSetAction AppFormColumnSetAction = BeanUtil.copyProperties(formColumnSetActionDTO, AppFormColumnSetAction.class);
        this.updateById(AppFormColumnSetAction);
        return BeanUtil.copyProperties(AppFormColumnSetAction, AppFormColumnSetActionVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param formColumnSetActionId formColumnSetActionId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteFormColumnSetAction(Long formColumnSetActionId) {
        
        return this.removeById(formColumnSetActionId);
    }

    /**
     * 功能描述:
     * 〈复制字段动作〉
     * @param sourceModuleId sourceModuleId
     * @param targetModuleId targetModuleId
     * @param copyColumn copyColumn
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyFormColumnSetAction(Long sourceModuleId, Long targetModuleId, Map<Long, AppFormColumn> copyColumn) {
        if(CollUtil.isEmpty(copyColumn)){
            return;
        }
        List<AppFormColumnSetAction> sourceSetActions = this.lambdaQuery().eq(AppFormColumnSetAction::getModuleId, sourceModuleId).list();
        if(CollUtil.isEmpty(sourceSetActions)){
            return;
        }
        List<AppFormColumnSetAction> targetActions = sourceSetActions.stream()
                .filter(sourceAction->copyColumn.containsKey(sourceAction.getColumnId()))
                .map(sourceAction -> {
            Long id = IdUtil.getSnowflake().nextId();
            AppFormColumnSetAction targetAction = new AppFormColumnSetAction();
            BeanUtil.copyProperties(sourceAction, targetAction, ObjFieldUtil.ignoreDefault());
            targetAction.setId(id);
            targetAction.setModuleId(targetModuleId);
            AppFormColumn appFormColumn = copyColumn.get(sourceAction.getColumnId());
            targetAction.setFormId(appFormColumn.getFormId());
            targetAction.setColumnId(appFormColumn.getId());
            targetAction.setColumnMac(appFormColumn.getColumnMac());
            targetAction.setColumnName(appFormColumn.getColumnName());
            return targetAction;
        }).toList();
        //批量保存
        this.saveBatch(targetActions);
    }
}
