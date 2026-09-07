package com.platform.mesh.gen.biz.modules.code.buildconf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.gen.biz.modules.code.buildconf.domain.po.CodeBuildConf;
import com.platform.mesh.gen.biz.modules.code.buildconf.mapper.CodeBuildConfMapper;
import com.platform.mesh.gen.biz.modules.code.buildconf.service.ICodeBuildConfService;
import com.platform.mesh.gen.biz.modules.code.buildconf.service.manual.CodeBuildConfServiceManual;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 配置信息
 * @author 蝉鸣
 */
@Service
public class CodeBuildConfServiceImpl extends ServiceImpl<CodeBuildConfMapper, CodeBuildConf> implements ICodeBuildConfService {

    @Autowired
    private CodeBuildConfServiceManual codeBuildConfServiceManual;


    /**
     * 功能描述:
     * 〈获取需要配置的信息〉
     * @return 正常返回:{@link MPage<CodeBuildConf>}
     * @author 蝉鸣
     */
    @Override
    public MPage<CodeBuildConf> buildConfPage(PageDTO pageEntity) {
        MPage<CodeBuildConf> confMPage = MPageUtil.pageEntityToMPage(pageEntity, CodeBuildConf.class);
        return this.lambdaQuery().page(confMPage);
    }
}
