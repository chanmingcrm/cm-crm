package com.platform.mesh.upms.biz.modules.doc.pub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.doc.pub.domain.po.DocPub;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 在线文档信息
 * @author 蝉鸣
 */
public interface IDocPubService extends IService<DocPub> {

    DocPub getByTenantId(Long tenantId);
}
