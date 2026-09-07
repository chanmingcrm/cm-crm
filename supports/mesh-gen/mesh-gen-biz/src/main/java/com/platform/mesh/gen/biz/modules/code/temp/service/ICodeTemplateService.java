package com.platform.mesh.gen.biz.modules.code.temp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.gen.biz.modules.code.temp.domain.po.CodeTemplate;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 代码模板信息
 * @author 蝉鸣
 */
public interface ICodeTemplateService extends IService<CodeTemplate> {

    /**
     * 功能描述:
     * 〈查询所有的模板信息〉
     * @return 正常返回:{@link List<CodeTemplate>}
     * @author 蝉鸣
     */
    List<CodeTemplate> selectAllTempList();

    /**
     * 功能描述:
     * 〈根据分组ID获取模板〉
     * @param tplGroupIds tplGroupIds
     * @return 正常返回:{@link List<CodeTemplate>}
     * @author 蝉鸣
     */
    List<CodeTemplate> getTemplateListByGroupId(List<Long> tplGroupIds);


}
