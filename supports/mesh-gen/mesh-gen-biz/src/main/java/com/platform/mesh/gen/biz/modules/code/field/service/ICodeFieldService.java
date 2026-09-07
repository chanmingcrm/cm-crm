package com.platform.mesh.gen.biz.modules.code.field.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.gen.biz.modules.code.field.domain.po.CodeFieldMapping;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 字段映射信息
 * @author 蝉鸣
 */
public interface ICodeFieldService extends IService<CodeFieldMapping> {


    List<CodeFieldMapping> getAllFieldMappingList();
}
