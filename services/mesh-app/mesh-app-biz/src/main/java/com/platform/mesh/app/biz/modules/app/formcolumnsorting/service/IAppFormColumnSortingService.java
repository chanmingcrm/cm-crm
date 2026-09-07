package com.platform.mesh.app.biz.modules.app.formcolumnsorting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.dto.AppFormColumnSettingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.vo.AppFormColumnSettingVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.dto.AppFormColumnSortingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.po.AppFormColumnSorting;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.vo.AppFormColumnSortingVO;
import com.platform.mesh.utils.result.Result;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 单字段排序信息
 * @author 蝉鸣
 */
public interface IAppFormColumnSortingService extends IService<AppFormColumnSorting> {

    /**
     * 功能描述:
     * 〈获取当前单字段排序信息〉
     * @param formId formId
     * @return 正常返回:{@link List<AppFormColumnSortingVO>}
     * @author 蝉鸣
     */
    List<AppFormColumnSortingVO> getFormColumnSortingInfoByFormId(Long formId);

    /**
     * 功能描述:
     * 〈新增单字段排序〉
     * @param formColumnSortingDTOS formColumnSortingDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addFormColumnSorting(List<AppFormColumnSortingDTO> formColumnSortingDTOS);

    /**
     * 功能描述:
     * 〈删除单字段排序〉
     * @param formId formId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteFormColumnSorting(Long formId);
}
