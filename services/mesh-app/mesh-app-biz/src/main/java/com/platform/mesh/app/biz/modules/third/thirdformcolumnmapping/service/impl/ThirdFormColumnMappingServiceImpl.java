package com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.dto.ThirdFormColumnMappingDTO;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.po.ThirdFormColumnMapping;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.mapper.ThirdFormColumnMappingMapper;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.service.IThirdFormColumnMappingService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 第三方字段映射设置
 * @author 蝉鸣
 */
@Service
public class ThirdFormColumnMappingServiceImpl extends ServiceImpl<ThirdFormColumnMappingMapper, ThirdFormColumnMapping> implements IThirdFormColumnMappingService {

    /**
     * 功能描述:
     * 〈获取当前第三方字段映射信息〉
     * @param sourceFlag sourceFlag
     * @return 正常返回:{@link List<ThirdFormColumnMapping>}
     * @author 蝉鸣
     */
    public List<ThirdFormColumnMapping> getThirdFormColumnMapping(Integer sourceFlag){
        return this.getBaseMapper().getThirdFormColumnMapping(sourceFlag);
    }

    /**
     * 功能描述:
     * 〈新增第三方字段映射〉
     * @param thirdFormColumnMappingDTOS thirdFormColumnMappingDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean addThirdFormColumnMapping(List<ThirdFormColumnMappingDTO> thirdFormColumnMappingDTOS){
        List<ThirdFormColumnMappingDTO> mappingDTOS = thirdFormColumnMappingDTOS.stream()
                .filter(column -> ObjectUtil.isNotEmpty(column.getSourceFlag())).toList();
        if(CollUtil.isEmpty(mappingDTOS)){
            return Boolean.FALSE;
        }
        ThirdFormColumnMappingDTO mappingDTO = CollUtil.getFirst(mappingDTOS);
        //删除旧数据
        this.lambdaUpdate()
                .eq(ThirdFormColumnMapping::getSourceFlag,mappingDTO.getSourceFlag())
                .remove();
        List<ThirdFormColumnMapping> thirdFormColumnMappings = BeanUtil.copyToList(thirdFormColumnMappingDTOS, ThirdFormColumnMapping.class);
        this.saveBatch(thirdFormColumnMappings);
        return Boolean.TRUE;
    }


}
