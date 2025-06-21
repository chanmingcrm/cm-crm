package com.platform.mesh.app.biz.modules.app.formbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.formbase.domain.dto.AppFormBaseDTO;
import com.platform.mesh.app.biz.modules.app.formbase.domain.dto.AppFormBasePageDTO;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.app.biz.modules.app.formbase.domain.vo.AppFormBaseVO;
import com.platform.mesh.mybatis.plus.extention.MPage;

import java.util.Map;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 单信息
 * @author 蝉鸣
 */
public interface IAppFormBaseService extends IService<AppFormBase> {

    /***
     * 功能描述:
     * 〈页面查询〉
     * @param appFormBasePageDTO appFormBasePageDTO
     * @return 正常返回:{@link MPage<AppFormBase>}
     * @author 蝉鸣
     * @since 2024/8/29 17:08
     */
    MPage<AppFormBase> selectPage(AppFormBasePageDTO appFormBasePageDTO);

    /**
     * 功能描述:
     * 〈获取当前单信息〉
     * @param formBaseId formBaseId
     * @return 正常返回:{@link AppFormBaseVO}
     * @author 蝉鸣
     */
    AppFormBaseVO getFormBaseInfoById(Long formBaseId);

    /**
     * 功能描述:
     * 〈新增单〉
     * @param formBaseDTO formBaseDTO
     * @return 正常返回:{@link AppFormBaseVO}
     * @author 蝉鸣
     */
    AppFormBaseVO addFormBase(AppFormBaseDTO formBaseDTO);

    /**
     * 功能描述:
     * 〈修改单〉
     * @param formBaseDTO formBaseDTO
     * @return 正常返回:{@link AppFormBaseVO}
     * @author 蝉鸣
     */
    AppFormBaseVO editFormBase(AppFormBaseDTO formBaseDTO);

    /**
     * 功能描述:
     * 〈删除单〉
     * @param formBaseId formBaseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteFormBase(Long formBaseId);

    /**
     * 功能描述:
     * 〈复制表单〉
     * @param sourceModuleId sourceModuleId
     * @param targetModuleId targetModuleId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Map<Long, Long> copyFormBase(Long sourceModuleId, Long targetModuleId);

    /**
     * 功能描述:
     * 〈获取当前默认类型表单信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link AppFormBaseVO}
     * @author 蝉鸣
     */
    AppFormBaseVO getFormBaseDefaultByModuleId(Long moduleId, Integer formType);
}