package com.platform.mesh.gen.biz.modules.code.temp.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.po.GenGroupRel;
import com.platform.mesh.gen.biz.modules.code.temp.domain.po.CodeTemplate;
import com.platform.mesh.gen.biz.modules.code.temp.mapper.CodeTemplateMapper;
import com.platform.mesh.gen.biz.modules.code.temp.service.ICodeTemplateService;
import com.platform.mesh.gen.biz.modules.code.temp.service.manual.CodeTemplateServiceManual;
import com.platform.mesh.gen.biz.modules.gen.grouprel.enums.GenGroupRelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模板信息
 * @author 蝉鸣
 */
@Service
public class CodeTemplateServiceImpl extends ServiceImpl<CodeTemplateMapper, CodeTemplate> implements ICodeTemplateService {

    @Autowired
    private CodeTemplateServiceManual codeTemplateServiceManual;

    /**
     * 功能描述:
     * 〈查询所有的模板信息〉
     * @return 正常返回:{@link List<CodeTemplate>}
     * @author 蝉鸣
     */
    @Override
    public List<CodeTemplate> selectAllTempList() {
        return this.getBaseMapper().selectAllTempList(YesOrNoEnum.YES.getValue());
    }

    /**
     * 功能描述:
     * 〈根据分组获取模板列表〉
     * @param tplGroupIds tplGroupIds
     * @return 正常返回:{@link List<GenGroupRel>}
     * @author 蝉鸣
     */
    @Override
    public List<CodeTemplate> getTemplateListByGroupId(List<Long> tplGroupIds) {
        List<GenGroupRel> relList = codeTemplateServiceManual.getRelByGroupId(GenGroupRelEnum.CODE_TEMP.getValue(),tplGroupIds);
        if(CollUtil.isEmpty(relList)){
            return CollUtil.newArrayList();
        }
        List<Long> tempList = relList.stream().map(GenGroupRel::getDataId).distinct().collect(Collectors.toList());
        return this.lambdaQuery().eq(CodeTemplate::getDelFlag,YesOrNoEnum.YES.getValue()).in(CodeTemplate::getId,tempList).list();
    }
}
