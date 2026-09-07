package com.platform.mesh.gen.biz.modules.code.temp.service.manual;

import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.po.GenGroupRel;
import com.platform.mesh.gen.biz.modules.gen.grouprel.service.IGenGroupRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class CodeTemplateServiceManual {

    @Autowired
    private IGenGroupRelService genGroupRelService;

    /**
     * 功能描述:
     * 〈根据分组获取组关系列表〉
     * @param tplGroupIds tplGroupIds
     * @return 正常返回:{@link List<GenGroupRel>}
     * @author 蝉鸣
     */
    public List<GenGroupRel> getRelByGroupId(Integer groupType,List<Long> tplGroupIds) {
        return genGroupRelService.lambdaQuery()
                .eq(GenGroupRel::getGroupType,groupType)
                .in(GenGroupRel::getGroupId,tplGroupIds).list();
    }
}

