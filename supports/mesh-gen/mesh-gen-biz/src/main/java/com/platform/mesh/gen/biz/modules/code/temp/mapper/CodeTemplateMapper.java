package com.platform.mesh.gen.biz.modules.code.temp.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.gen.biz.modules.code.temp.domain.po.CodeTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 代码模板Mapper
 * @author 蝉鸣
 */
public interface CodeTemplateMapper extends BaseMapper<CodeTemplate> {

    /**
     * 功能描述:
     * 〈查询所有的模板信息〉
     * @return 正常返回:{@link List<CodeTemplate>}
     * @author 蝉鸣
     */
    @InterceptorIgnore(tenantLine = "true")
    List<CodeTemplate> selectAllTempList(@Param("delFlag")Integer delFlag);
}
