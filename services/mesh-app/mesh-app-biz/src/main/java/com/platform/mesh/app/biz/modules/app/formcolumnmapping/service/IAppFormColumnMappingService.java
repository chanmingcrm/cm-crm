package com.platform.mesh.app.biz.modules.app.formcolumnmapping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.dto.AppFormColumnMappingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.po.AppFormColumnMapping;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.vo.AppFormColumnMappingVO;

import java.util.List;




/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 单字段映射信息
 * @author 蝉鸣
 */
public interface IAppFormColumnMappingService extends IService<AppFormColumnMapping> {


    /**
     * 功能描述:
     * 〈获取当前单字段映射信息〉
     * @param formColumnMappingId formColumnMappingId
     * @return 正常返回:{@link AppFormColumnMappingVO}
     * @author 蝉鸣
     */
    AppFormColumnMappingVO getFormColumnMappingInfoById(Long formColumnMappingId);

    /**
     * 功能描述:
     * 〈新增单字段映射〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link AppFormColumnMappingVO}
     * @author 蝉鸣
     */
    AppFormColumnMappingVO addFormColumnMapping(AppFormColumnMappingDTO formColumnMappingDTO);

    /**
     * 功能描述:
     * 〈修改单字段映射〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link AppFormColumnMappingVO}
     * @author 蝉鸣
     */
    AppFormColumnMappingVO editFormColumnMapping(AppFormColumnMappingDTO formColumnMappingDTO);

    /**
     * 功能描述:
     * 〈删除单字段映射〉
     * @param formColumnMappingId formColumnMappingId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteFormColumnMapping(Long formColumnMappingId);
}