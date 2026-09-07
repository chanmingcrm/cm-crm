package com.platform.mesh.app.biz.modules.app.modulebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBaseCopyDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBaseDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBasePageDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleRelPageDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleBaseVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleFastPageVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleRelDictVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleRelVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseBO;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块信息
 * @author 蝉鸣
 */
public interface IAppModuleBaseService extends IService<AppModuleBase> {


    /**
     * 功能描述:
     * 〈删除模块〉
     * @param appModuleBasePageDTO appModuleBasePageReqVO
     * @return 正常返回:{@link MPage<AppModuleBase>}
     * @author 蝉鸣
     */
    MPage<AppModuleBase> selectPage(AppModuleBasePageDTO appModuleBasePageDTO);

    /**
     * 功能描述:
     * 〈获取当前模块信息〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link AppModuleBaseVO}
     * @author 蝉鸣
     */
    AppModuleBaseVO getModuleBaseInfoById(Long moduleBaseId);

    /**
     * 功能描述:
     * 〈获取当前模块信息〉
     * @param moduleBaseIds moduleBaseIds
     * @return 正常返回:{@link List<AppModuleBaseVO>}
     * @author 蝉鸣
     */
    List<AppModuleBaseVO> getModuleBaseInfoByIds(List<Long> moduleBaseIds);

    /**
     * 功能描述:
     * 〈根据表单名称获取模块信息:此方法主要用于定时任务执行需要忽略数据权限隔离〉
     * @param appTables appTables
     * @return 正常返回:{@link List<AppModuleBase>}
     * @author 蝉鸣
     */
    List<AppModuleBase> getModuleBaseInfoBySchema(List<String> appTables);

    /**
     * 功能描述:
     * 〈获取当前模块所有子信息〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link AppModuleBase}
     * @author 蝉鸣
     */
    List<AppModuleBase> getModuleBaseChildById(Long moduleBaseId);

    /**
     * 功能描述:
     * 〈新增模块〉
     * @param moduleBaseDTO moduleBaseDTO
     * @return 正常返回:{@link AppModuleBaseVO}
     * @author 蝉鸣
     */
    AppModuleBaseVO addModuleBase(AppModuleBaseDTO moduleBaseDTO);

    /**
     * 功能描述:
     * 〈修改模块〉
     * @param moduleBaseDTO moduleBaseDTO
     * @return 正常返回:{@link AppModuleBaseVO}
     * @author 蝉鸣
     */
    AppModuleBaseVO editModuleBase(AppModuleBaseDTO moduleBaseDTO);

    /**
     * 功能描述:
     * 〈删除模块〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteModuleBase(Long moduleBaseId);

    /**
     * 功能描述:
     * 〈删除模块〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean clearModuleBase(Long moduleBaseId);

    /**
     * 功能描述:
     * 〈发布模块〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean publicModuleBase(Long moduleBaseId);

    /**
     * 功能描述:
     * 〈初始化模块ES〉
     * @param tableSchema tableSchema
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    List<AppModuleBase> initModuleBaseEs(String tableSchema);

    /**
     * 功能描述:
     * 〈拷贝模块〉
     * @param copyDTO copyDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean copyModuleBase(AppModuleBaseCopyDTO copyDTO);

    /**
     * 功能描述:
     * 〈获取模块默认列表配置信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link AppModuleFastPageVO}
     * @author 蝉鸣
     */
    AppModuleFastPageVO fastPageFormBase(Long moduleId);

    /**
     * 功能描述:
     * 〈获取模块关联字典分页〉
     * @param appModuleRelPageDTO appModuleRelPageDTO
     * @return 正常返回:{@link MPage<DictBaseBO>}
     * @author 蝉鸣
     */
    MPage<AppModuleRelDictVO> selectRelDictPage(AppModuleRelPageDTO appModuleRelPageDTO);

    /**
     * 功能描述:
     * 〈查询当前模块关联的模块信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link List<AppModuleRelVO>}
     * @author 蝉鸣
     */
    List<AppModuleRelVO> selectRelModuleList(AppModuleRelPageDTO pageDTO);
}
