package com.platform.mesh.app.biz.modules.app.formcolumnmapping.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.dto.AppFormColumnMappingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.vo.AppFormColumnMappingVO;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.exception.AppFormColumnMappingExceptionEnum;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.mapper.AppFormColumnMappingMapper;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.service.IAppFormColumnMappingService;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.service.manual.AppFormColumnMappingServiceManual;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.po.AppFormColumnMapping;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段映射
 * @author 蝉鸣
 */
@Service
public class AppFormColumnMappingServiceImpl extends ServiceImpl<AppFormColumnMappingMapper, AppFormColumnMapping> implements IAppFormColumnMappingService  {

    @Autowired
    private AppFormColumnMappingServiceManual appFormColumnMappingServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param formColumnMappingId formColumnMappingId  
     * @return 正常返回:{@link AppFormColumnMappingVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnMappingVO getFormColumnMappingInfoById(Long formColumnMappingId) {
        AppFormColumnMapping appFormColumnMapping = this.getById(formColumnMappingId);
        return appFormColumnMappingServiceManual.getFormColumnMappingInfoById(appFormColumnMapping);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link AppFormColumnMappingVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnMappingVO addFormColumnMapping(AppFormColumnMappingDTO formColumnMappingDTO) {
        AppFormColumnMapping appFormColumnMapping = BeanUtil.copyProperties(formColumnMappingDTO, AppFormColumnMapping.class);
        this.save(appFormColumnMapping);
        return BeanUtil.copyProperties(appFormColumnMapping, AppFormColumnMappingVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link AppFormColumnMappingVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnMappingVO editFormColumnMapping(AppFormColumnMappingDTO formColumnMappingDTO) {
        if(ObjectUtil.isEmpty(formColumnMappingDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AppFormColumnMappingDTO::getId);
            throw AppFormColumnMappingExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AppFormColumnMapping appFormColumnMapping = BeanUtil.copyProperties(formColumnMappingDTO, AppFormColumnMapping.class);
        this.updateById(appFormColumnMapping);
        return BeanUtil.copyProperties(appFormColumnMapping, AppFormColumnMappingVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param formColumnMappingId formColumnMappingId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteFormColumnMapping(Long formColumnMappingId) {
        
        return this.removeById(formColumnMappingId);
    }
}