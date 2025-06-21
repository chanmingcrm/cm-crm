package com.platform.mesh.app.biz.modules.app.formcolumn.service;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto.AppFormColumnDTO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto.AppFormColumnPageDTO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnSimpVO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.es.domain.bo.EsIndexMappingBO;
import com.platform.mesh.mybatis.plus.extention.MPage;

import java.util.List;
import java.util.Map;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 单字段关联信息
 * @author 蝉鸣
 */
public interface IAppFormColumnService extends IService<AppFormColumn> {


    /**
     * 功能描述:
     * 〈获取当前单字段关联信息〉
     * @param moduleId moduleId
     * @param formId formId
     * @return 正常返回:{@link AppFormColumnVO}
     * @author 蝉鸣
     */
    List<AppFormColumnVO> getFormColumnVOList(Long moduleId,Long formId);

    /**
     * 功能描述:
     * 〈获取当前单字段信息〉
     * @param moduleId moduleId
     * @param formId formId
     * @return 正常返回:{@link AppFormColumnBO}
     * @author 蝉鸣
     */
    List<AppFormColumnBO> getFormColumnBOList(Long moduleId, Long formId);

    /**
     * 功能描述:
     * 〈获取当前单字段关联信息〉
     * @param moduleId moduleId
     * @param formId formId
     * @return 正常返回:{@link AppFormColumnVO}
     * @author 蝉鸣
     */
    List<AppFormColumnVO> getFormColumnTree(Long moduleId,Long formId);

    /**
     * 功能描述:
     * 〈获取单字段关联信息〉
     * @param formId formId
     * @param columnHash columnHash
     * @return 正常返回:{@link AppFormColumnVO}
     * @author 蝉鸣
     */
    AppFormColumnVO getSingColumnInfo(Long formId, String columnHash);

    /**
     * 功能描述:
     * 〈新增单字段关联〉
     * @param batchId batchId
     * @param formColumnDTOs formColumnDTOs
     * @return 正常返回:{@link AppFormColumnVO}
     * @author 蝉鸣
     */
    Boolean addFormColumn(Long batchId,List<AppFormColumnDTO> formColumnDTOs);

    /**
     * 功能描述:
     * 〈删除单字段关联〉
     * @param formColumnIds formColumnIds
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteFormColumn(List<Long> formColumnIds);

    /***
     * 功能描述:
     * 〈表单列表查询〉
     * @param appFormColumnPageDTO appFormColumnPageDTO
     * @return 正常返回:{@link MPage<AppFormColumn>}
     * @author 蝉鸣
     * @since 2024/8/29 17:15
     */
    MPage<AppFormColumn> page(AppFormColumnPageDTO appFormColumnPageDTO);

    /***
     * 功能描述:
     * 〈根据条件查询〉
     * @param appFormColumnPageDTO appFormColumnPageDTO
     * @return 正常返回:{@link List<AppFormColumn>}
     * @author 蝉鸣
     * @since 2024/8/29 17:45
     */
    List<AppFormColumn> queryList(AppFormColumnPageDTO appFormColumnPageDTO);

    /**
     * 功能描述:
     * 〈新增单字段关联〉
     * @param moduleBaseId moduleBaseId
     * @param moduleIndex moduleIndex
     * @return 正常返回:{@link Map<String,Property>}
     * @author 蝉鸣
     */
    EsIndexMappingBO getFormColumnEsMapping(Long moduleBaseId,String moduleIndex);

    /**
     * 功能描述:
     * 〈复制字段关联〉
     * @param sourceModule sourceModule
     * @param targetModule targetModule
     * @param copyForm copyForm
     * @return 正常返回:{@link Map<Long,AppFormColumn>}
     * @author 蝉鸣
     */
    Map<Long, AppFormColumn> copyFormColumn(AppModuleBase sourceModule, AppModuleBase targetModule, Map<Long, Long> copyForm);

    /**
     * 功能描述:
     * 〈根据moduleId 表单类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link AppFormColumnSimpVO}
     * @author 蝉鸣
     */
    List<AppFormColumnVO> fastColumnVOByModuleAndFormType(Long moduleId, Integer formType);

    /**
     * 功能描述:
     * 〈根据moduleId 表单类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link AppFormColumnSimpVO}
     * @author 蝉鸣
     */
    List<AppFormColumnVO> fastColumnTreeVOByModuleAndFormType(Long moduleId, Integer formType);

    /**
     * 功能描述:
     * 〈根据moduleId 表单类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link AppFormColumnSimpVO}
     * @author 蝉鸣
     */
    List<AppFormColumnBO> fastColumnBOByModuleAndFormType(Long moduleId, Integer formType);

    /**
     * 功能描述:
     * 〈根据moduleId 业务字段类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param columnType columnType
     * @return 正常返回:{@link AppFormColumnSimpVO}
     * @author 蝉鸣
     */
    AppFormColumnVO fastColumnByModuleAndType(Long moduleId, Integer columnType);

}