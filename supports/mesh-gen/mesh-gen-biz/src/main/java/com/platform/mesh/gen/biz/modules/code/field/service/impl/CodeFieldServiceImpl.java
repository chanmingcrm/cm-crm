package com.platform.mesh.gen.biz.modules.code.field.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.gen.biz.modules.code.field.domain.po.CodeFieldMapping;
import com.platform.mesh.gen.biz.modules.code.field.mapper.CodeFieldMapper;
import com.platform.mesh.gen.biz.modules.code.field.service.ICodeFieldService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前SysUserRoleRelImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 字段类型映射信息
 * @author 蝉鸣
 */
@Service
public class CodeFieldServiceImpl extends ServiceImpl<CodeFieldMapper, CodeFieldMapping> implements ICodeFieldService {


    @Override
    public List<CodeFieldMapping> getAllFieldMappingList() {
        return this.getBaseMapper().getAllFieldMappingList();
    }
}
