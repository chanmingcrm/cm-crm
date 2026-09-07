package com.platform.mesh.app.biz.modules.app.formcolumnsetting.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.dto.AppFormColumnSettingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.po.AppFormColumnSetting;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.vo.AppFormColumnSettingVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.exception.AppFormColumnSettingExceptionEnum;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.mapper.AppFormColumnSettingMapper;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.service.IAppFormColumnSettingService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.service.manual.AppFormColumnSettingServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段配置
 * @author 蝉鸣
 */
@Service
public class AppFormColumnSettingServiceImpl extends ServiceImpl<AppFormColumnSettingMapper, AppFormColumnSetting> implements IAppFormColumnSettingService {

    @Autowired
    private AppFormColumnSettingServiceManual appFormColumnSettingServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param formColumnMappingId formColumnMappingId  
     * @return 正常返回:{@link AppFormColumnSettingVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnSettingVO getFormColumnMappingInfoById(Long formColumnMappingId) {
        AppFormColumnSetting AppFormColumnSetting = this.getById(formColumnMappingId);
        return appFormColumnSettingServiceManual.getFormColumnSettingInfoById(AppFormColumnSetting);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link AppFormColumnSettingVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnSettingVO addFormColumnMapping(AppFormColumnSettingDTO formColumnMappingDTO) {
        AppFormColumnSetting AppFormColumnSetting = BeanUtil.copyProperties(formColumnMappingDTO, AppFormColumnSetting.class);
        this.save(AppFormColumnSetting);
        return BeanUtil.copyProperties(AppFormColumnSetting, AppFormColumnSettingVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link AppFormColumnSettingVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnSettingVO editFormColumnMapping(AppFormColumnSettingDTO formColumnMappingDTO) {
        if(ObjectUtil.isEmpty(formColumnMappingDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AppFormColumnSettingDTO::getId);
            throw AppFormColumnSettingExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AppFormColumnSetting AppFormColumnSetting = BeanUtil.copyProperties(formColumnMappingDTO, AppFormColumnSetting.class);
        this.updateById(AppFormColumnSetting);
        return BeanUtil.copyProperties(AppFormColumnSetting, AppFormColumnSettingVO.class);
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
