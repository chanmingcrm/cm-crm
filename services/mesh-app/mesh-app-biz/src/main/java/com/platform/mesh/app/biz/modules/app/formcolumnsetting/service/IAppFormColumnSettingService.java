package com.platform.mesh.app.biz.modules.app.formcolumnsetting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.dto.AppFormColumnSettingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.po.AppFormColumnSetting;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.vo.AppFormColumnSettingVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 单字段配置信息
 * @author 蝉鸣
 */
public interface IAppFormColumnSettingService extends IService<AppFormColumnSetting> {


    /**
     * 功能描述:
     * 〈获取当前单字段配置信息〉
     * @param formColumnMappingId formColumnMappingId
     * @return 正常返回:{@link AppFormColumnSettingVO}
     * @author 蝉鸣
     */
    AppFormColumnSettingVO getFormColumnMappingInfoById(Long formColumnMappingId);

    /**
     * 功能描述:
     * 〈新增单字段配置〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link AppFormColumnSettingVO}
     * @author 蝉鸣
     */
    AppFormColumnSettingVO addFormColumnMapping(AppFormColumnSettingDTO formColumnMappingDTO);

    /**
     * 功能描述:
     * 〈修改单字段配置〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link AppFormColumnSettingVO}
     * @author 蝉鸣
     */
    AppFormColumnSettingVO editFormColumnMapping(AppFormColumnSettingDTO formColumnMappingDTO);

    /**
     * 功能描述:
     * 〈删除单字段配置〉
     * @param formColumnMappingId formColumnMappingId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteFormColumnMapping(Long formColumnMappingId);
}