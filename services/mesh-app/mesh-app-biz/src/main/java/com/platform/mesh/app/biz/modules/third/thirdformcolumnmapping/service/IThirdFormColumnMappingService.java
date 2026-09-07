package com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.dto.ThirdFormColumnMappingDTO;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.po.ThirdFormColumnMapping;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 第三方字段映射设置信息
 * @author 蝉鸣
 */
public interface IThirdFormColumnMappingService extends IService<ThirdFormColumnMapping> {


    /**
     * 功能描述:
     * 〈获取当前第三方字段映射信息〉
     * @param sourceFlag sourceFlag
     * @return 正常返回:{@link List<ThirdFormColumnMapping>}
     * @author 蝉鸣
     */
    List<ThirdFormColumnMapping> getThirdFormColumnMapping(Integer sourceFlag);

    /**
     * 功能描述:
     * 〈新增第三方字段映射〉
     * @param thirdFormColumnMappingDTOS thirdFormColumnMappingDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addThirdFormColumnMapping(List<ThirdFormColumnMappingDTO> thirdFormColumnMappingDTOS);

}
