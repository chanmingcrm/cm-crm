package com.platform.mesh.app.biz.modules.app.formcolumnsetevent.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.dto.AppFormColumnSetEventDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.po.AppFormColumnSetEvent;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.vo.AppFormColumnSetEventVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.exception.AppFormColumnSetEventExceptionEnum;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.mapper.AppFormColumnSetEventMapper;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.service.IAppFormColumnSetEventService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.service.manual.AppFormColumnSetEventServiceManual;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.po.AppFormColumnSetRequire;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段事件
 * @author 蝉鸣
 */
@Service
public class AppFormColumnSetEventServiceImpl extends ServiceImpl<AppFormColumnSetEventMapper, AppFormColumnSetEvent> implements IAppFormColumnSetEventService {

    @Autowired
    private AppFormColumnSetEventServiceManual appFormColumnSetEventServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param formColumnSetEventId formColumnSetEventId  
     * @return 正常返回:{@link AppFormColumnSetEventVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnSetEventVO getFormColumnSetEventInfoById(Long formColumnSetEventId) {
        AppFormColumnSetEvent appFormColumnSetEvent = this.getById(formColumnSetEventId);
        return appFormColumnSetEventServiceManual.getFormColumnSetEventInfoById(appFormColumnSetEvent);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param formColumnSetEventDTO formColumnSetEventDTO
     * @return 正常返回:{@link AppFormColumnSetEventVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnSetEventVO addFormColumnSetEvent(AppFormColumnSetEventDTO formColumnSetEventDTO) {
        AppFormColumnSetEvent AppFormColumnSetEvent = BeanUtil.copyProperties(formColumnSetEventDTO, AppFormColumnSetEvent.class);
        this.save(AppFormColumnSetEvent);
        return BeanUtil.copyProperties(AppFormColumnSetEvent, AppFormColumnSetEventVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param formColumnSetEventDTO formColumnSetEventDTO
     * @return 正常返回:{@link AppFormColumnSetEventVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnSetEventVO editFormColumnSetEvent(AppFormColumnSetEventDTO formColumnSetEventDTO) {
        if(ObjectUtil.isEmpty(formColumnSetEventDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AppFormColumnSetEventDTO::getId);
            throw AppFormColumnSetEventExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AppFormColumnSetEvent AppFormColumnSetEvent = BeanUtil.copyProperties(formColumnSetEventDTO, AppFormColumnSetEvent.class);
        this.updateById(AppFormColumnSetEvent);
        return BeanUtil.copyProperties(AppFormColumnSetEvent, AppFormColumnSetEventVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param formColumnSetEventId formColumnSetEventId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteFormColumnSetEvent(Long formColumnSetEventId) {
        
        return this.removeById(formColumnSetEventId);
    }

    /**
     * 功能描述:
     * 〈复制字段事件〉
     * @param sourceModuleId sourceModuleId
     * @param targetModuleId targetModuleId
     * @param copyColumn copyColumn
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Long, AppFormColumnSetEvent> copyFormColumnSetEvent(Long sourceModuleId, Long targetModuleId
            , Map<AppFormColumn, AppFormColumn> copyColumn, Map<Long, AppFormColumnSetRequire> copyRequire) {
        Map<Long, AppFormColumnSetEvent> copyMap = new HashMap<>();
        if(CollUtil.isEmpty(copyColumn)){
            return copyMap;
        }
        //转换Map
        Map<Long, AppFormColumn> idMap = new HashMap<>(copyColumn.size());
        copyColumn.forEach((key, value) -> idMap.put(key.getId(), value));

        Map<String, AppFormColumn> hashMap = new HashMap<>(copyColumn.size());
        copyColumn.forEach((key, value) -> hashMap.put(key.getColumnHash(), value));

        List<AppFormColumnSetEvent> sourceSetEvents = this.lambdaQuery().eq(AppFormColumnSetEvent::getModuleId, sourceModuleId).list();
        if(CollUtil.isEmpty(sourceSetEvents)){
            return copyMap;
        }
        List<AppFormColumnSetEvent> targetSetEvents = sourceSetEvents.stream()
                .filter(sourceEvent->idMap.containsKey(sourceEvent.getColumnId()))
                .map(sourceEvent -> {
            Long id = IdUtil.getSnowflake().nextId();
            AppFormColumnSetEvent targetEvent = new AppFormColumnSetEvent();
            BeanUtil.copyProperties(sourceEvent, targetEvent, ObjFieldUtil.ignoreDefault());
            targetEvent.setId(id);
            targetEvent.setModuleId(targetModuleId);
            //替换事件流数据
            //替换请求ID
            copyRequire.forEach((key, value) -> {
                String replaceAll = JSONUtil.toJsonStr(targetEvent.getEventFlow()).replaceAll(key.toString(), value.getId().toString());
                targetEvent.setEventFlow(JSONUtil.parseObj(replaceAll));
            });
            //替换字段Hash
            hashMap.forEach((key, value) -> {
                String replaceAll = JSONUtil.toJsonStr(targetEvent.getEventFlow()).replaceAll(key, value.getColumnHash());
                targetEvent.setEventFlow(JSONUtil.parseObj(replaceAll));
            });
            AppFormColumn appFormColumn = idMap.get(sourceEvent.getColumnId());
            targetEvent.setFormId(appFormColumn.getFormId());
            targetEvent.setColumnId(appFormColumn.getId());
            targetEvent.setColumnMac(appFormColumn.getColumnMac());
            targetEvent.setColumnName(appFormColumn.getColumnName());
            copyMap.put(sourceEvent.getId(), targetEvent);
            return targetEvent;
        }).toList();
        //批量保存
        this.saveBatch(targetSetEvents);
        return copyMap;
    }
}
