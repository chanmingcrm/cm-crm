package com.platform.mesh.gen.biz.modules.code.buildconfdata.service.manual;


import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.gen.biz.modules.code.buildconfdata.domain.po.CodeBuildConfData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class CodeBuildConfDataServiceManual {

    private final static Logger log = LoggerFactory.getLogger(CodeBuildConfDataServiceManual.class);

    /**
     * 功能描述:
     * 〈将配置参数转化Map形式〉
     * @param codeBuildConfData codeBuildConfData
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String, Object> getConfDataMap(List<CodeBuildConfData> codeBuildConfData) {
        if(CollUtil.isEmpty(codeBuildConfData)){
            return new HashMap<>();
        }
       return codeBuildConfData
               .stream()
               .collect(Collectors.toMap(CodeBuildConfData::getConfCode, CodeBuildConfData::getConfValue,(v1,v2) -> v2));
    }
}

