package com.platform.mesh.gen.biz.modules.code.buildconf.service.manual;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class CodeBuildConfServiceManual {

    private final static Logger log = LoggerFactory.getLogger(CodeBuildConfServiceManual.class);


}

