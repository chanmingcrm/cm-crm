package com.platform.mesh.gen.biz.modules.code.buildconfdata.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.domain.dto.CodeBuildConfDataPageDTO;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.domain.po.CodeBuildConfData;
import com.platform.mesh.mybatis.plus.extention.MPage;

import java.util.Map;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 构造项信息
 * @author 蝉鸣
 */
public interface ICodeBuildConfDataService extends IService<CodeBuildConfData> {

    /**
     * 功能描述:
     * 〈获取需要配置的信息〉
     * @return 正常返回:{@link MPage <CodeBuildConfData>}
     * @author 蝉鸣
     */
    MPage<CodeBuildConfData> buildConfDataPage(CodeBuildConfDataPageDTO pageEntity);

    /**
     * 功能描述:
     * 〈根据构建ID获取配置参数〉
     * @param buildId buildId
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    Map<String, Object> getConfDataMapByBuildId(Long buildId);
}
