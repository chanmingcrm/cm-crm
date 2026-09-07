package com.platform.mesh.gen.biz.modules.code.buildconf.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.gen.biz.modules.code.buildconf.domain.po.CodeBuildConf;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 配置信息
 * @author 蝉鸣
 */
public interface ICodeBuildConfService extends IService<CodeBuildConf> {

    /**
     * 功能描述:
     * 〈获取需要配置的信息〉
     * @return 正常返回:{@link MPage<CodeBuildConf>}
     * @author 蝉鸣
     */
    MPage<CodeBuildConf> buildConfPage(PageDTO pageEntity);
}
