package com.platform.mesh.app.biz.modules.app.formcolumn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.bo.ModuleFieldBO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 单字段关联
 * @author 蝉鸣
 */
public interface AppFormColumnMapper extends BaseMapper<AppFormColumn> {

    /**
     * 功能描述:
     * 〈获取排序后得字段信息〉
     * @param moduleId moduleId
     * @param formId formId
     * @return 正常返回:{@link List<AppFormColumn>}
     * @author 蝉鸣
     */
    List<AppFormColumn> getFormColumnSortList(@Param("moduleId") Long moduleId,@Param("formId") Long formId);

    /**
     * 功能描述:
     * 〈获取排序后得字段信息〉
     * @param moduleId moduleId
     * @param formId formId
     * @return 正常返回:{@link List<AppFormColumn>}
     * @author 蝉鸣
     */
    List<AppFormColumn> getFormColumnBOList(@Param("moduleId") Long moduleId,@Param("formId") Long formId);

    /**
     * 功能描述:
     * 〈删除多余数据〉
     * @param moduleId moduleId
     * @param formId formId
     * @author 蝉鸣
     */
    void deleteLossColumn(@Param("moduleId") Long moduleId, @Param("formId") Long formId);

    /**
     * 功能描述:
     * 〈获取排序后得字段信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link List<ModuleFieldBO>}
     * @author 蝉鸣
     */
    List<ModuleFieldBO> getRelModuleToSync(@Param("moduleId")Long moduleId,@Param("compType") String compType);
}
