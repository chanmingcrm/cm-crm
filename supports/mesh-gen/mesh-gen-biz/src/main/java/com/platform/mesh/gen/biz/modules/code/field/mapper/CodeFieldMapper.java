package com.platform.mesh.gen.biz.modules.code.field.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.gen.biz.modules.code.field.domain.po.CodeFieldMapping;

import java.util.List;

/**
 * @description 字段映射Mapper
 * @author 蝉鸣
 */
public interface CodeFieldMapper extends BaseMapper<CodeFieldMapping> {


    @InterceptorIgnore(tenantLine = "true")
    List<CodeFieldMapping> getAllFieldMappingList();
}
